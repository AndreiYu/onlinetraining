package com.yushkev.onlinetraining.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.yushkev.onlinetraining.content.RequestContent;

/*Class to get and put messages in Session or Request MessagesContainers (ArrayList) when executing Commands or implementing Filters and printing result on JSPs*
 * contains only static methods, so no need to create new instance of class */

public class MessageService {
	
	private MessageService() {
	
	}
	
	@SuppressWarnings("unchecked")
	public static void addRequestMessage(Object request, String messageName, String messageContent) {
		ArrayList<String> newList = new ArrayList<>();
		ArrayList<String> messages;
		if (request instanceof HttpServletRequest) {
			messages = (ArrayList<String>) ((HttpServletRequest) request).getAttribute(messageName);
			if (messages != null) {
				newList = messages;
			}
			else {
				((HttpServletRequest) request).setAttribute(messageName, newList);
			}
		}
		if (request instanceof RequestContent) {
			messages = (ArrayList<String>) ((RequestContent) request).getRequestAttribute(messageName);
			if (messages != null) {
				newList = messages;
			}
			else {
				((RequestContent) request).setRequestAttribute(messageName, newList);
			}
		}
		newList.add(messageContent);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void addSessionMessage(Object request, String messageName, String messageContent) {
		ArrayList<String> newList = new ArrayList<>();
		ArrayList<String> messages;
		if (request instanceof HttpServletRequest) {
			messages = (ArrayList<String>) ((HttpServletRequest) request).getSession().getAttribute(messageName);
			if (messages != null) {
				newList = messages;
			}
			else {
				((HttpServletRequest) request).getSession().setAttribute(messageName, newList);
			}
		}
		if (request instanceof RequestContent) {
			messages = (ArrayList<String>) ((RequestContent) request).getSessionAttribute(messageName);
			if (messages != null) {
				newList = messages;
			}
			else {
				((RequestContent) request).setSessionAttribute(messageName, newList);
			}
		}
		newList.add(messageContent);
	}
	

}
