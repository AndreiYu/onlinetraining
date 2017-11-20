package com.yushkev.onlinetraining.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.dao.interfacedao.IUserDao;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.entity.enumtype.UserRole;
import com.yushkev.onlinetraining.exception.DAOException;

import jdk.nashorn.internal.ir.WithNode;

public class UserDaoImpl extends AbstractDAO<String, User> implements IUserDao{

	

	private static final String SQL_SELECT_ALL_USERS = "SELECT id, login, role, first_name, last_name, "
			+ "email, is_active, avatar_path FROM user";
    private static final String SQL_SELECT_USER_BY_LOGIN = "SELECT id, login, role, first_name, last_name, "
    		+ "email, is_active, avatar_path FROM user WHERE login = ?";
    private static final String SQL_SELECT_PASSWORD_BY_LOGIN = "SELECT password FROM user WHERE login = ?";
	private static final String SQL_SELECT_EMAIL = "SELECT email FROM user WHERE email = ?";
    private static final String SQL_DELETE_USER_BY_LOGIN = "DELETE FROM user WHERE login = ?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET login = ?, role = ?, first_name = ?, "
    		+ "last_name = ?, email = ?, is_active = ?, avatar_path = ? WHERE login = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE login = ?";
//    private static final String SQL_INSERT_USER = "INSERT INTO user (login, role, first_name, last_name, email, "
//    		+ "is_active, avatar_path) values (?,?,?,?,?,?,?)";
	private static final String SQL_INSERT_USER_WITH_PASSWORD = "INSERT INTO user (login, password, role, first_name, "
			+ "last_name, email, is_active, avatar_path) values (?,?,?,?,?,?,?,?)";
    
    public UserDaoImpl() throws DAOException {
		super();

	}
    
    /*without passwords*/
	@Override
	public List<User> getAll() throws DAOException {
		return getAll(SQL_SELECT_ALL_USERS);
	}
    
    /*without password*/
	@Override
	public Optional<User> getEntityByKey(String login) throws DAOException {
		return getEntityByKey(login, SQL_SELECT_USER_BY_LOGIN);
	}

	@Override
    public boolean delete(String login) throws DAOException {
		return delete(login, SQL_DELETE_USER_BY_LOGIN);
    }
    
    /*without password*/
	@Override
	public Integer update(User user) throws DAOException {
		return update(user, SQL_UPDATE_USER);	
	}
    
//	@Override
//	public boolean create(User user) throws DAOException {
//			try (PreparedStatement statement = 
//					(user.getPassword() == null) ? this.connection.prepareStatement(SQL_INSERT_USER) : 
//				this.connection.prepareStatement(SQL_INSERT_USER_WITH_PASSWORD)) { 
//				fillStatement(statement, user);
//		     	return statement.executeUpdate() > 0;
//		    }
//			catch (SQLException e) { 
//				throw new DAOException("Unable to create user: " + user.getLogin() + "   " + e);
//		    }
//	}

	@Override
	public Integer create(User user) throws DAOException {
		return create(user, SQL_INSERT_USER_WITH_PASSWORD);
	}	
	
	@Override
	protected User fillEntity(ResultSet resultSet) throws DAOException {
		User user = new User();
		try {
			   user.setId(resultSet.getInt("id"));
			   user.setLogin(resultSet.getString("login"));	
//			   user.setPassword(resultSet.getString("password"));	//XXX: WITHOUT A PASSWORD, but SQL query dont call Password from DB 
			   user.setRole(UserRole.valueOf(resultSet.getString("role").toUpperCase()));
			   user.setFirst_name(resultSet.getString("first_name"));
			   user.setLast_name(resultSet.getString("last_name"));
			   user.setEmail(resultSet.getString("email"));
			   user.setActive(resultSet.getBoolean("is_active"));
			   user.setAvatar_path(resultSet.getString("avatar_path"));
		}
		catch (SQLException e) {
			throw new DAOException("Unable to fill entity " + e);
		}
		return user;
	}
	
