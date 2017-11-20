package com.yushkev.onlinetraining.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.dao.interfacedao.ICourseTypeDao;
import com.yushkev.onlinetraining.entity.CourseType;
import com.yushkev.onlinetraining.exception.DAOException;

public class CourseTypeDaoImpl  extends AbstractDAO<Integer, CourseType> implements ICourseTypeDao{
	
	private static final String SQL_SELECT_ALL_COURSE_TYPES = "SELECT id, category FROM course_types";
	private static final String SQL_SELECT_COURSE_TYPE_BY_ID = "SELECT id, category FROM course_types WHERE id = ?";
	private static final String SQL_SELECT_COURSE_TYPE_BY_CATEGORY = "SELECT id, category FROM course_types WHERE category = ?";
	private static final String SQL_DELETE_COURSE_TYPE_BY_ID = "DELETE FROM course_types WHERE id = ?";
	private static final String SQL_UPDATE_COURSE_TYPE = "UPDATE course_types SET category = ? WHERE id = ?";
	private static final String SQL_INSERT_COURSE_TYPE = "INSERT INTO course_types (category) values (?)";

	public CourseTypeDaoImpl() throws DAOException {
		super();
	}

	@Override
	public List<CourseType> getAll() throws DAOException {
		return getAll(SQL_SELECT_ALL_COURSE_TYPES);
	}

	@Override
	public Optional<CourseType> getEntityByKey(Integer id) throws DAOException {
		return getEntityByKey(id, SQL_SELECT_COURSE_TYPE_BY_ID);
	}
	

	@Override
	public boolean delete(Integer id) throws DAOException {
		return delete(id, SQL_DELETE_COURSE_TYPE_BY_ID);
	}

	@Override
	public Integer update(CourseType entity) throws DAOException {
		return update(entity, SQL_UPDATE_COURSE_TYPE);
	}

	@Override
	public Integer create(CourseType entity) throws DAOException {
		return create(entity, SQL_INSERT_COURSE_TYPE);
	}

	@Override
	protected CourseType fillEntity(ResultSet resultSet) throws DAOException {
		CourseType courseType = new CourseType();
		try {
			courseType.setId(resultSet.getInt("id"));
			courseType.setCategory(resultSet.getString("category"));	
		}
		catch (SQLException e) {
			throw new DAOException("Unable to fill entity " + e);
		}
		return courseType;
	}

	@Override
	protected void fillStatement(PreparedStatement statement, CourseType entity) throws DAOException {
		try {
			int i = 1;
	     	statement.setString(i++, entity.getCategory());
		/*	if need to update existing category*/
	     	if (entity.getId() != null) {
				statement.setInt(i++, entity.getId());	
			}
		} catch (SQLException e) { 
			throw new DAOException("Unable to fill Statement for: " + entity.getId() + "   " + e);
	    }
		
	}
	
	public Optional<CourseType> getCourseTypeByCategory(String category) throws DAOException {
		CourseType resulType = null;
		try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_COURSE_TYPE_BY_CATEGORY)){
		 	   statement.setString(1, category);
		try (ResultSet resultSet = statement.executeQuery()) { 
			if (resultSet.next()) {
				resulType = fillEntity(resultSet);
		 	}
		} 
		
	} catch (SQLException e) { 
 	 throw new DAOException("Unable to get entity: " + category + "   " + e);
	}
		return Optional.ofNullable(resulType);
	}

	
}

