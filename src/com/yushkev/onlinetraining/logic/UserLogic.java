package com.yushkev.onlinetraining.logic;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import com.sun.javafx.collections.MappingChange.Map;
import com.yushkev.onlinetraining.dao.DaoFactory;
import com.yushkev.onlinetraining.dao.UserDaoImpl;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;
import com.yushkev.onlinetraining.exception.DAOException;
import com.yushkev.onlinetraining.exception.LogicCommandException;
import com.yushkev.onlinetraining.validator.DataValidator;

public class UserLogic {
	
    public static User createNewUser(String login, String password, String role, String first_name, String last_name, 
    		String email, String avatarPath) throws LogicCommandException {
    	try (UserDaoImpl dao = (UserDaoImpl) DaoFactory.getInstance().getUserDao()) {
    		String hash = BCrypt.hashpw(password, BCrypt.gensalt());
    		UserRole userRole = UserRole.valueOf(role.toUpperCase());
    		Integer userId = dao.create (new User(login, hash, userRole, first_name, last_name, email, true, avatarPath));
   /* 		return User without password to store in session*/
    		User user = new User(login, userRole, first_name, last_name, email, true, avatarPath); 
    		user.setId(userId); // bind userId to user
    		return user;
		} catch (DAOException e) {
				throw new LogicCommandException("An exception occured. Couldnt't create user: " + login + "   " + e);
		}
    }

	public static Optional<User> getUser(String login) throws LogicCommandException {
    	Optional<User> user = Optional.empty();
        if (login != null) {
        	try (UserDaoImpl dao = (UserDaoImpl) DaoFactory.getInstance().getUserDao()){
        		user = dao.getEntityByKey(login);
           	} catch (DAOException e) {
               throw new LogicCommandException("An exception occured. Couldnt't get user: " + login + e);
            } 
        }
         return ((user.isPresent() ? user : Optional.empty()));
    }
	
	/*	Utility method to receive errormessage from validating input data */
	public static String getMessageFromValidation (String login, String password, String firstName, String lastName, String email, String confirm_password) {
	String errorMessage = !DataValidator.isValidToRegex(login, DataValidator.LOGIN_REGEX) ? "message.signup.wrong_login" :
		!DataValidator.isValidToRegex(password, DataValidator.PASSWORD_REGEX) ? "message.signup.wrong_password" :
			(!DataValidator.isValidToRegex(firstName, DataValidator.NAME_REGEX) ||
					!DataValidator.isValidToRegex(lastName, DataValidator.NAME_REGEX)) ? "message.signup.wrong_name" :
						!DataValidator.isValidToRegex(email, DataValidator.EMAIL_REGEX) ? "message.signup.wrong_email" :
							password != confirm_password ? "message.signup.wrong_passwords" : 
								null;
		return errorMessage;
	}

}
