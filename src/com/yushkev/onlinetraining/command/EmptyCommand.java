package com.yushkev.onlinetraining.command;

import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.resource.ConfigurationManager;


/**
 * Command invoked when command is wrong or empty (for ex. in case logged in user tried to modify command query).
 * Redirects to index page
 */
public class EmptyCommand extends AbstractCommand{

	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
//		String page = (String) requestContent.getSessionAttribute(GeneralConstant.FROM_PAGE_PATH);
		String page = ConfigurationManager.getProperty("path.page.index");
		return new ActionResult(ActionType.REDIRECT, page);
	}
	

}
