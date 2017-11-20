package com.yushkev.onlinetraining.content;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.yushkev.onlinetraining.constant.GeneralConstant;


/**
 * Data class which encapsulates context content (parameters, attributes of context, 
 * session, request, and other data like file parts.
 */

public class RequestContent {

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;  
    private Map<String, Object> sessionAttributes;
    private Map<String, Object> contextAttributes;
    private Map<String, Part> fileParts;

    public RequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        sessionAttributes = new HashMap<>();
        contextAttributes = new HashMap<>();
        fileParts = new HashMap<>();
    }

    
/*    Get data from incoming request and fill RequestContent container with it*/
    public void extractValues(HttpServletRequest request) throws IOException, ServletException {

        Enumeration<String> requestAttributeNames = request.getAttributeNames();
        while (requestAttributeNames.hasMoreElements()) {
            String name = requestAttributeNames.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }

        requestParameters = request.getParameterMap();
    	
        HttpSession session = request.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String name = sessionAttributeNames.nextElement();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    	
    	ServletContext servletContext = request.getServletContext();
        Enumeration<String> contextAttributeNames = servletContext.getAttributeNames();
        while (contextAttributeNames.hasMoreElements()) {
            String name = contextAttributeNames.nextElement();
            contextAttributes.put(name, servletContext.getAttribute(name));
        }
        
        if (request.getContentType() != null &&
                request.getContentType().toLowerCase().contains("multipart/form-data")) {
            for (Part part : request.getParts()) {
                fileParts.put(part.getName(), part);
            }
        }
    }

	public void insertAttributes(HttpServletRequest request) {
		
/*  To prevent IllegalStateException:  setAttribute: Session has already been invalidated or prevent creating a new 
 * 	previously invalidated session*/
		requestAttributes.entrySet().forEach((entry) -> request.setAttribute(entry.getKey(), entry.getValue()));
        if(requestAttributes.get(GeneralConstant.REQUEST_ATTR_DESTROY_SESSION) == null) {
        	HttpSession session = request.getSession();
        	sessionAttributes.entrySet().forEach((entry) -> session.setAttribute(entry.getKey(), entry.getValue()));
        }
        ServletContext servletContext = request.getServletContext();
        contextAttributes.entrySet().forEach((entry) -> servletContext.setAttribute(entry.getKey(), entry.getValue()));
	}
	
    public Object getRequestAttribute(String name) {
        return requestAttributes.get(name);
    }

    public void setRequestAttribute(String name, Object value) {
        requestAttributes.put(name, value);
    }

    public Object getSessionAttribute(String name) {
        return sessionAttributes.get(name);
    }

    public void setSessionAttribute(String name, Object value) {
        sessionAttributes.put(name, value);
    }

    public Object getContextAttribute(String name) {
        return contextAttributes.get(name);
    }

    public void setContextAttribute(String name, Object value) {
        contextAttributes.put(name, value);
    }

    public String getRequestParameter(String name) {
        String[] values = requestParameters.get(name);
        String value = null;
        if (values != null) {
            value = values[0];
        }
        return value;
    }
    
    public Map<String, String[]> getRequestParameters() {
      return requestParameters;
    }

    public String[] getRequestParameterValues(String name) {
        return requestParameters.get(name);
    }
    
    public Part getPart(String name) {
        return fileParts.get(name);
    }
    
    public Map<String, Part> getAllParts() {
        return fileParts;
    }

   


	
}
