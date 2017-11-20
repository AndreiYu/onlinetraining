package com.yushkev.onlinetraining.dao.interfacedao;

import java.util.GregorianCalendar;
import java.util.List;

import com.yushkev.onlinetraining.entity.Course;
import com.yushkev.onlinetraining.entity.Task;

public interface ITaskDao {

	List<Task> getAllCourseTasks(Course course); 
	
	List<Task> getTasksBeforeDate(GregorianCalendar beforeDate); 
	
	List<Task> getTasksAfterDate(Course aftereDate); 
	
	
}
