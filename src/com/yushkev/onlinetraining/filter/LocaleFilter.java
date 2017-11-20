package com.yushkev.onlinetraining.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yushkev.onlinetraining.constant.GeneralConstant;

/*Filter to set user locale based on incoming request or saved cookies, 
 * or setting default locale based on "Accept-Language header" of request
 * If there is no cookies, but locale changed by request - filter writes cookies in response*/

@WebFilter (
       filterName = "LocaleFilter",
       urlPatterns = { "/*" },
       dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class LocaleFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponce = (HttpServletResponse) response;
		Locale currentLocale = (Locale) httpRequest.getSession().getAttribute(GeneralConstant.LOCALE);
		String requestLocale = httpRequest.getParameter(GeneralConstant.LOCALE);
/*		Returns the preferred Locale that the client will accept content in, based on the Accept-Language header. 
		If the client request doesn't provide an Accept-Language header, this method returns the default locale for the server.
*/		Locale newLocale = httpRequest.getLocale();
		String[] localeParamStrings = null;
		
		if (requestLocale != null && !requestLocale.isEmpty()) {
/*			set new locale and write cookie if request to change locale comes*/
			localeParamStrings = httpRequest.getParameter(GeneralConstant.LOCALE).split("_");
			newLocale = (localeParamStrings.length == 2) ? (new Locale(localeParamStrings[0], 
					localeParamStrings[1])) : currentLocale;
/*			set locale cookies when request for changing locale comes*/
			Cookie cookie = new Cookie(GeneralConstant.LOCALE, newLocale.toString());
			cookie.setMaxAge(GeneralConstant.COOKIE_LIFETIME);
			httpResponce.addCookie(cookie);
		}
		
		else if (currentLocale == null) {
/*			search locale cookies if there is no current locale set*/
			Cookie [] localeCookies = httpRequest.getCookies(); //avoid NullPEx when calling stream
	        Cookie localeCookie = localeCookies != null ? Arrays.stream(localeCookies)
	                .filter(c -> c.getName().equals(GeneralConstant.LOCALE)).findAny().orElse(null) : null;
			if (localeCookie != null) {
				localeParamStrings = localeCookie.getValue().split("_");
				newLocale = (localeParamStrings.length == 2) ? (new Locale(localeParamStrings[0], 
						localeParamStrings[1])) : newLocale;
			} 
		}

		else if (requestLocale == null || requestLocale.isEmpty() && currentLocale != null) {
/*			if there is no request to change locale and session already has locale set new locale = current */
			newLocale = currentLocale;
		}
	
		httpRequest.getSession().setAttribute(GeneralConstant.LOCALE, newLocale);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}



}
