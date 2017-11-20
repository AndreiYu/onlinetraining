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

import com.yushkev.onlinetraining.command.ActionFactory;
import com.yushkev.onlinetraining.command.CommandType;
import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.entity.User;

//Filter to avoid invoking commands by adding command names in adressbar (for ex. adding "/controller?command=commandName" )
//Если мы напрямую переходим на страницу администратора через строку браузера .../admin.jsp, то ее ловит обычный фильтр
//Если же мы переходим туда через команду, которая форвардом переходит на страницу администратора, то обычный фильтр ее не словит
//т.е. .../Servlet?command=toAdminPage - пройдем обычный фильтр, но попадем на текущий, и если прав нет, то дальше не пустит

@WebFilter (
	     filterName = "CommandAccessFilter",
	     urlPatterns = { "/controller" },
	     dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class CommandAccessFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponce = (HttpServletResponse) response;
		User currentUser = (User) httpRequest.getSession().getAttribute(GeneralConstant.SESSION_ATTR_USER);
		CommandType currentCommandType = (new ActionFactory()).defineCommand(requestContent)
				getParameter(GeneralConstant.PARAM_COMMAND);//TODO:///////////////////
        String queryString = httpRequest.getQueryString();
        String urlQuery = httpRequest.getRequestURI() + ((queryString != null) ? ("?" + queryString) : "");
        if (urlQuery.startsWith("/*")) { //TODO:check!
            chain.doFilter(request, response);
        } else 
        if (urlQuery.matches(URI_REGEX)) {
            chain.doFilter(request, response);
        } else {
        	String referrer = (String) httpRequest.getSession().getAttribute(GeneralConstant.FROM_PAGE_PATH);
        	httpRequest.getRequestDispatcher(referrer).forward(request, response);
        }
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		
	}

}
