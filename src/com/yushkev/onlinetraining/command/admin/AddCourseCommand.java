package com.yushkev.onlinetraining.command.admin;

import java.io.UncheckedIOException;

import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.command.AbstractCommand;
import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.entity.Course;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.logic.CourseLogic;
import com.yushkev.onlinetraining.resource.ConfigurationManager;
import com.yushkev.onlinetraining.service.AvatarImgService;
import com.yushkev.onlinetraining.service.MessageService;
import com.yushkev.onlinetraining.validator.DataValidator;

public class AddCourseCommand extends AbstractCommand{
	
	private static Logger logger = LogManager.getLogger(AddCourseCommand.class);

	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
		String page = ConfigurationManager.getProperty("path.page.admin.add_course");
		String submit = requestContent.getRequestParameter(GeneralConstant.SUBMIT);
		String errorMessage = null;
		
		CourseLogic.setInfoForModifyCourse(requestContent);
	
	/* 		if command from another page - just go to this page, else - try to add new course and go to course_info page*/	
	if (submit != null) {
			
			String lecturer_id = requestContent.getRequestParameter(GeneralConstant.COURSE_LECTURER);
			String type_id = requestContent.getRequestParameter(GeneralConstant.COURSE_TYPE_ID);
			String status = requestContent.getRequestParameter(GeneralConstant.COURSE_STATUS);
			String title = requestContent.getRequestParameter(GeneralConstant.COURSE_TITLE);
			String start_date = requestContent.getRequestParameter(GeneralConstant.COURSE_START_DATE);
			String end_date = requestContent.getRequestParameter(GeneralConstant.COURSE_END_DATE);
			String description = requestContent.getRequestParameter(GeneralConstant.COURSE_DESCRIPTION);
			String certificate_price = requestContent.getRequestParameter(GeneralConstant.CERTIFICATE_PRICE);
			Part avatar_img = requestContent.getPart(GeneralConstant.AVATAR_COURSE_IMG);
			String avatarPath = null;
			
			errorMessage = CourseLogic.getMessageFromValidation(title, start_date, end_date);
	/* 		add error message about invalid regex or early date if happened*/
		if (errorMessage != null) {
				MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
			}
	/*if no error messages, create course*/
		else {
			Course course = CourseLogic.createNewCourse(lecturer_id, type_id, status, title, 
					start_date, end_date, description, certificate_price, null);
							
	/*Try to upload image. If image of wrong format or size - add message but continue anyway*/
				if (avatar_img != null && avatar_img.getSize() != 0) {
					if (!DataValidator.isImageValid(avatar_img)) {
						errorMessage = "message.wrong_file";
						MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
					}
				}
				if (errorMessage == null) {
					try {
						avatarPath = AvatarImgService.uploadImage(requestContent, course.getId().toString()); //upload image or get default filepath
						course.setAvatar_path(avatarPath);
					} catch (UncheckedIOException | IOException e) {
						logger.log(Level.WARN, "Course id: " + course.getId() + " unable to save image " + e);
						MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, "message.error_file"); //XXX: print message on user's page
					}
				}
				CourseLogic.updateCourseAvatar(course.getId(), avatarPath);
				requestContent.setSessionAttribute(GeneralConstant.SESSION_ATTR_COURSE, course);				//XXX: del after showing on infopage if not needed	
				page = ConfigurationManager.getProperty("path.page.common.course_info");
				MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, "message.success"); //XXX: print success on redirecting page!!!
				return new ActionResult(ActionType.REDIRECT, page);
		}
	}

		/*if no course created go back to add_course page	
		* if created (with foto uploaded or not) - redirect to course_info page*/	
		return new ActionResult(ActionType.FORWARD, page);
	}

	
	
}

