package com.yushkev.onlinetraining.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import com.yushkev.onlinetraining.dao.interfacedao.ICourseDao;
import com.yushkev.onlinetraining.entity.Course;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.CourseStatus;
import com.yushkev.onlinetraining.exception.DAOException;

import sun.font.CreatedFontTracker;

public class CourseDaoImpl extends AbstractDAO<Integer, Course> implements ICourseDao{

	private static final String SQL_SELECT_ALL_COURSES = "SELECT id, lecturer_id, type_id, status, title, start_date, end_date, "
			+ "description, certificate_price, avatar_path  FROM course";
	private static final String SQL_SELECT_COURSE_BY_ID = "SELECT id, lecturer_id, type_id, status, title, start_date, end_date, "
			+ "description, certificate_price, avatar_path  FROM course WHERE course.id = ?";
	private static final String SQL_DELETE_COURSE_BY_ID = "DELETE FROM course WHERE id = ?";
	private static final String SQL_UPDATE_COURSE = "UPDATE course SET lecturer_id = ?, type_id = ?, status = ?, title = ?, start_date = ?, "
			+ "end_date = ?, description = ?, certificate_price = ?, avatar_path = ? WHERE id = ?";
    private static final String SQL_INSERT_COURSE = "INSERT INTO course (lecturer_id, type_id, status, title, start_date, end_date, "
    		+ "description, certificate_price, avatar_path) values (?,?,?,?,?,?,?,?,?)";
	private static final String SQL_UPDATE_COURSE_AVATAR_PATH = "UPDATE course SET avatar_path = ? WHERE id = ?";
	
	
	
	public CourseDaoImpl() throws DAOException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Course> getCoursesForUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> getCoursesByStatus(CourseStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateCourseStatus(CourseStatus courseStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Course> getAll() throws DAOException {
		return getAll(SQL_SELECT_ALL_COURSES);
	}

	@Override
	public Optional<Course> getEntityByKey(Integer id) throws DAOException {
	    return getEntityByKey(id, SQL_SELECT_COURSE_BY_ID);
	}

	@Override
	public boolean delete(Integer id) throws DAOException {
    	return delete(id, SQL_DELETE_COURSE_BY_ID);
     }

	@Override
	public Integer update(Course entity) throws DAOException {
		return update(entity, SQL_UPDATE_COURSE);
	}

	@Override
	public Integer create(Course entity) throws DAOException {
		return create(entity, SQL_INSERT_COURSE);
	}	
		
		


	@Override
	protected Course fillEntity(ResultSet resultSet) throws DAOException {
		Course course = new Course();
		GregorianCalendar calendar = null;
		Date date = null;
		try {
				course.setId(resultSet.getInt("id"));
				course.setLecturerId(resultSet.getInt("lecturer_id"));
				course.setTypeId(resultSet.getInt("type_id"));
				course.setStatus(CourseStatus.valueOf(resultSet.getString("status").toUpperCase()));
				course.setTitle(resultSet.getString("title"));
				date = resultSet.getDate("start_date");
				if (date != null) {
					calendar = new GregorianCalendar();
					calendar.setTime(date);
					course.setStartDate(calendar);
				}
				date = resultSet.getDate("end_date");
				if (date != null) {
					calendar = new GregorianCalendar();
					calendar.setTime(date);
					course.setEndDate(calendar);
				}
				course.setDescription(resultSet.getString("description"));
				course.setCertificatePrice(resultSet.getDouble("certificate_price"));
				course.setAvatar_path(resultSet.getString("avatar_path"));
		}
		catch (SQLException e) {
			throw new DAOException("Unable to fill entity " + e);
		}
		return course;
	}

	@Override
	protected void fillStatement(PreparedStatement statement, Course entity) throws DAOException {
		try {
			int i = 1;
			statement.setInt(i++, entity.getLecturerId());		
	        statement.setInt(i++, entity.getTypeId());
	        statement.setString(i++, entity.getStatus().name().toLowerCase());
	        statement.setString(i++, entity.getTitle());
	        if (entity.getStartDate() != null) {
	        	statement.setDate(i++, new java.sql.Date(entity.getStartDate().getTimeInMillis()));
			}
	        else {
	        	statement.setNull(i++, Types.DATE);
	        }
	        if (entity.getEndDate() != null) {
	        	statement.setDate(i++, new java.sql.Date(entity.getEndDate().getTimeInMillis()));
			}
	        else {
	        	statement.setNull(i++, Types.DATE);
	        }
	        statement.setString(i++, entity.getDescription());
	        statement.setDouble(i++, entity.getCertificatePrice());
	        statement.setString(i++, entity.getAvatar_path());
	   /*     if need to update existing entity based on ID*/
	        if(entity.getId() != null) {
	        	statement.setInt(i++, entity.getId());
	        }
		} catch (SQLException e) { 
			throw new DAOException("Unable to fill Statement for Course ID: " + entity.getId() + "   " + e);
	    }
	}

	public boolean updateAvatarPath(Integer courseId, String path) throws DAOException {
		try (PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE_COURSE_AVATAR_PATH)){
	    	   statement.setString(1, path);
	    	   statement.setInt(2, courseId);
	    	   return statement.executeUpdate() > 0;
	       }   
	       catch (SQLException e) {
	    	 throw new DAOException("Unable to update avatarPath for : " + courseId + "   " + e);
		}
	}


	

}
