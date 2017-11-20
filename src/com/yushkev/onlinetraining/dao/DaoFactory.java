package com.yushkev.onlinetraining.dao;




import com.yushkev.onlinetraining.connection.ConnectionPool;
import com.yushkev.onlinetraining.connection.ProxyConnection;
import com.yushkev.onlinetraining.dao.interfacedao.ICourseDao;
import com.yushkev.onlinetraining.dao.interfacedao.ICourseTypeDao;
import com.yushkev.onlinetraining.dao.interfacedao.IUserDao;
import com.yushkev.onlinetraining.entity.CourseType;
import com.yushkev.onlinetraining.exception.DAOException;
import com.yushkev.onlinetraining.exception.PoolException;
import com.yushkev.onlinetraining.filter.PageSecurityFilter;


public class DaoFactory {
	
	private DaoFactory() {
	
	}
	
	private static class SingletonHolder { 
		private final static DaoFactory INSTANCE = new DaoFactory();
	}
		
	public static DaoFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}
		
	public IUserDao getUserDao() throws DAOException {
		return new UserDaoImpl();
	};
	
	public ICourseDao getCourseDao() throws DAOException{
		return new CourseDaoImpl();
	};
	
	public ICourseTypeDao getCourseTypeDao() throws DAOException{
		return new CourseTypeDaoImpl();
	}
//	
//	public TaskDao getTaskDao(){
//		return new TaskDaoImpl();
//	};
//	
//	public TaskPerformedDao getTaskPerformedDao(){
//		return new TaskPerformedDaoImpl();
//	};
//		
//	public CourseEnrolmentDao getCourseEnrolmentDao(){
//		return new CourseEnrolmentDaoImpl();
//	};
	

}
