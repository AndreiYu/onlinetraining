package com.yushkev.onlinetraining.command.admin;

import com.yushkev.onlinetraining.command.AbstractCommand;
import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.entity.CourseType;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.logic.AuthenticationLogic;
import com.yushkev.onlinetraining.logic.CourseLogic;
import com.yushkev.onlinetraining.resource.ConfigurationManager;
import com.yushkev.onlinetraining.service.MessageService;
import com.yushkev.onlinetraining.validator.DataValidator;

public class AddCourseTypeCommand  extends AbstractCommand{

	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
		String page = ConfigurationManager.getProperty("path.page.admin.add_course");
		String errorMessage = null;
		String submit = requestContent.getRequestParameter(GeneralConstant.SUBMIT);
		CourseType courseType = null;
		
		CourseLogic.setInfoForModifyCourse(requestContent);
	
	/* 		if command from another page - just go to this page, else - try to add new courseType and go to course_info page*/	
		if (submit != null) {

//			String type_id = requestContent.getRequestParameter(GeneralConstant.COURSE_TYPE_ID);
			String courseType_category = requestContent.getRequestParameter(GeneralConstant.COURSE_TYPE_CATEGORY);

			errorMessage = !DataValidator.isValidToRegex(courseType_category, DataValidator.COURSE_TITLE_REGEX) ? "message.modify_course.wrong_title" : null;
	/* 		add error message about invalid regex or early date if happened*/
			if (errorMessage != null) {
				MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage);
			}
	/* 		add error message about category exists if happened*/
			if (CourseLogic.checkCourseTypeExists(courseType_category)) {
				MessageService.addRequestMessage(requestContent, GeneralConstant.INFO_MESSAGE, errorMessage = "message.modify_course.wrong.name_exists");
			}
		
			/*if no error messages, create courseType*/
			courseType = (errorMessage == null) ?	CourseLogic.createNewCourseType(courseType_category) : null;
			if (courseType != null) {

				requestContent.setSessionAttribute(GeneralConstant.REQUEST_ATTR_COURSE_TYPE, courseType);
				MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, "message.success");
				return new ActionResult(ActionType.REDIRECT, page); 
			}
		}

		/*if no courseType created go back to add_course page	
		* if created  - redirect to add_course page*/
//		requestContent.setRequestAttribute(GeneralConstant.REQUEST_ATTR_COURSE_TYPE, courseType);
		return new ActionResult(ActionType.FORWARD, page);
		}

	
	}
