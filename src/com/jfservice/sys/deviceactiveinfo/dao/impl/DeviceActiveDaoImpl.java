package com.jfservice.sys.deviceactiveinfo.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.jfservice.sys.deviceactiveinfo.dao.DeviceActiveDao;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;

@Repository
public class DeviceActiveDaoImpl implements DeviceActiveDao{

	private Logger logger = Logger.getLogger(DeviceActiveDaoImpl.class);
	
	private final String COUNT_DEVICEACTIVE = "countDeviceActive";	
	private final String FIND_BYID_DEVICEACTIVE = "findByIdDeviceActive";
	private final String FIND_ALL_DEVICEACTIVE = "findAllDeviceActive";
	private final String FIND_DEVICEACTIVE = "findDeviceActive";
	private final String FIND_DEVICE_GROUPS = "findDeviceGroups";
	private final String INSERT_DEVICEACTIVE = "insertDeviceActive";
	private final String INSERT_HEARTRATE = "insertHeartRate";
	private final String UPDATE_BYID_DEVICEACTIVE = "updateByIdDeviceActive";	
	private final String UPDATE_DEVICEACTIVE = "updateDeviceActive";	
	private final String BATCH_UPDATE_DEVICEACTIVE = "batchUpdateDeviceActive";
	private final String DELETE_BYID_DEVICEACTIVE = "deleteByIdDeviceActive";
	private final String BATCH_DELETE_DEVICEACTIVE = "batchDeleteDeviceActive";
	private final String DELETE_DEVICEACTIVE = "deleteDeviceActive";
	private final String INSERT_GROUP = "insertGroup";
	private final String UPDATE_TALKGROUP = "updateGroup";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int countOf(DeviceActive model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(COUNT_DEVICEACTIVE, model);
	}

	@Override
	public DeviceActive findById(@Param("id") int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(FIND_BYID_DEVICEACTIVE,id);
	}

	@Override
	public List<DeviceActive> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(FIND_ALL_DEVICEACTIVE);
	}

	@Override
	public List<DeviceActive> find(DeviceActive model) throws DataAccessException {
		// TODO Auto-generated method stub
		PageHelper.startPage(model.getPage(), model.getRows(), model.getSort()+" "+model.getOrder());
		return sqlSessionTemplate.selectList(FIND_DEVICEACTIVE, model);
	}

	@Override
	public int insert(DeviceActive model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(INSERT_DEVICEACTIVE, model);
	}

	@Override
	public int updateById(@Param("id") int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_BYID_DEVICEACTIVE, id);
	}

	@Override
	public int update(DeviceActive model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_DEVICEACTIVE, model);
	}

	@Override
	public int batchUpdate(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(DeviceActive model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_DEVICEACTIVE, model);
	}

	@Override
	public int deleteById(@Param("id") int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_BYID_DEVICEACTIVE, id);
	}

	@Override
	public int batchDelete(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DeviceActive> findDeviceGroups(DeviceActive da) {
		PageHelper.startPage(da.getPage(), da.getRows(), da.getSort()+" "+da.getOrder());
		return sqlSessionTemplate.selectList(FIND_DEVICE_GROUPS, da);
	}

	@Override
	public int insertHeartRate(DeviceActive l) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(INSERT_HEARTRATE, l);
	}

	@Override
	public int insertDeviceGroups(DeviceActive dav) {
		return sqlSessionTemplate.insert(INSERT_GROUP, dav);
	}

	@Override
	public int updateTalkGroup(DeviceActive vo) {
		return sqlSessionTemplate.update(UPDATE_TALKGROUP, vo);
	}

}
