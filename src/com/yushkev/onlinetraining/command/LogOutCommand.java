package com.yushkev.onlinetraining.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.resource.ConfigurationManager;

public class LogOutCommand extends AbstractCommand{
	
	private static Logger logger = LogManager.getLogger(LogOutCommand.class);

	@Override
	public ActionResult execute(RequestContent requestContent) {
		String page = ConfigurationManager.getProperty("path.page.index");
		String userLogin = (String) ((User)requestContent.getSessionAttribute(GeneralConstant.SESSION_ATTR_USER)).getLogin();
		requestContent.setRequestAttribute(GeneralConstant.REQUEST_ATTR_DESTROY_SESSION, true);
		logger.log(Level.INFO, "User: " + userLogin + " has logged out");    
		
		return new ActionResult(ActionType.REDIRECT, page);
    }

}
