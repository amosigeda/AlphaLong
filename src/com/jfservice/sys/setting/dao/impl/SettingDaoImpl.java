package com.jfservice.sys.setting.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.jfservice.sys.setting.dao.SettingDao;
import com.jfservice.sys.setting.model.Setting;

@Repository
public class SettingDaoImpl implements SettingDao{

	private final String COUNT_SETTING = "countSetting";	
	private final String FIND_BYID_SETTING = "findByIdSetting";
	private final String FIND_ALL_SETTING = "findAllSetting";
	private final String FIND_SETTING = "findSetting";
	private final String INSERT_SETTING = "insertSetting";
	private final String UPDATE_BYID_SETTING = "updateByIdSetting";	
	private final String UPDATE_SETTING = "updateSetting";	
	private final String BATCH_UPDATE_SETTING = "batchUpdateSetting";
	private final String DELETE_BYID_SETTING = "deleteByIdSetting";
	private final String BATCH_DELETE_SETTING = "batchDeleteSetting";
	private final String DELETE_SETTING = "deleteSetting";
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int countOf(Setting model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(COUNT_SETTING, model);
	}

	@Override
	public Setting findById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(FIND_BYID_SETTING, FIND_BYID_SETTING);
	}

	@Override
	public List<Setting> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Setting> find(Setting model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(FIND_SETTING, model);
	}

	@Override
	public int insert(Setting model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(INSERT_SETTING, model);
	}

	@Override
	public int updateById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_BYID_SETTING, id);
	}

	@Override
	public int update(Setting model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_SETTING, model);
	}

	@Override
	public int batchUpdate(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Setting model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_SETTING, model);
	}

	@Override
	public int deleteById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_BYID_SETTING, id);
	}

	@Override
	public int batchDelete(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
