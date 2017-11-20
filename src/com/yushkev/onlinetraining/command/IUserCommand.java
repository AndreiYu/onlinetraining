package com.yushkev.onlinetraining.command;

import com.yushkev.onlinetraining.content.RequestContent;

public interface IUserCommand{

	void logIn(RequestContent requestContent);
	void logOut(RequestContent requestContent);
	void signUp(RequestContent requestContent);
	
}
