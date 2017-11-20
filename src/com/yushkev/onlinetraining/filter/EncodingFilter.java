package com.yushkev.onlinetraining.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/*Filter setting request&response encoding.*/

@WebFilter (
        filterName = "EncodingFilter",
        urlPatterns = { "/*" },
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding parameter")}
)

public class EncodingFilter implements Filter{

	private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
    		throws IOException, ServletException {
    	
    	String codeRequest = request.getCharacterEncoding();
        if (encoding != null && !encoding.equalsIgnoreCase(codeRequest)) {
	       	request.setCharacterEncoding(encoding);
	        response.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
	
}
