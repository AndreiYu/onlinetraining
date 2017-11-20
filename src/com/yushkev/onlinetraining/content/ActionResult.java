package com.yushkev.onlinetraining.content;

/**
 * Data class for keeping result of action command
 */

public class ActionResult {

	/** 
	 * action type for servlet to execute
	 */
	private ActionType type;
	
	/** 
	 * page to go on as a result of execution of command
	 */
	private String page;
	
	public ActionResult() {
		super();
	}

	public ActionResult(ActionType type, String page) {
		super();
		this.type = type;
		this.page = page;
	}

	public ActionType getType() {
		return type;
	}

	public String getPage() {
		return page;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public enum ActionType {
		FORWARD, REDIRECT;
	}
}
