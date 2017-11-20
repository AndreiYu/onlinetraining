package com.yushkev.onlinetraining.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.yushkev.onlinetraining.command.admin.AddCourseCommand;
import com.yushkev.onlinetraining.command.admin.AddCourseTypeCommand;
import com.yushkev.onlinetraining.command.admin.AddLecturerCommand;
import com.yushkev.onlinetraining.command.admin.ModifyCourseCommand;
import com.yushkev.onlinetraining.command.admin.ModifyCourseTypeCommand;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;

public enum CommandType {
	
	LOGIN (new LogInCommand(), UserRole.values()),
	SIGN_UP (new SignUpCommand(), UserRole.values()),
	LOGOUT (new LogOutCommand(), UserRole.ADMIN, UserRole.LECTURER, UserRole.STUDENT),
		
	ADD_COURSE (new AddCourseCommand(), UserRole.ADMIN),
	MODIFY_COURSE (new ModifyCourseCommand(), UserRole.ADMIN),
	ADD_COURSE_TYPE (new AddCourseTypeCommand(), UserRole.ADMIN),
	MODIFY_COURSE_TYPE (new ModifyCourseTypeCommand(), UserRole.ADMIN),
	ADD_LECTUER (new AddLecturerCommand(), UserRole.ADMIN),
	

	
	EMPTY (new EmptyCommand(), UserRole.values());
	
//	TO_PAGE (new ToPageCommand());
	
		
	private AbstractCommand command;
	
	private List<UserRole> accessRole;
	
	private CommandType(AbstractCommand command, UserRole...roles) {
		this.command = command;
		this.accessRole = new ArrayList<>(Arrays.asList(roles));
	}
	
	public AbstractCommand getCurrentCommand() {
		return command;
	}
	
/*	in case someone who gets roles for this command could modify it*/
	public List<UserRole> getUserRoles() {
		return Collections.unmodifiableList(accessRole);
	}
	
}
