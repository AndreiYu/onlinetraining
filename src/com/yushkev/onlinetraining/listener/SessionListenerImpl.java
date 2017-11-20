package com.yushkev.onlinetraining.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.entity.User;

/**
 * WebListener for binding a new User with role Guest after session created
 */
@WebListener
public class SessionListenerImpl implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		 se.getSession().setAttribute(GeneralConstant.SESSION_ATTR_USER, new User());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}

}
