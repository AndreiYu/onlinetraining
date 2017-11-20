package com.yushkev.onlinetraining.dao.interfacedao;

import java.util.List;

import com.yushkev.onlinetraining.entity.CourseEnrolment;
import com.yushkev.onlinetraining.entity.User;

public interface ICourseEnrolmentDAO {

	/**
     * get all CourseEnrolments from database
     */
	
	List<CourseEnrolment> getAll();
	
    /**
     * Remove record from base by student id
     * @param courseId course id
     * @param studentId student id
     */
    boolean deleteForStudent(Integer courseId, Integer studentId);

    /**
     * get all CourseEnrolments for one Course
     * @param courseId
     * @return CourseEnrolment list
     */
   
    List<CourseEnrolment> getCourseEnrolmentsByCourse(Integer courseId);

    /**
     * Get student CourseEnrolments by student
     * @param student - student
     * @return - CourseEnrolment list for student
     */
    
    List<CourseEnrolment> getCourseEnrolmentsByStatus(CourseEnrolment status);
    
    List<CourseEnrolment> getCourseEnrolmentsByCertificate(boolean isObtained);
    
    List<CourseEnrolment> getCourseEnrolmentsByStudentId(User student);

    /**
     * Adding student CourseEnrolment
     * @param courseEnrolment - student CourseEnrolment
     * @return is operation successful
     */
    boolean addCourseEnrolment(CourseEnrolment courseEnrolment);

    /**
     * Update student CourseEnrolment
     * @param courseEnrolment
     * @return is operation successful
     */
    boolean updateCourseEnrolment(CourseEnrolment courseEnrolment);

    /**
     * Adding student to course
     * @param user - student account
     * @param courseId - course id
     * @return success of operation
     */
    boolean addStudentToCourse(User user, Integer courseId);

    CourseEnrolment getCourseEnrolmentByStudentCourse(Integer student, Integer course);

    /**
     * Check CourseEnrolment for empty
     * @param course
     * @return CourseEnrolment status
     */
    boolean checkAllCourseEnrolmentsForCourse(Integer course);

}
	
