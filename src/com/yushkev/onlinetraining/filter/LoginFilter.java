package com.yushkev.onlinetraining.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;
import com.yushkev.onlinetraining.resource.ConfigurationManager;

/* Filter that redirects registered user from "login"/ "register" page if session is still active
 * so user can't access this page while logged in */

@WebFilter (
        filterName = "LoginFilter",
        urlPatterns = { "/", "/login.jsp", "/jsp/login.jsp", "/index.jsp", "/register.jsp", "/jsp/register.jsp", },
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)

public class LoginFilter implements Filter{
	
	private String adminSendPath;
	private String studentSendPath;
	private String lecturerSendPath;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		adminSendPath = ConfigurationManager.getProperty("path.page.admin.account");
		studentSendPath = ConfigurationManager.getProperty("path.page.student.account");
		lecturerSendPath = ConfigurationManager.getProperty("path.page.lecturer.account");
	}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
    		throws IOException, ServletException {
//TODO: This code is working but made on JSPs    	
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		User currentUser = (User) httpRequest.getSession().getAttribute(GeneralConstant.SESSION_ATTR_USER);
		UserRole currentRole = currentUser.getRole();
		if (httpRequest.getAttribute(GeneralConstant.REQUEST_ATTR_DESTROY_SESSION) == null && currentRole != UserRole.GUEST) {
			String path = currentUser.getRole() == UserRole.ADMIN ? adminSendPath :
				currentUser.getRole() == UserRole.LECTURER ? lecturerSendPath : studentSendPath;
			httpRequest.getRequestDispatcher(path).forward(request, response);
		}
		else {
			chain.doFilter(request, response);
		}
    }

    @Override
    public void destroy() {
    	adminSendPath = null;  
    	studentSendPath = null;
    	lecturerSendPath = null;
    }
	
}
