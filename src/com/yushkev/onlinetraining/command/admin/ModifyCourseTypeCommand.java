package com.yushkev.onlinetraining.command.admin;

import com.yushkev.onlinetraining.command.AbstractCommand;
import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.entity.CourseType;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.logic.CourseLogic;
import com.yushkev.onlinetraining.resource.ConfigurationManager;
import com.yushkev.onlinetraining.service.MessageService;
import com.yushkev.onlinetraining.validator.DataValidator;

public class ModifyCourseTypeCommand  extends AbstractCommand{

	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
		String page = ConfigurationManager.getProperty("path.page.admin.add_course");
		String errorMessage = null;
		String submit = requestContent.getRequestParameter(GeneralConstant.SUBMIT);	
		String chooseToModify = requestContent.getRequestParameter(GeneralConstant.CHOOSE_TO_MODIFY);	
		
		String selected_courseType = requestContent.getRequestParameter(GeneralConstant.SELECTED_ITEM);
	
	/* 		if command from another page - just go to this page, else - try to modify courseType and go to addCourse page*/	
		if (submit != null) {
			
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
		
			/*if no error messages, update courseType*/
			CourseType courseType = (errorMessage == null) ?	CourseLogic.updateCourseType(selected_courseType, courseType_category) : null;
			if (courseType != null) {

				requestContent.setSessionAttribute(GeneralConstant.REQUEST_ATTR_COURSE_TYPE, courseType);
				MessageService.addSessionMessage(requestContent, GeneralConstant.INFO_MESSAGE, "message.success");
				return new ActionResult(ActionType.REDIRECT, page); 
			}
		}
		
		/*if button "choose_to_modify" was pressed, add selected course to request again*/	
		else if (chooseToModify != null && selected_courseType != null && !selected_courseType.isEmpty()) {
			requestContent.setRequestAttribute(GeneralConstant.SELECTED_ITEM,	selected_courseType);
		}

		/*if no courseType created go back to add_course page	
		* if created  - redirect to add_course page*/
//		requestContent.setRequestAttribute(GeneralConstant.REQUEST_ATTR_COURSE_TYPE, courseType);
		return new ActionResult(ActionType.FORWARD, page);
		}

}

