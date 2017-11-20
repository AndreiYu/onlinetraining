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

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.resource.ConfigurationManager;


/*Filter to block access for banned users*/ //TODO: Keep in separate filter or remove????

@WebFilter (
	     filterName = "BanFilter",
	     urlPatterns = { "/*" },
	     dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)

public class BanFilter implements Filter{

	private String redirectPath;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		redirectPath = ConfigurationManager.getProperty("path.page.contact_page");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String errorMessage = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponce = (HttpServletResponse) response;
		User currentUser = (User) httpRequest.getSession().getAttribute(GeneralConstant.SESSION_ATTR_USER);
		/* allow not registered users visit several pages like login, registration*/
		if (currentUser == null || currentUser != null && currentUser.isActive()) {
			filterChain.doFilter(request, response);
		}
		else {
			errorMessage = "message.error.is_banned";
			httpRequest.getSession().setAttribute(GeneralConstant.INFO_MESSAGE, errorMessage); 
			httpResponce.sendRedirect(httpRequest.getContextPath() + redirectPath);
		}
	
	}
	
	
	@Override
	public void destroy() {
		redirectPath = null;
	}

}
