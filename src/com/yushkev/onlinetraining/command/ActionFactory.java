package com.yushkev.onlinetraining.command;

import java.util.Arrays;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;

public class ActionFactory {

	/**
	 * Method to define command class.
	 *
	 * @param requestContent information received from {@link RequestContent}
	 * @return current command type object or Empty command if command not found or 
	 * user doesn't have rights to perform it.
     */
    public AbstractCommand defineCommand(RequestContent requestContent) {
        AbstractCommand command = CommandType.EMPTY.getCurrentCommand();

            String commandName = requestContent.getRequestParameter(GeneralConstant.PARAM_COMMAND);
            if (commandName != null && !commandName.isEmpty()) {
            	command = Arrays.stream(CommandType.values()).filter((a) -> a.name()
            			.equals(commandName.toUpperCase())).findFirst()
            			.map((a) -> checkActionRight(a, requestContent))
            			.orElse(CommandType.EMPTY).getCurrentCommand();
            }
	        return command;
    }
    
    
    /**
     * Utility function to check {@code User} right to call this command.
     * @param commandType instance of {@code CommandType} defined by {@code Actionfactory}
     * @param requestContent instance of {@code RequestContent} that came to {@code Actionfactory}
     * @return instance of {@link CommandType} if Acess is allowed, or else Null
     */
    private CommandType checkActionRight(CommandType commandType, RequestContent requestContent) {
    	User user = (User)requestContent.getSessionAttribute(GeneralConstant.SESSION_ATTR_USER);
    	UserRole userRole = user.getRole();
    	return commandType.getUserRoles().contains(userRole) ? commandType : null;
    }
    
    
}
	

