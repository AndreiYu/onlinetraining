package com.yushkev.onlinetraining.command.admin;

import java.io.IOException;
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

public class ModifyCourseCommand extends AbstractCommand{
	
	private static Logger logger = LogManager.getLogger(ModifyCourseCommand.class);

	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
		String page = ConfigurationManager.getProperty("path.page.admin.add_course");
		String submit = requestContent.getRequestParameter(GeneralConstant.SUBMIT);	
		String chooseToModify = requestContent.getRequestParameter(GeneralConstant.CHOOSE_TO_MODIFY);	
		Course course_to_modify = (Course) requestContent.getRequestAttribute(GeneralConstant.REQUEST_ATTR_COURSE_TO_MODIFY);
		String selected_course = requestContent.getRequestParameter(GeneralConstant.SELECTED_ITEM);
		String errorMessage = null;

		CourseLogic.setInfoForModifyCourse(requestContent);
	
	/* 		if command from another page - just go to this page, else - try to modify course and go to course_info page*/	
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
			
			errorMessage = CourseLogic.getMessageFromValidation(title, start_date, end_date);
	/* 		add error message about invalid regex or early date if happened*/
			if (errorMessage != null) {
				MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
			}
			else {
//	/*if no error messages, update course*/
//			Course course = (errorMessage == null) ?	CourseLogic.updateCourse(lecturer_id, type_id, status, title, 
//					start_date, end_date, description, certificate_price, avatarPath, selected_course) : null;
//			if (course != null) {
				
/*Try to upload image. If image of wrong format or size - add message but continue anyway*/
				if (avatar_img != null && avatar_img.getSize() != 0) {
					if (!DataValidator.isImageValid(avatar_img)) {
						errorMessage = "message.wrong_file";
						MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
					}
					else {
						try {
							String avatarPath = AvatarImgService.uploadImage(requestContent, course_to_modify.getId().toString()); //upload image or get default filepath
							course_to_modify.setAvatar_path(avatarPath);
						} catch (UncheckedIOException | IOException e) {
							logger.log(Level.WARN, "Course id: " + course_to_modify.getId() + " unable to save image " + e);
							MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, "message.error_file"); //XXX: print message on user's page
						}
					}
				}
				
				CourseLogic.updateCourse(course_to_modify, lecturer_id, type_id, status, title, start_date, end_date, description, certificate_price);
				requestContent.setSessionAttribute(GeneralConstant.SESSION_ATTR_COURSE, course_to_modify);				//XXX: del after showing on infopage if not needed	
				page = ConfigurationManager.getProperty("path.page.common.course_info");
				MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, "message.success"); //XXX: print success on redirecting page!!!
				return new ActionResult(ActionType.REDIRECT, page);
			}	

		}
		/*if button "choose_to_modify" was pressed, add selected course to request again and show parameters of this course on the page*/	
		else if (chooseToModify != null && selected_course != null && !selected_course.isEmpty()) {//XXX do it on JSP page!!!!////////////////////////////////////////////
			requestContent.setRequestAttribute(GeneralConstant.SELECTED_ITEM,	selected_course);
		}
		
		/*if no course modified - go back to modify_course page	
		* if updated (with foto uploaded or not) - redirect to course_info page*/	
			return new ActionResult(ActionType.FORWARD, page);
	}
	
		
		
	}

