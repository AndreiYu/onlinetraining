package com.yushkev.onlinetraining;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.mindrot.jbcrypt.BCrypt;

import com.yushkev.onlinetraining.connection.ConnectionPool;
import com.yushkev.onlinetraining.connection.ProxyConnection;
import com.yushkev.onlinetraining.exception.PoolException;

/**
 * Hello world!
 *
 */
public class App {
	  

	
//	static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
//	static ArrayDeque<Integer> queue = new ArrayDeque<>(2);


	
    public static void main(String[ ] args) {
    	
    	
    	System.out.println(BCrypt.hashpw("1234Abcd", BCrypt.gensalt()));
    	System.out.println(BCrypt.hashpw("1234aBcd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234abCd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234abcD", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234ABcd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234AbCd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234AbcD", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234ABCd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234AbCD", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234aBCD", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("1234aBCD", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("2345Abcd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("2345aBcd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("2345abCd", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("2345abcD", BCrypt.gensalt()));
//    	System.out.println(BCrypt.hashpw("2345abcD", BCrypt.gensalt()));
    	
    	System.out.println(BCrypt.checkpw("1234Abcd", "$2a$10$0nsID6bOqt514MoJmVu1OOhTum4bR.obxUEtpT7CeFTtQnhD4TEG6"));
    	
//    	for (int i = 0; i < 50; i++ ) {
//    	new Thread()
//    	{
// 
//    	    	 public void run() {
//    	    	    	try {
////    	    	    		Thread.sleep((long)Math.random()*100);
//    	    	    		try
//    		    			(ProxyConnection con = ConnectionPool.getInstance().getConnection()) {
//    		    			ResultSet rSet = null;
//    		    			Statement statement = con.createStatement();
//    		    			rSet = statement.executeQuery("SELECT * FROM onlinetrainingdb.task");
//    		    			rSet.next();
//    		    			System.out.println(rSet.getString(3));
//    		    			rSet.close();
//    		    			statement.close();
//    		    			    		    			
//    		    			}
//    		    			
//    		    		} catch (PoolException e) {
//    		    			// TODO Auto-generated catch block
//    		    			e.printStackTrace();
//    		    		} catch (SQLException e) {
//    		    			// TODO Auto-generated catch block
//    		    			e.printStackTrace();
//    		    		}
////    		    		} catch (InterruptedException e) {
////							// TODO Auto-generated catch block
////							e.printStackTrace();
////						};
//    	    		 
//   	    		 
//    	    		 
//    	    }
//    	}.start();
//    	
//    	}	
    	
    	}
    	
    	
    	
    	
    	
//    	ConnectionPool.getInstance().shutDown();
//    	
//    	
//    	
//    	
//    	}  	
		  
//     synchronized void prr(){
//    	 boolean  a = queue.isEmpty();
//    	System.out.println(a);
//    }
//    
  
}

		  

 

	  
 







	







