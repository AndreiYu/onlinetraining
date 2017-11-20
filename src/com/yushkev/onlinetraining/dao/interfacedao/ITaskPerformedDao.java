package com.yushkev.onlinetraining.dao.interfacedao;

import java.util.List;

import com.yushkev.onlinetraining.entity.Task;
import com.yushkev.onlinetraining.entity.TaskPerformed;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.TaskPerformedStatus;

public interface ITaskPerformedDao {

	
	List<TaskPerformed> getTaskPerformedForStudent(User student); 
	
	List<TaskPerformed> getTaskPerformedForTask(Task task); 
	
	List<TaskPerformed> getTaskPerformedByStatus(TaskPerformedStatus status); 
	
	List<TaskPerformed> getTaskPerformedByMark(Integer mark);
	
	boolean updateTaskPeformedStatus(TaskPerformedStatus courseStatus);


}
