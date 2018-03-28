package com.jfservice.sys.location.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.jfservice.sys.location.dao.LocationDao;
import com.jfservice.sys.location.model.LocationInfo;

@Repository
public class LocationDaoImpl implements LocationDao{

	private final String COUNT_LOCATION = "countLocationInfo";	
	private final String FIND_BYID_LOCATION = "findByIdLocationInfo";
	private final String FIND_ALL_LOCATION = "findAllLocationInfo";
	private final String FIND_LOCATION = "findLocationInfo";
	private final String INSERT_LOCATION = "insertLocationInfo";
	private final String UPDATE_BYID_LOCATION = "updateByIdLocationInfo";	
	private final String UPDATE_LOCATION = "updateLocationInfo";	
	private final String BATCH_UPDATE_LOCATION = "batchUpdateLocationInfo";
	private final String DELETE_BYID_LOCATION = "deleteByIdLocationInfo";
	private final String BATCH_DELETE_LOCATION = "batchDeleteLocationInfo";
	private final String DELETE_LOCATION = "deleteLocationInfo";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	
	@Override
	public int countOf(LocationInfo model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(COUNT_LOCATION, model);
	}

	@Override
	public LocationInfo findById(@Param("id") int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(FIND_BYID_LOCATION, id);
	}

	@Override
	public List<LocationInfo> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(FIND_ALL_LOCATION);
	}

	@Override
	public List<LocationInfo> find(LocationInfo model) throws DataAccessException {
		// TODO Auto-generated method stub
		PageHelper.startPage(model.getPage(), model.getRows(), model.getSort()+" "+model.getOrder());
		return sqlSessionTemplate.selectList(FIND_LOCATION, model);
	}

	@Override
	public int insert(LocationInfo model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(INSERT_LOCATION, model);
	}

	@Override
	public int updateById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_BYID_LOCATION, id);
	}

	@Override
	public int update(LocationInfo model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_LOCATION, model);
	}

	@Override
	public int batchUpdate(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LocationInfo model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_LOCATION, model);
	}

	@Override
	public int deleteById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_BYID_LOCATION, id);
	}

	@Override
	public int batchDelete(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
