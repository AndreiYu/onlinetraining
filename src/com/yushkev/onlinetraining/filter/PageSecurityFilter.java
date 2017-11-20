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
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;
import com.yushkev.onlinetraining.resource.ConfigurationManager;
import com.yushkev.onlinetraining.service.MessageService;

/* Filter to avoid access to admin, student and lecturer content without proper User role
 * and block access for deactivated users. Since "httpRequest.getHeader("referer")" is not 100% reliable 
 * as it can be modified on client side
*/
@WebFilter (
     filterName = "PageSecurityFilter",
     urlPatterns = { "/jsp/admin/*", "/jsp/lecturer/*", "/jsp/student/*" },
     dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)

public class PageSecurityFilter implements Filter{
	
//	private static Logger logger = LogManager.getLogger(PageSecurityFilter.class);
	
	private String redirectPath;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		redirectPath = ConfigurationManager.getProperty("path.page.contact_page"); 
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
//TODO: This code is working!
		String message = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponce = (HttpServletResponse) response;
/*prevent creating a new session after invalidating and redirecting to index page*/		
		User currentUser = (User) httpRequest.getSession().getAttribute(GeneralConstant.SESSION_ATTR_USER); 
/*		if user is not logged in, his role is GUEST*/
		UserRole currentRole = currentUser.getRole();
/*		if user is not logged in, his activation status is "deactivated" by default (but it is not needed in this if-if logic now...)*/
		boolean isActive = (currentRole != UserRole.GUEST) ? currentUser.isActive() : false;
		String currentURI = httpRequest.getRequestURI();
		UserRole accessRole = currentURI.matches(".*?/admin/.*") ? UserRole.ADMIN : 
						currentURI.matches(".*?/lecturer/.*") ? UserRole.LECTURER :
						currentURI.matches(".*?/student/.*") ? UserRole.STUDENT :
						UserRole.GUEST;
		if (!currentRole.equals(accessRole)) {
			message = "message.error.access_role";
			logger.log(Level.WARN,"Attempt to access page " + accessRole.toString() + " by: " + ((currentRole != UserRole.GUEST) ? currentUser.getLogin() : "anonimous user"));
			processAction(httpRequest, httpResponce, message);
        }
/*		 not logged in users are allowed to see pages that are not in (admin, lecturer, student) folders
//		 banned users are not allowed to see pages in their admin, lecturer, student directories*/
		else if (currentRole != UserRole.GUEST && !isActive) {
			message = "message.error.is_banned";
			logger.log(Level.INFO,"User: " + ((currentRole != UserRole.GUEST) ? currentUser.getLogin() : "anonimous user") + " is deactivated and redirected to contact page");
			processAction(httpRequest, httpResponce, message);
        }
		else {
        	filterChain.doFilter(request, response);
       }
	}
	
	@Override
	public void destroy() {
		redirectPath = null;
	}
	
	private void processAction (HttpServletRequest httpRequest, HttpServletResponse httpResponce, String errorMessage) throws IOException {
		MessageService.addSessionMessage(httpRequest, GeneralConstant.INFO_MESSAGE, errorMessage); 
		httpResponce.sendRedirect(httpRequest.getContextPath() + redirectPath);
	}



}
