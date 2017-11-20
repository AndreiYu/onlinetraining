package com.yushkev.onlinetraining.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.yushkev.onlinetraining.constant.GeneralConstant;

/**
 * Filter to set session attribute "path of the page from which request came".
 * Since sending httpRequest.getHeader("referer") can be blocked by firewalls or browser configuration, 
 * it can't be 100% used to determine From page
 */

@WebFilter (
        filterName = "RequestPageFilter",
        urlPatterns = { "/*"},
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class RequestPageFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
				
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        // get the original url out of the page when a forward has taken place.
        String requestURI = (String) httpRequest.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
        if(requestURI == null || requestURI.isEmpty())  {
            requestURI = httpRequest.getRequestURI();
        }
        
        String queryString = (String) httpRequest.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);

		if (queryString == null || queryString.isEmpty()) {
        	queryString = httpRequest.getQueryString();
        	queryString = (queryString != null && !queryString.isEmpty()) ? '?' + queryString : "";
        }

        String fromPagePath = requestURI + queryString;
/*prevent creating a new session after invalidating and redirecting to index page*/
        httpRequest.getSession(false).setAttribute(GeneralConstant.FROM_PAGE_PATH, fromPagePath);

chain.doFilter(request, response);
	
	}

	@Override
	public void destroy() {
				
	}

}
