package com.yushkev.onlinetraining.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.yushkev.onlinetraining.connection.ConnectionPool;
import com.yushkev.onlinetraining.connection.ProxyConnection;
import com.yushkev.onlinetraining.dao.interfacedao.CasterToInstance_tmp;
import com.yushkev.onlinetraining.entity.BaseEntity;
import com.yushkev.onlinetraining.entity.User;
import com.yushkev.onlinetraining.exception.DAOException;
import com.yushkev.onlinetraining.exception.PoolException;

/**
 * Base type for all DAO classes
 *
 * @param <K> type of the primary key
 * @param <T> type of the main entity
 */

public abstract class AbstractDAO<K, T extends BaseEntity> implements AutoCloseable{
	
	protected ProxyConnection connection;
	
    public AbstractDAO() throws DAOException {
    	try {
			this.connection = ConnectionPool.getInstance().getConnection();
		} catch (PoolException e) {
			throw new DAOException("Unable to get DAO implementation due to ConnectioPool problem: ",e);
		}
  	}
	
    /*Base methods to override in Impl classes and pass SQL query*/
    public abstract List<T> getAll() throws DAOException;
    public abstract Optional<T> getEntityByKey(K key) throws DAOException;
    public abstract boolean delete(K key) throws DAOException;
//    protected abstract boolean delete(T entity) throws DAOException;//TODO: is this method necessary?
    public abstract Integer update(T entity) throws DAOException;
    public abstract Integer create(T entity) throws DAOException;

    /**
   	 * Get all entities of type T from database 
   	 * @return List of entities
   	 * @throws DAOException if database access error occurs 
   	 * or this method is called on a closed result set or preparedStatement
   	*/    
    protected List<T> getAll(String query) throws DAOException {
		List<T> entities = new ArrayList<>();
	       try (Statement statement = this.connection.createStatement();
	    		ResultSet resultSet = statement.executeQuery(query)) {
		    	   	while (resultSet.next()) {
		    	   		entities.add(fillEntity(resultSet));
		    	   }
	    	   }
	       catch (SQLException e) { 
	    	 throw new DAOException("Unable to get all entities: " + e);
	     }
			return entities;
	}
   
    /**
	 * Get the instance of T by key otherwise return empty Optional
	 * @return Optional of T
	 * @throws DAOException if database access error occurs 
	 * or this method is called on a closed result set or preparedStatement
	*/    
    protected Optional<T> getEntityByKey(K key, String query) throws DAOException {
	       T entity = null;
	       try (PreparedStatement statement = this.connection.prepareStatement(query)) { 
	    	   statement.setObject(1, key);
	    	   try (ResultSet resultSet = statement.executeQuery()) {
		    	 if (resultSet.next()) {
		    		 entity = fillEntity(resultSet);
		    	 }
	    	   }
	     } 
	       catch (SQLException e) { 
	    	 throw new DAOException("Unable to get entity ID: " + key + "   " + e);
	     }
			return Optional.ofNullable(entity);
	}

	/**
	* Delete instance of T by key 
	* @return boolean success of result
	* @throws DAOException  if a database access error
	* occurs or this method is called on a closed PreparedStatement
	*/
    protected boolean delete(K key, String query) throws DAOException {
		try (PreparedStatement statement = this.connection.prepareStatement(query)) { 
	       statement.setObject(1, key);
	       return statement.executeUpdate() > 0;
	    }
	    catch (SQLException e) { 
	     throw new DAOException("Unable to delete entity: " + key + "   " + e);
	    }
	}
	    
	/**
	* Update instance of T passing new instance 
	* @param entity instance of T 
	* @param query query String
	* @return boolean success of result
	* @throws DAOException  if a database access error
	* occurs or this method is called on a closed PreparedStatement
	*/
    protected int update(T entity, String query) throws DAOException {
    	Integer result = null;
		try (PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { 
			fillStatement(statement, entity);
			result = statement.executeUpdate();	
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next() && result > 0) {
				result = generatedKeys.getInt(1);
			}
		}
		catch (SQLException e) { 
			throw new DAOException("Unable to update entity: " + entity + "   " + e);
		}
		return result;
	}
	    
