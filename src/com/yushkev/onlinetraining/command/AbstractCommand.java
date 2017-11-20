package com.yushkev.onlinetraining.command;

import com.yushkev.onlinetraining.content.ActionResult;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.exception.LogicCommandException;

public  abstract class AbstractCommand {
   
    public abstract ActionResult execute(RequestContent requestContent) 
    		throws LogicCommandException;


    
    
}
