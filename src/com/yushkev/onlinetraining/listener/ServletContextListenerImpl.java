package com.yushkev.onlinetraining.listener;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.yushkev.onlinetraining.connection.ConnectionPool;
import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.exception.PoolException;
import com.yushkev.onlinetraining.resource.ConfigurationManager;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener{

	//TODO: Another way to handle exceptions??
	
	private static Logger logger = LogManager.getLogger(ServletContextListenerImpl.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		/*set realPath for binding to RequestContent and img uploading*/
		String realPath = sce.getServletContext().getRealPath("");
/*		store realpath in config.properties*/
		try {
//			ConfigurationManager.setProperty(GeneralConstant.SERVLET_CONTEXT_ATTR_REAL_PATH, realPath);//TODO: set property TO PROPERTIES FILE!!!!!!!!!!!!!!!!!!!!!!!
			sce.getServletContext().setAttribute(GeneralConstant.SERVLET_CONTEXT_ATTR_REAL_PATH, realPath);
			ConnectionPool.getInstance();
		} catch (PoolException e) {
			logger.log(Level.FATAL, "Couldn't get instance of connection pool when trying to init! PoolException occured: " + e);
			throw new RuntimeException(e);
		}
//		catch (IOException |URISyntaxException e) {
//			logger.log(Level.ERROR, "Couldn't write realpath to properties file: " + e);
//		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			ConnectionPool.getInstance().close();
		} catch (PoolException e) {
			logger.log(Level.ERROR, "Couldn't get instance of connection pool when trying to close! PoolException occured: " + e);
		}
		
	}

}
