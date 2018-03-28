package com.jfservice.sys.media.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.jfservice.sys.media.dao.MediaDao;
import com.jfservice.sys.media.model.Media;

@Repository
public class MediaDaoImpl implements MediaDao{

	private Logger logger = Logger.getLogger(MediaDaoImpl.class);
	
	private final String COUNT_MEDIA = "countMedia";	
	private final String FIND_BYID_MEDIA = "findByIdMedia";
	private final String FIND_ALL_MEDIA = "findAllMedia";
	private final String FIND_MEDIA = "findMedia";
	private final String INSERT_MEDIA = "insertMedia";
	private final String UPDATE_BYID_MEDIA = "updateByIdMedia";	
	private final String UPDATE_MEDIA = "updateMedia";	
	private final String BATCH_UPDATE_MEDIA = "batchUpdateMedia";
	private final String DELETE_BYID_MEDIA = "deleteByIdMedia";
	private final String BATCH_DELETE_MEDIA = "batchDeleteMedia";
	private final String DELETE_MEDIA = "deleteMedia";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int countOf(Media model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(COUNT_MEDIA, model);
	}

	@Override
	public Media findById(@Param("id") int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(FIND_BYID_MEDIA,id);
	}

	@Override
	public List<Media> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(FIND_ALL_MEDIA);
	}

	@Override
	public List<Media> find(Media model) throws DataAccessException {
		// TODO Auto-generated method stub
		PageHelper.startPage(model.getPage(), model.getRows(), model.getSort()+" "+model.getOrder());
		return sqlSessionTemplate.selectList(FIND_MEDIA, model);
	}

	@Override
	public int insert(Media model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(INSERT_MEDIA, model);
	}

	@Override
	public int updateById(@Param("id") int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_BYID_MEDIA, id);
	}

	@Override
	public int update(Media model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_MEDIA, model);
	}

	@Override
	public int batchUpdate(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Media model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_MEDIA, model);
	}

	@Override
	public int deleteById(@Param("id") int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_BYID_MEDIA, id);
	}

	@Override
	public int batchDelete(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