	@Override
	protected void fillStatement (PreparedStatement statement, User entity) throws DAOException {
		try {
			int i = 1;
			statement.setString(i++, entity.getLogin());
		/*	with password */
			if (entity.getPassword() != null) {
				statement.setString(i++, entity.getPassword());
			}
		/*	without password */
				statement.setString(i++, entity.getRole().name().toLowerCase());
		        statement.setString(i++, entity.getFirst_name());
		        statement.setString(i++, entity.getLast_name());
		        statement.setString(i++, entity.getEmail());
		        statement.setBoolean(i++, entity.isActive()); 
	        	statement.setString(i++, entity.getAvatar_path());
		} catch (SQLException e) { 
			throw new DAOException("Unable to fill Statement for: " + entity.getLogin() + "   " + e);
	    }
	}
    
	
	public String getPasswordByLogin(String login) throws DAOException {
	    String password = null;   
		try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN)){
	    	   statement.setString(1, login);
	    	   try (ResultSet resultSet = statement.executeQuery()) { 
	    		   if (resultSet.next()) {
	    			   password = resultSet.getString(GeneralConstant.PASSWORD);
	    		   }
	    	   } 
	       }
	       catch (SQLException e) {
		    	 throw new DAOException("Unable to get password for user: " + login + "   " + e);
		   }
		return password;
	}
	
	public Optional<String> getEmailByEmail(String email) throws DAOException {
	    String result = null;   
		try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_EMAIL)){
	    	   statement.setString(1, email);
	    	   try (ResultSet resultSet = statement.executeQuery()) { 
	    		   if (resultSet.next()) {
	    			   result = resultSet.getString(GeneralConstant.EMAIL);
	    		   }
	    	   } 
	       }   
	       catch (SQLException e) {
	    	 throw new DAOException("Unable to get email: " + email + "   " + e);
		}
		return Optional.ofNullable(result);
	}
	
	public boolean updatePassword(User user, String password) throws DAOException {
		try (PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE_PASSWORD)){
	    	   statement.setString(1, password);
	    	   statement.setString(2, user.getLogin());
	    	   return statement.executeUpdate() > 0;
	       }   
	       catch (SQLException e) {
	    	 throw new DAOException("Unable to update password for : " + user.getLogin() + "   " + e);
		}
	}
	

	
	
//		return Optional.ofNullable(findOne(set -> {
//			try {
//				return new User(
//							set.getInt("id"),set.getString("login"),UserRole.valueOf(set.getString("role")),
//				            set.getString("first_name"),set.getString("last_name"),set.getString("e-mail"),
//				            Boolean.valueOf(set.getString("is_active")),set.getString("avatar_path"));
//			} catch (SQLException e) {
//				logger.log(Level.WARN, "Unable to execute getEntityByKeymethod, can't get parameters from resultSet due to dataBase problem: ", e);
//			}
//			return null;
//		},
//				 SQL_SELECT_USER_BY_KEY, login));
	
//	}
//
//	@Override
//	public Optional<User> getByLoginAndPassword(String login, String password) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<User> getAllLecturers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<User> getAllStudents() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean updateWithoutPassword(User user) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean checkLogin(String login) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean setActive(User user) {
//		// TODO Auto-generated method stub
//		return false;
//	}



	// may really return Null if not found in database
//	@Override
//	protected Optional<User> getEntityByKey(Integer key) throws DAOException {
//		 return Optional.ofNullable(findOne(set -> {
//			try {
//				return new User(
//							set.getInt("id"),set.getString("login"),UserRole.valueOf(set.getString("role")),
//				            set.getString("first_name"),set.getString("last_name"),set.getString("e-mail"),
//				            Boolean.valueOf(set.getString("is_active")),set.getString("avatar_path"));
//			} catch (SQLException e) {
//				logger.log(Level.WARN, "Unable to execute getEntityByKeymethod, can't get parameters from resultSet due to dataBase problem: ", e);
//			}
//			return null;
//		},
//				 SQL_SELECT_USER_BY_KEY, key));
//	}
//
//	@Override
//	protected boolean delete(Integer key) throws DAOException {
//		return sqlUpdate(SQL_DELETE_USER_BY_KEY,  key);
//	}
//
//	@Override
//	protected boolean delete(User entity) throws DAOException {
//		return delete(entity.getId());
//	}
//
//	@Override
//	protected boolean update(User entity) throws DAOException {
//        return sqlUpdate(SQL_UPDATE_USER_BY_LOGIN,
//                entity.getFirst_name(), entity.getLast_name(), entity.getEmail(), entity.isIs_active(), 
//                entity.getAvatar_path(), entity.getLogin());
//	}
//
//	@Override
//	protected boolean create(User entity) throws DAOException {
//		return sqlUpdate(SQL_INSERT_USER,
//                entity.getLogin(), entity.getPassword(), entity.getRole(), entity.getFirst_name(), 
//                entity.getLast_name(), entity.getEmail(), entity.isIs_active(), entity.getAvatar_path() );
//	}




}
