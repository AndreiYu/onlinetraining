package com.yushkev.onlinetraining.dao.interfacedao;

import java.util.List;

import com.yushkev.onlinetraining.entity.Course;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.CourseStatus;

public interface ICourseDao {

//	List<Course> findCoursesForLecturer(User lecturer);
//	
//	List<Course> findCoursesForStudent(User student);
	
	List<Course> getCoursesForUser(User user); //general method?
	
	List<Course> getCoursesByStatus(CourseStatus status);
	
    /**
     * Update course status to "open", "in_process" or "closed"
     * @param courseStatus
     * @return success operation
     */
	
	boolean updateCourseStatus(CourseStatus courseStatus);
	
	
	
}
