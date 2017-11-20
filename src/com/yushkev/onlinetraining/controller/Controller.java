package com.yushkev.onlinetraining.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.command.AbstractCommand;
import com.yushkev.onlinetraining.command.ActionFactory;
import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.exception.LogicCommandException;


@WebServlet("/controller")

@MultipartConfig(fileSizeThreshold = GeneralConstant.AVATAR_IMG_MAXSIZE, // the size threshold at which the file will be written to the disk
maxFileSize = GeneralConstant.AVATAR_IMG_MAXSIZE // the maximum size allowed for uploaded files (in bytes). If the size of any uploaded file is greater than this size, the web container will throw an exception (IllegalStateException).
//maxRequestSize
)
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(Controller.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		processRequest(request, response);
	}  
	 
	private void processRequest(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestContent requestContent = new RequestContent();
		requestContent.extractValues(request); //in order to define command and execute it
		try { 
			ActionFactory factory = new ActionFactory();
			AbstractCommand executionCommand = factory.defineCommand(requestContent);
//			if (executionCommand != null) {			
				ActionResult actionResult =	executionCommand.execute(requestContent);
				requestContent.insertAttributes(request); 
				switch (actionResult.getType()) {
				case FORWARD:					System.out.println("ACTIONRESULT FROWSRD TO: " + actionResult.getPage());////////////////////////////////////////	
					request.getRequestDispatcher(actionResult.getPage()).forward(request, response);
					break;
				case REDIRECT:					System.out.println("ACTIONRESULT REDIRECT TO: " + actionResult.getPage());////////////////////////////////////////
					response.sendRedirect(request.getContextPath() + actionResult.getPage());	
					break;
//				}
				}
/*			Case command was undefined - forward to 404 error page*/
//			else {
//				request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error_404")).forward(request, response);
//			}
		}
		catch (LogicCommandException e) {
			logger.log(Level.ERROR, "LogicCommand exception occured due to: " + e);//TODO: 
			throw new ServletException(e); //TODO: Another handle? Is good to throw ServletException & Log???
			
//			request.setAttribute(GeneralConstant.INFO_MESSAGE, e.getMessage());
//			request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error")).forward(request, response);
		}
		
	}
	
}
