package com.yushkev.onlinetraining.connection;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.yushkev.onlinetraining.exception.PoolException;

public class ConnectionPool implements AutoCloseable{
	
	private static Logger logger = LogManager.getLogger(ConnectionPool.class);

	private static Lock lock = new ReentrantLock(true);
	private static ConnectionPool pool;
	private ConnectionConfig connectionConfig;
	private static AtomicBoolean isCreated = new AtomicBoolean(false);
	private static AtomicBoolean isRunning = new AtomicBoolean(false);
	private static AtomicInteger lastConnectionNumber = new AtomicInteger(0);
	private BlockingQueue<ProxyConnection> connectionQueue;
	
	/**
	 * Creates connections and fills BlockingQueue with them.
	 * @throws PoolException 
	 * if wasn't unable to register driver or not enough 
	 * connections to start program were created
	 */
	private ConnectionPool() throws PoolException {
		try{
			connectionConfig = new ConnectionConfig();
			Class.forName(connectionConfig.getDriver_db());
		    connectionQueue = new ArrayBlockingQueue<>(connectionConfig.getMax_poolSize(), true);
		    for (int i = 0; i < connectionConfig.getMin_poolSize(); i++)
		    	connectionQueue.add(createConnection());
		 	}
		catch (ClassNotFoundException ex){
			logger.log(Level.FATAL, "Couldn't create connection pool! Driver ClassName is invalid! Program terminated!", ex);
			throw new PoolException(ex);
		}
		catch (PoolException ex){
			logger.log(Level.FATAL, "Couldn't create connection pool! Not enouth connections to start!", ex);
			throw new PoolException(ex);
		}
		isRunning.set(true);
		logger.log(Level.INFO, "ConnectionPool with # of active connections " + connectionQueue.size() + 
				" and max capacity " + connectionConfig.getMax_poolSize() + " was created");
	}
	
	/**
	 * Get the instance of ConnectionPool
	 * @return instance of this ConnectionPool
	 * @throws PoolIsClosedException 
	 * if wasn't unable to register driver or not enough 
	 * connections to start program were created
	 */
	public static ConnectionPool getInstance() throws PoolException {
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (pool == null) {
                    pool = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return pool;
    }
	
	/**
	 * Method for creating a new single connection
	 * @returns ProxyConnection connection
	 * @throws PoolException 
	 * if wasn't unable to get connection from DriverManager due to database problems
	 */
    private ProxyConnection createConnection() throws PoolException {
    	try {
			return new ProxyConnection(DriverManager.getConnection(
				connectionConfig.getUrl_db(), connectionConfig.getUser_db(),connectionConfig.getPass_db()));
		} catch (SQLException ex) {
			throw new PoolException("Could not connect to DataBase: ", ex);
		}
    }
   
    /**
     *Method for getting {@link ProxyConnection} instance.
     *If there are no free connections but no MAX amount reached, new 
     *connection is created. Else a connection created before is used.
     * @return instance of {@link ProxyConnection}
     * @throws PoolException in case of no available connections can be given
     */
    public ProxyConnection getConnection() throws PoolException {
    	if (!isRunning.get()) {
    		throw new PoolException ("Attempt to get connection when ConnectionPool is stopped");
    	}
    	ProxyConnection connection = null;
	    	try {
	    		lock.lock();
	    		if (connectionQueue.isEmpty() && lastConnectionNumber.get() < connectionConfig.getMax_poolSize()) { //cant't use @remainingCapacity() method: you cannot always tell if an attempt to insert an element will succeed by inspecting remainingCapacity because it may be the case that another thread is about to insert or remove an element.
	    			try {
	    				connection = createConnection();
	    				logger.log(Level.INFO, "Additional connection was created, size of connection pool now is: " + connectionQueue.size());
	    			}
	    			catch (PoolException e) {
						logger.log(Level.WARN, "Couldn't get new connection: ", e);
					}
	    		}
	    		else {
	    			connection = connectionQueue.poll();
	    		}
	    		if (connection != null) {
	    			lastConnectionNumber.incrementAndGet();
	    		}
	    		else {
	    			throw new PoolException("Can't get connection from pool, no free connections"); 
	    		} 			//TODO:wait until connection is available?>?? or throw exception if not possible to get new connection?
	    	}
	    	finally {
	            lock.unlock();
	        }
	    	return connection;
   	}

    /**
     *Method for returning {@link ProxyConnection} instance to the pool.
     *If can't be returned, the connection is to be closed.
     */
    public void returnConnection(ProxyConnection connection) {
    		boolean isReturned = connectionQueue.offer(connection);
	   		if (isReturned) {
	   			logger.log(Level.INFO, "Connection was returned to the Pool");
	    	}
	    	else {
	    		try {
					connection.hardCloseConnection();
				} catch (SQLException e) {
		    		logger.log(Level.ERROR, "Couldn't return connection. Exception occured while trying to close connection: ", e);
				}
	    	}
	   		lastConnectionNumber.decrementAndGet();
   	}

    
    /**
	 * Method for shutting down ConnectionPool and 
	 * deregistering loaded database drivers.
	 * Can be used with try-with-resources due to 
	 * implementing  AutoCloseable interface
	 */
    @Override
	public void close() {
	    isRunning.set(false);
	    for(ProxyConnection connection: connectionQueue) {
	    	connection.close();
	    }
	    try {
			Enumeration<Driver> drivers = DriverManager.getDrivers();
			while (drivers.hasMoreElements()) {
				Driver driver = drivers.nextElement();
				DriverManager.deregisterDriver(driver);
			}
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Couldn't deregister driver while closing connection: ", e);
		}
	    logger.log(Level.INFO, "ConnectionPool was shutDown");
	}

}
	
	

