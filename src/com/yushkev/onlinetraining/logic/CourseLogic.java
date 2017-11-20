package com.yushkev.onlinetraining.logic;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.dao.CourseDaoImpl;
import com.yushkev.onlinetraining.dao.CourseTypeDaoImpl;
import com.yushkev.onlinetraining.dao.DaoFactory;
import com.yushkev.onlinetraining.dao.UserDaoImpl;
import com.yushkev.onlinetraining.entity.Course;
import com.yushkev.onlinetraining.entity.CourseType;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.CourseStatus;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;
import com.yushkev.onlinetraining.exception.DAOException;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.validator.DataValidator;

public class CourseLogic {
	
	
    /**
     * Set info for editing or creating Course
     * @param {@code RequestContent}
     * @throws LogicCommandException
     */
	public static void setInfoForModifyCourse(RequestContent requestContent) throws LogicCommandException{
		
		try (UserDaoImpl daoUser = (UserDaoImpl) DaoFactory.getInstance().getUserDao();
			 CourseTypeDaoImpl daoCourseType = (CourseTypeDaoImpl) DaoFactory.getInstance().getCourseTypeDao();
			 CourseDaoImpl daoCourse = (CourseDaoImpl) DaoFactory.getInstance().getCourseDao()) {

			List<CourseType> courseTypes = daoCourseType.getAll();
			requestContent.setRequestAttribute(GeneralConstant.REQUEST_ATTR_COURSE_TYPES, courseTypes);
            List<User> lecturers = daoUser.getAll().stream().filter(a->a.getRole().equals(UserRole.LECTURER)) //XXX or all users and filter on JSP?
            		.collect(Collectors.toList());
            requestContent.setRequestAttribute(GeneralConstant.REQUEST_ATTR_LECTURERS, lecturers);
            List<Course> allCourses = daoCourse.getAll();
			requestContent.setRequestAttribute(GeneralConstant.REQUEST_ATTR_ALL_COURSES, allCourses);
		}
		catch (DAOException e) {
			throw new LogicCommandException("An exception occured. Couldn't set info for modyfying course: " + e);
		}
    }
	
    /**
     * Method to create new Course
     * @return {@link Course} instance
     * @throws LogicCommandException
     */
    public static Course createNewCourse(String lecturer_id, String type_id, String status, String title, String start_date, 
    		String end_date, String description, String certificate_price, String avatar_path) throws LogicCommandException {
    	
    	try (CourseDaoImpl dao = (CourseDaoImpl) DaoFactory.getInstance().getCourseDao()) {
    		
    		GregorianCalendar startDate = DataValidator.checkDate(start_date).orElse(null);
    		GregorianCalendar endDate = DataValidator.checkDate(end_date).orElse(null);
    		Course resultCourse = new Course(Integer.valueOf(lecturer_id), Integer.valueOf(type_id), 
    				CourseStatus.valueOf(status.toUpperCase()), title, startDate, endDate, description, 
    				Double.valueOf(certificate_price), avatar_path);
    		Integer courseId = dao.create (resultCourse);
    		resultCourse.setId(courseId);
    		
    		return resultCourse;
		} catch (DAOException e) {
				throw new LogicCommandException("An exception occured. Couldnt't create Course: " + title + "   " + e);
		}
    }
    
    /**
     * Method to update Course
     * @return {@link Course} instance
     * @throws LogicCommandException
     */
    public static Course updateCourse(Course selected_course, String lecturer_id, String type_id, String status, String title, String start_date, 
    		String end_date, String description, String certificate_price) throws LogicCommandException {
    	
		GregorianCalendar startDate = DataValidator.checkDate(start_date).orElse(null);
		GregorianCalendar endDate = DataValidator.checkDate(end_date).orElse(null);
    	selected_course.setLecturerId(Integer.valueOf(lecturer_id));
    	selected_course.setTypeId(Integer.valueOf(type_id));
    	selected_course.setStatus(CourseStatus.valueOf(status.toUpperCase()));
    	selected_course.setTitle(title);
    	selected_course.setStartDate(startDate);
    	selected_course.setEndDate(endDate);
    	selected_course.setDescription(description);
    	selected_course.setCertificatePrice(Double.valueOf(certificate_price));
    	
    	try (CourseDaoImpl dao = (CourseDaoImpl) DaoFactory.getInstance().getCourseDao()) {
    		return (dao.update(selected_course) > 0) ? selected_course : null;
		} catch (DAOException e) {
				throw new LogicCommandException("An exception occured. Couldnt't update Course: " + selected_course.getId() + "   " + e);
		}
    }
    
		
    /**
     * Method to update Course avatarPath
     * @return boolean result
     * @throws LogicCommandException
     */
    public static boolean updateCourseAvatar(Integer courseId, String path) throws LogicCommandException {
    	
    	try (CourseDaoImpl dao = (CourseDaoImpl) DaoFactory.getInstance().getCourseDao()) {
    		
    		return dao.updateAvatarPath(courseId, path);
		} catch (DAOException e) {
				throw new LogicCommandException("An exception occured. Couldnt't update CourseAvatarPath: " + courseId + "   " + e);
		}
    }
    
    
	/*	Utility method to receive errormessage from validating input data */
	public static String getMessageFromValidation (String title, String start_date, String end_date) {
		String errorMessage = !DataValidator.isValidToRegex(title, DataValidator.COURSE_TITLE_REGEX) ? "message.modify_course.wrong_title" :
			(!DataValidator.isDateAfterPresent(start_date) || !DataValidator.isDateAfterPresent(end_date)) ? 
					"message.modify_course.wrong_date" : (!DataValidator.isEndDateAfterStartDate(start_date, end_date)) ?
							"message.modify_course.wrong_date" : null;
			return errorMessage;
	}
    
    
   
    public static CourseType createNewCourseType(String category) throws LogicCommandException {
    	
    	try (CourseTypeDaoImpl dao = (CourseTypeDaoImpl) DaoFactory.getInstance().getCourseTypeDao()) {
    		
    		CourseType resultCourseType = new CourseType(category);
    		Integer courseTypeId = dao.create (resultCourseType);
    		resultCourseType.setId(courseTypeId);
    		
    		return resultCourseType;
		} catch (DAOException e) {
				throw new LogicCommandException("An exception occured. Couldnt't create CourseType: " + category + "   " + e);
		}
    }
    
    public static CourseType updateCourseType(String courseTypeId, String category) throws LogicCommandException {
    	
    	try (CourseTypeDaoImpl dao = (CourseTypeDaoImpl) DaoFactory.getInstance().getCourseTypeDao()) {
    		
    		CourseType resultCourseType = new CourseType(Integer.valueOf(courseTypeId), category);
    		
    		return (dao.update(resultCourseType) > 0) ? resultCourseType : null;
		} catch (DAOException e) {
				throw new LogicCommandException("An exception occured. Couldnt't update CourseType: " + category + "   " + e);
		}
    }
    
    public static boolean checkCourseTypeExists(String category) throws LogicCommandException {
    	boolean result = false;
    	if (category != null) {
    		try (CourseTypeDaoImpl dao = (CourseTypeDaoImpl) DaoFactory.getInstance().getCourseTypeDao()){
        		result = dao.getCourseTypeByCategory(category).isPresent();
      		} catch (DAOException e) {
    			throw new LogicCommandException("An exception occured. Couldnt't check category exist for category: " + category + "   " + e);
			}
    	}
		return result;
    }
		
}
	

