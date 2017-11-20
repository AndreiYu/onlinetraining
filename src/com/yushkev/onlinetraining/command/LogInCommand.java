package com.yushkev.onlinetraining.command;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.logic.AuthenticationLogic;
import com.yushkev.onlinetraining.resource.ConfigurationManager;
import com.yushkev.onlinetraining.service.MessageService;

public class LogInCommand extends AbstractCommand {

	private static Logger logger = LogManager.getLogger(LogInCommand.class);
    
	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
		String page = ConfigurationManager.getProperty("path.page.login");
		String errorMessage = null;
		String login = requestContent.getRequestParameter(GeneralConstant.LOGIN);
		String password = requestContent.getRequestParameter(GeneralConstant.PASSWORD);
		Optional<User> user = AuthenticationLogic.authenticate(login, password); //TODO: Check if login/password parameters are null?
		if (!user.isPresent()) {
			errorMessage = "message.login.error";
			logger.log(Level.WARN, "Attempt to login under user account:  " + login);
		}
		else {
			requestContent.setSessionAttribute(GeneralConstant.SESSION_ATTR_USER, user.get());
			switch (user.get().getRole()) {
			case ADMIN:
//				CourseLogic.setAllCourses(requestContent);
	            page = ConfigurationManager.getProperty("path.page.admin.account");	
	            break;
			case LECTURER:
//				CourseLogic.setCoursesForLecturer(requestContent, user);
	            page = ConfigurationManager.getProperty("path.page.lecturer.account");
	            break;
			case STUDENT:
//				CourseLogic.setCoursesForStudent(requestContent, user);
	           	page = ConfigurationManager.getProperty("path.page.student.account");
				break;
			default:
				errorMessage = "message.error.unknown_role";
				page = ConfigurationManager.getProperty("path.page.admin_contact");
				logger.log(Level.WARN, "User: " + login + " is without role assigned and was redirected to admin contactPage");
			}
			logger.log(Level.INFO, "User: " + login + " has successfully logged in");
		}
		MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
		return new ActionResult(ActionType.REDIRECT, page);
	}
}





/*			if (user.get().isActive()) {
//	requestContent.setContextAttribute(RequestContent.USERS_ONLINE, usersOnline.add(user.getLogin()));
switch (user.get().getRole()) {
case ADMIN:
//	CourseLogic.setAllCourses(requestContent);
    page = ConfigurationManager.getProperty("path.page.admin.account");	
    break;
case LECTURER:
//	CourseLogic.setCoursesForLecturer(requestContent, user);
    page = ConfigurationManager.getProperty("path.page.lecturer.account");
    break;
case STUDENT:
//	CourseLogic.setCoursesForStudent(requestContent, user);
   	page = ConfigurationManager.getProperty("path.page.student.account");
	break;
default:
	errorMessage = "message.error.unknown_role";
	page = ConfigurationManager.getProperty("path.page.admin_contact");
	logger.log(Level.WARN, "User: " + login + " is without role assigned and was redirected to admin contactPage");
}
logger.log(Level.INFO, "User: " + login + " has successfully logged in");
}	
else {
	errorMessage = "message.error.is_banned";
	page = ConfigurationManager.getProperty("path.page.admin_contact");
}*/

