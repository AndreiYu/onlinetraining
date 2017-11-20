package com.yushkev.onlinetraining.command;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.ActionResult.ActionType;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.resource.ConfigurationManager;


/*Command used to forward to another page if no new parameters are attached or new parameters are catched by filters (for ex. localeFilter)*/
public class ToPageCommand_tmp extends AbstractCommand {

	@Override
	public ActionResult execute(RequestContent requestContent) throws LogicCommandException {
		
		String toPage = (String) requestContent.getRequestAttribute(GeneralConstant.TO_PAGE_PATH);
		ActionResult actionResult;
		if (toPage != null && !toPage.isEmpty()) {
			toPage = ConfigurationManager.getProperty(toPage);
			actionResult = new ActionResult(ActionType.FORWARD, toPage);
		}
		else {
			toPage = ConfigurationManager.getProperty("path.page.error_404");
			actionResult = new ActionResult(ActionType.FORWARD, toPage);
		}
		return actionResult;
	}
}





