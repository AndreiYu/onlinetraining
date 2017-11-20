package com.yushkev.onlinetraining.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;
import com.yushkev.onlinetraining.resource.ConfigurationManager;

/* Filter to prevent direct access to jsp pages and controller from adressbar 
 * also is called if user comes from saved bookmark (ending ".jsp" or "/controller") in his browser, so he is forwarded to main page
 * as 'referer' validation is not 100% safe, another filters and ways of protection are implemented
 * p.s. It will/may be empty when the enduser
 * == entered the site URL in browser address bar itself.
 * == visited the site by a browser-maintained bookmark.
 * == visited the site as first page in the window/tab.
 * == switched from a https URL to a http URL.
 * == switched from a https URL to a different https URL.
 * == has security software installed (antivirus/firewall/etc) which strips the referrer from all requests.
 * == is behind a proxy which strips the referrer from all requests.
 * == visited the site programmatically (like, curl) without setting the referrer header (searchbots).*/

@WebFilter (
        filterName = "DirectAccessFilter",
        urlPatterns = { "/jsp/admin/*", "/jsp/student/*","/jsp/lecturer/*", "/controller*"}
)

public class DirectAccessFilter implements Filter{
	
	private static Logger logger = LogManager.getLogger(DirectAccessFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) 
    		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String referrer = (String) httpRequest.getHeader("referer");
		if (referrer != null) {
			filterChain.doFilter(request, response);
		}
		else {
			referrer = ConfigurationManager.getProperty("path.page.index");
			User currentUser = (User) httpRequest.getSession().getAttribute(GeneralConstant.SESSION_ATTR_USER); 			
			logger.log(Level.WARN,"User with IP: " + request.getRemoteAddr() + ", login: "  + ((currentUser.getRole() != UserRole.GUEST) ? currentUser.getLogin() : 
				"anonimous user") + " tried to access jsp or controller directly");
			httpRequest.getRequestDispatcher(referrer).forward(request, response);
		}
    }

    @Override
    public void destroy() {
        
    }
	
}
