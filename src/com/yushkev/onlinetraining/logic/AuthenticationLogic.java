package com.yushkev.onlinetraining.logic;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

import com.yushkev.onlinetraining.dao.DaoFactory;
import com.yushkev.onlinetraining.dao.UserDaoImpl;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.exception.DAOException;
import com.yushkev.onlinetraining.exception.LogicCommandException;

public class AuthenticationLogic {
	
    
    public static Optional<User> authenticate(String login, String password) throws LogicCommandException {
    	Optional<User> user = Optional.empty();
    	boolean passwordValid = false;

        if (login != null && password != null) {
        	try (UserDaoImpl dao = (UserDaoImpl) DaoFactory.getInstance().getUserDao()){
        		user = dao.getEntityByKey(login);
        		if (user.isPresent()) {
            		String hash = dao.getPasswordByLogin(login); //one connection is used
        			passwordValid = BCrypt.checkpw(password, hash);
        		}
        	} catch (DAOException e) {
               throw new LogicCommandException("An exception occured. Couldnt't authenticate user: " + login + "   " + e);
            } 
        }
         return ((user.isPresent() && passwordValid) ? user : Optional.empty());
    }
    
    public static boolean checkLoginExists(String login) throws LogicCommandException {
    	boolean result = false;
    	if (login != null) {
    		try (UserDaoImpl dao = (UserDaoImpl) DaoFactory.getInstance().getUserDao()){
        		result = dao.getEntityByKey(login).isPresent();
      		} catch (DAOException e) {
    			throw new LogicCommandException("An exception occured. Couldnt't check login exist for user: " + login + "   " + e);
			}
    	}
		return result;
    }
    
    public static boolean checkEmailExists(String email) throws LogicCommandException {
    	boolean result = false;
    	if (email != null) {
    		try (UserDaoImpl dao = (UserDaoImpl) DaoFactory.getInstance().getUserDao()){
        		result = dao.getEmailByEmail(email).isPresent();
      		} catch (DAOException e) {
    			throw new LogicCommandException("An exception occured. Couldnt't check email existance: " + email + "   " + e);
			}
    	}
		return result;
    }
    
    public static boolean checkPasswordIsValid(String login, String password) throws LogicCommandException {
    	boolean passwordValid = false;
    	if (login !=null && password != null) {
    		try (UserDaoImpl dao = (UserDaoImpl) DaoFactory.getInstance().getUserDao()){
        			String hash = dao.getPasswordByLogin(login);
        			passwordValid = (hash != null) ? BCrypt.checkpw(password, hash) : false;
        		}
    		catch (DAOException e) {
               throw new LogicCommandException("An exception occured. Couldnt't authenticate user: " + login + "   " + e);
    		}
    	}
		return passwordValid;
    }
 

    
    
    
    
    
    
    

}
    
    
//    https://stackoverflow.com/questions/9722388/how-to-use-jbcrypt-for-password-hash-comparison
//    BCrypt.checkpw(dbhash)
//	BCrypt.hashpw(password, BCrypt.gensalt());
//    The database field the pw_hash was stored in was 80 characters. This was 20 characters more than a BCrypt hash. Trimming the hash or resetting the database field to 60 characters worked.
    
//    public static boolean checkLoginExist(String login) {
//        boolean result = false;
//        ConnectionPool pool = ConnectionPool.getInstance();
//        try (ProxyConnection connection = pool.getConnection()) {
//
//            UserDAO userDAO = new UserDAO(connection);
//            Optional<String> loginOptional = userDAO.selectLogin(enterLogin);
//            if (loginOptional.isPresent()) {
//                result = true;
//            }
//    }
//        
//        public static boolean checkEmailExist(String enterEmail) throws BusinessLogicException {
//            boolean result = false;
//            ConnectionPool pool = ConnectionPool.getInstance();
//            try (ProxyConnection connection = pool.getConnection()) {
//
//                UserDAO userDAO = new UserDAO(connection);
//                Optional<String> emailOptional = userDAO.selectEmailByItself(enterEmail);
//                if (emailOptional.isPresent()) {
//                    result = true;
//                }
//
//            } catch (ConnectionPoolException | DAOException e) {
//                throw new BusinessLogicException("Problem when check existence of email: ", e);
//            }
//            return result;
//        }
//

//    
//        public static void setNewPassword(String login, String cryptoPassword) throws BusinessLogicException {
//            ConnectionPool pool = ConnectionPool.getInstance();
//            try (ProxyConnection connection = pool.getConnection()) {
//                UserDAO userDAO = new UserDAO(connection);
//                userDAO.updatePassword(login, cryptoPassword);
//
//            } catch (ConnectionPoolException | DAOException e) {
//                throw new BusinessLogicException("Problem when set new password: ", e);
//            }
//        }
    
    


//    /**
//     * Perform logout
//     * @param session
//     */
//    public static void logout(HttpSession session){
//        session.invalidate();
//    }
//
//    /**
//     * Get account from session
//     * @param request
//     * @return
//     */
//    public static Account account(HttpServletRequest request){
//        HttpSession session = request.getSession();
//        Object ob = session.getAttribute(SESSION_VAR);
//        return (ob != null && ob.getClass().equals(Account.class)) ? (Account) ob : null;
//    }