	/**
	* Insert new instance of T in database
	* @param entity instance of T 
	* @param query query String
	* @throws DAOException  if a database access error
	* occurs or this method is called on a closed PreparedStatement
	*/
    protected Integer create(T entity, String query) throws DAOException {
    		Integer result = null;
			try (PreparedStatement statement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) { 
					fillStatement(statement, entity);
				result = statement.executeUpdate();	
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next() && result > 0) {
					result = generatedKeys.getInt(1);
				}
			}
			catch (SQLException e) { 
				throw new DAOException("Unable to create entity: " + entity + "   " + e);
			}
			return result;
	}
	
    
    /**
	 * Get instance of T after executingQuery 
	 * @param resultSet instance resultSet {@code ResultSet} 
	 * @return {@code T} created after setting parameters from resultSet
	 * @throws DAOException  if a database access error occurs
	 */	    
    protected abstract T fillEntity(ResultSet resultSet) throws DAOException;
    
    /**
	 * Fill PreparedStatement to update or create a new item in database 
	 * @param instance of {@code PreparedStatement},  {@code T}
	 * @throws DAOException  if a database access error occurs
	 */	
    protected abstract void fillStatement(PreparedStatement statement, T entity) throws DAOException;
    

    

    
    
//    protected  <E> E findOne(CasterToInstance<E> instance, String sql, Object... preparedArgs) throws DAOException {
//	   try(ProxyConnection connection = pool.getConnection(); 
//   			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            if (preparedArgs != null) {
//                for (int i = 0; i < preparedArgs.length; i++) {
//                    preparedStatement.setObject(i + 1, preparedArgs[i]);
//                }
//            }
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//            if (resultSet.next()) {
//                if (resultSet.isLast()) {
//                    return instance.cast(resultSet);
//                }
//            }
//            throw new DAOException("Founded two or more resultSets in method");
//        } 
//	   } catch (SQLException | PoolException e){
//		   throw new DAOException(e);
//		}
//    }

//    protected  <E> List<E> findAll(CasterToInstance<E> instance, String sql, Object... preparedArgs) throws DAOException {
//    	ArrayList<E> list = new ArrayList<E>();
//    	if (instance != null && sql != null) {
//	    	try(ProxyConnection connection = pool.getConnection(); 
//	       			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//	    			if (preparedArgs != null) {
//	                for (int i = 0; i < preparedArgs.length; i++) {
//	                    preparedStatement.setObject(i + 1, preparedArgs[i]);
//	                }
//	            }
//	    		try (ResultSet resultSet = preparedStatement.executeQuery()) {
//	                while (resultSet.next()) {
//	                	list.add(instance.cast(resultSet));
//	                }
//	            }
//	        } 
//	    	catch (SQLException | PoolException e) {
//	        	throw new DAOException(e);
//			}
//    	}
//        return list;
//   	} 
//     
//	protected  <E extends BaseEntity> E findOne(CasterToInstance<E> instance, String sql, Object... preparedArgs) throws DAOException{
//    	try(ProxyConnection connection = pool.getConnection(); 
//    			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//    		if (preparedArgs != null){
//                for (int i = 0; i < preparedArgs.length; i++) {
//                    preparedStatement.setObject(i + 1, preparedArgs[i]);
//                }
//    		}
//    		try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    return instance.cast(resultSet);
//                }
//    		}
//    	} catch (SQLException | PoolException e) {
//			throw new DAOException(e);
//		}
//        return null;
//    }
//
//    protected boolean sqlUpdate(String sql, Object... preparedArgs) throws DAOException {
//    	Integer resultValue = 0;
//    	try(ProxyConnection connection = pool.getConnection(); 
//       			PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//        for (int i = 0; i < preparedArgs.length; i++ ){
//        	preparedStatement.setObject( i+1, preparedArgs[i]);
//         }
//            resultValue = preparedStatement.executeUpdate();
//        } catch (SQLException | PoolException e) {
//            throw new DAOException(e);
//        } 
//        return resultValue > 0;
//    }
    
    
    
    
    
    
    
//    //get PreparedStatement object
//    protected PreparedStatement getPrepStatement(String sql) {
//        PreparedStatement ps = null;
//        try {
//            ps = pool.connection.prepareStatement(sql);
//        } catch (SQLException e) {
//        	logger.log(Level.WARN, "Unable to get PreparedStatement, generated by DAO: ",e);
//        }
//        return ps;
//    }
//    
//    //get Statement object
//    protected Statement getStatement() {
//    	Statement st = null;
//        try {
//            st = connection.createStatement();
//        } catch (SQLException e) {
//        	logger.log(Level.WARN, "Unable to get Statement, generated by DAO: ",e);
//        }
//        return st;
//	}
    
    

//    //close ResultSet and Statement object
//    protected void closeResSetStatement(ResultSet resultSet, Statement statement) {
//    	try {
//    	if (resultSet != null) {
//    		resultSet.close();
//    	}
//        if (statement != null) {
//        	statement.close();
//        }
//    	} catch (SQLException e) {
//    		logger.log(Level.WARN, "Unable to close resultset and statement, generated by DAO: ",e);
//    	}
//    }
    
	@Override
    public void close() {
    	this.connection.close();
    }

       	
}



