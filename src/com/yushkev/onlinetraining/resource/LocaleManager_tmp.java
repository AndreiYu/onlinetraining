package com.yushkev.onlinetraining.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum LocaleManager_tmp {

	INSTANCE;
	
	private ResourceBundle resourceBundle;
	private final String resourceName = "localization.pagecontent";
	private LocaleManager_tmp() {
		resourceBundle = ResourceBundle.getBundle(resourceName, Locale.getDefault());
	}
	public void changeLocale(Locale locale) {
		resourceBundle = ResourceBundle.getBundle(resourceName, locale);
	}
	public String getString(String key) {
		return resourceBundle.getString(key);
	}
	
	
	
}
