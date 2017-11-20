package com.yushkev.onlinetraining.customtag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.yushkev.onlinetraining.constant.GeneralConstant;

public class DisplayMessageTag extends TagSupport{

	/**
	 * Tag to print messages attached to request or session (if present) and remove them after that
	 */
	private static final long serialVersionUID = 1L;
	
	private String info_message; 

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() {
		HttpSession session = pageContext.getSession();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
/*		try get message from request*/
		ArrayList<String> message = (ArrayList<String>) request.getAttribute(GeneralConstant.INFO_MESSAGE);
/*		try get message from session*/
		if (message == null || message.isEmpty()) {
			message = (ArrayList<String>) session.getAttribute(GeneralConstant.INFO_MESSAGE);
		}
		if (message != null && !message.isEmpty()) {
			JspWriter out = pageContext.getOut();	
			Locale sessionLocale = (Locale)session.getAttribute(GeneralConstant.LOCALE);
			ResourceBundle bundle = ResourceBundle.getBundle("resources.localization.pagecontent", sessionLocale);
			
				((Iterable<String>) message).forEach(item->{
					if (item != null) { 
						try {
							out.print("<div class=\"alert alert-");
							if (item.contains("wrong") || item.contains("error")) {
								out.print("danger\"> "); 
							}
							else {
								out.print("success\"> ");
							}
							out.print("<p><strong>");
							out.print(bundle.getString((String) item));
							out.print("</strong></p></div>");
	
						}
						catch (IOException e) {
							throw new IllegalArgumentException(e); //Unchecked exception
						}
					}
				});
		}
				
				session.removeAttribute(GeneralConstant.INFO_MESSAGE);
/*		in case request will be forwarded along the way, remove attribute*/
				request.removeAttribute(GeneralConstant.INFO_MESSAGE);

		

		return SKIP_BODY;
	}

	public String getInfo_message() {
		return info_message;
	}

	public void setInfo_message(String info_message) {
		this.info_message = info_message;
	}

	
	
	
	
	

}
