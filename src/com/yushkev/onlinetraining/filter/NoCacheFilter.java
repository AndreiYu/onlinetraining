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
import javax.servlet.http.HttpServletResponse;

import com.yushkev.onlinetraining.constant.GeneralConstant;

/**
 * Filter to prevent user return to cached page content after logout
 * and session invalidation by pressing back/forward button of browser
 */
@WebFilter (
        filterName = "NoCacheFilter",
        urlPatterns = { "/*"}
)
public class NoCacheFilter implements Filter{
	
	
    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        if (req.getSession(false).getAttribute(GeneralConstant.SESSION_ATTR_USER) == null)	 {
        	res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        	res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        	res.setDateHeader("Expires", 0); // Proxies
        }
            chain.doFilter(req, res);  
    }
   
    
    @Override
    public void destroy() {
    
    }
    
    


	
	
}
