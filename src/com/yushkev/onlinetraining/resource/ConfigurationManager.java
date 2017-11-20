package com.yushkev.onlinetraining.resource;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConfigurationManager {
	
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/config");

    private ConfigurationManager() {}

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
    
    public static void setProperty(String key, String value) throws IOException, URISyntaxException  {
    	Properties prop = new Properties();
	    	prop.load(ConfigurationManager.class.getClassLoader().getResourceAsStream("resources/config.properties"));
	    	prop.setProperty(key, value);
	    	File file = new File(ConfigurationManager.class.getClassLoader().getResource("resources/config.properties").toURI());

			FileOutputStream fileOut = new FileOutputStream(file);
		   	prop.store(fileOut,null);
		    fileOut.close();
    }

}
