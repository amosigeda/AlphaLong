package com.jfservice.sys.family.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.jfservice.sys.family.dao.FamilyDao;
import com.jfservice.sys.family.model.Family;

@Repository
public class FamilyDaoImpl implements FamilyDao{

	private final String COUNT_FAMILY = "countFamily";	
	private final String FIND_BYID_FAMILY = "findByIdFamily";
	private final String FIND_ALL_FAMILY = "findAllFamily";
	private final String FIND_FAMILY = "findFamily";
	private final String INSERT_FAMILY = "insertFamily";
	private final String UPDATE_BYID_FAMILY = "updateByIdFamily";	
	private final String UPDATE_FAMILY = "updateFamily";	
	private final String BATCH_UPDATE_FAMILY = "batchUpdateFamily";
	private final String DELETE_BYID_FAMILY = "deleteByIdFamily";
	private final String BATCH_DELETE_FAMILY = "batchDeleteFamily";
	private final String DELETE_FAMILY = "deleteFamily";
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int countOf(Family model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(COUNT_FAMILY, model);
	}

	@Override
	public Family findById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(FIND_BYID_FAMILY, FIND_BYID_FAMILY);
	}

	@Override
	public List<Family> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Family> find(Family model) throws DataAccessException {
		// TODO Auto-generated method stub
		PageHelper.startPage(model.getPage(),model.getRows(),model.getSort()+" "+model.getOrder());
		return sqlSessionTemplate.selectList(FIND_FAMILY, model);
	}

	@Override
	public int insert(Family model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(INSERT_FAMILY, model);
	}

	@Override
	public int updateById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_BYID_FAMILY, id);
	}

	@Override
	public int update(Family model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_FAMILY, model);
	}

	@Override
	public int batchUpdate(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Family model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_FAMILY, model);
	}

	@Override
	public int deleteById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_BYID_FAMILY, id);
	}

	@Override
	public int batchDelete(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
