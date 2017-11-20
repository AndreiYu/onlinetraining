package com.yushkev.onlinetraining.command;

import java.io.IOException;
import java.io.UncheckedIOException;

import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.logic.AuthenticationLogic;
import com.yushkev.onlinetraining.logic.UserLogic;
import com.yushkev.onlinetraining.resource.ConfigurationManager;
import com.yushkev.onlinetraining.service.AvatarImgService;
import com.yushkev.onlinetraining.service.MessageService;
import com.yushkev.onlinetraining.validator.DataValidator;

public class SignUpCommand  extends AbstractCommand{
	
	private static Logger logger = LogManager.getLogger(SignUpCommand.class);
	
	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
		String page = ConfigurationManager.getProperty("path.page.sign_up");
		String errorMessage = null;
		
			String login = requestContent.getRequestParameter(GeneralConstant.LOGIN);
			String password = requestContent.getRequestParameter(GeneralConstant.PASSWORD);
			String confirm_password = requestContent.getRequestParameter(GeneralConstant.CONFIRM_PASSWORD);
			String first_name = requestContent.getRequestParameter(GeneralConstant.FIRST_NAME);
			String last_name = requestContent.getRequestParameter(GeneralConstant.LAST_NAME);
			String email = requestContent.getRequestParameter(GeneralConstant.EMAIL);
			String role = requestContent.getRequestParameter(GeneralConstant.USER_ROLE);
			Part avatar_img = requestContent.getPart(GeneralConstant.AVATAR_USER_IMG);
			String avatarPath = null;
		
				errorMessage = UserLogic.getMessageFromValidation(login, password, first_name, last_name, email, confirm_password);
	/* 		add error message about invalid regex if happened*/
			if (errorMessage != null) {
				MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
			}
	/* 		add error message about login exists if happened*/
			if (AuthenticationLogic.checkLoginExists(login)) {
				MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage = "message.signup.wrong.login_exists");
			}
	/* 		add error message about email exists if happened*/		
			if (AuthenticationLogic.checkEmailExists(email)) {
				MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage = "message.signup.wrong.email_exists");
			}
	/*if no error messages, create user*/
			if (errorMessage == null) {
				/*Try to upload image. If image of wrong format or size - add message to session, but continue creating user*/
				if (avatar_img != null && avatar_img.getSize() != 0) {
					if (!DataValidator.isImageValid(avatar_img)) {
						errorMessage = "message.wrong_file";
						MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
						}
				}
					if (errorMessage == null) {
						try {
							avatarPath = AvatarImgService.uploadImage(requestContent, login); //upload image or get default filepath if no image attached
						} catch (UncheckedIOException | IOException e) {
							logger.log(Level.WARN, "User: " + login + " unable to save image " + e);
							MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, "message.error_file"); //XXX: print message on user's page and change to session scope as redirect happenes?
						}
					}
				User user = UserLogic.createNewUser(login, password, role, first_name, last_name, email, avatarPath);
				page = UserRole.valueOf(role.toUpperCase()) == UserRole.STUDENT ? 	//XXX change to path.page.blank and there print all data
						ConfigurationManager.getProperty("path.page.student.account") : ConfigurationManager.getProperty("path.page.lecturer.account");
				logger.log(Level.INFO, "User: " + login + " has successfully registered as " + role);
								requestContent.setSessionAttribute(GeneralConstant.SESSION_ATTR_USER, user);
								
				return new ActionResult(ActionType.REDIRECT, page);
			}

	/*if no user created, forward back to sign_up page	
	* if created (with foto uploaded or not) - redirect to user's page*/	
		return new ActionResult(ActionType.FORWARD, page);
	}
		
	
	
	
}