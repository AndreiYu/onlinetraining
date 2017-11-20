package com.yushkev.onlinetraining.connection;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;





/**
 * Package-private class to hide ConnectionConfig properties from outside.
 */
class ConnectionConfig {
	
	private static Logger logger = LogManager.getLogger(ConnectionConfig.class);

	private static final String DEFAULT_DB_URL = "jdbc:mysql://localhost:3306/onlinetrainingdb?useUnicode=true&characterEncoding=UTF-8&useSSL=true";
	private static final String DEFAULT_DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DEFAULT_DB_USER = "root";
	private static final String DEFAULT_DB_PASSWORD = "admin";
	private static final String DEFAULT_DB_MIN_POOLSIZE = "3";
	private static final String DEFAULT_DB_MAX_POOLSIZE = "10";
	
	private String url_db;
	private String driver_db;
	private String user_db;
	private String pass_db;
	private int min_poolSize;
	private int max_poolSize;
	
	ConnectionConfig() {
		initData();
	}
	
	String getUrl_db() {
		return url_db;
	}

	String getDriver_db() {
		return driver_db;
	}

	String getUser_db() {
		return user_db;
	}

	String getPass_db() {
		return pass_db;
	}

	int getMin_poolSize() {
		return min_poolSize;
	}

	int getMax_poolSize() {
		return max_poolSize;
	}

	/**
	 * Inits configuration DATA to establish connection with database
	 * if unable to load config file, default values are set 
	 */
	void initData() {
		Properties property = new Properties();
		try {
			property.load(getClass().getClassLoader().getResourceAsStream("resources/database.properties"));
		} catch (IOException e) {
			logger.log(Level.ERROR, "Couldn't load configuration data for connecting to DataBase. Default values were loaded: ", e);
		}
		url_db = property.getProperty("db.url", DEFAULT_DB_URL);
		driver_db = property.getProperty("db.driver", DEFAULT_DB_DRIVER);
		user_db = property.getProperty("db.user", DEFAULT_DB_USER);
		pass_db = property.getProperty("db.password", DEFAULT_DB_PASSWORD);
		min_poolSize = Integer.parseInt(property.getProperty("db.minpoolsize", DEFAULT_DB_MIN_POOLSIZE));
		max_poolSize = Integer.parseInt(property.getProperty("db.maxpoolsize", DEFAULT_DB_MAX_POOLSIZE));

	}
	
	
	
	
	
}

