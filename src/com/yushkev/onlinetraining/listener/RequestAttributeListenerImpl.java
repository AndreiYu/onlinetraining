package com.yushkev.onlinetraining.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import com.yushkev.onlinetraining.constant.GeneralConstant;

/**
 * WebListener for invalidating user session after logout.
 */
@WebListener
public class RequestAttributeListenerImpl implements ServletRequestAttributeListener{

	@Override
	public void attributeAdded(ServletRequestAttributeEvent srae) {
		if(srae.getName().equals(GeneralConstant.REQUEST_ATTR_DESTROY_SESSION)){
			HttpServletRequest request = (HttpServletRequest) srae.getServletRequest();
			request.getSession().invalidate();
		}
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		
	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		
	}

}
