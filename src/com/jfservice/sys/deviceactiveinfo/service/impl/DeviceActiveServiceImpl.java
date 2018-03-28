package com.jfservice.sys.deviceactiveinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfservice.sys.deviceactiveinfo.dao.DeviceActiveDao;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;
import com.jfservice.sys.deviceactiveinfo.service.DeviceActiveService;

@Service
public class DeviceActiveServiceImpl implements DeviceActiveService{

	@Autowired
	private DeviceActiveDao deviceActiveDao;

	@Override
	public int countOf(DeviceActive model) {
		// TODO Auto-generated method stub
		return deviceActiveDao.countOf(model);
	}

	@Override
	public DeviceActive findById(int id) {
		// TODO Auto-generated method stub
		return deviceActiveDao.findById(id);
	}

	@Override
	public List<DeviceActive> findAll() {
		// TODO Auto-generated method stub
		return deviceActiveDao.findAll();
	}

	@Override
	public List<DeviceActive> find(DeviceActive model) {
		// TODO Auto-generated method stub
		return deviceActiveDao.find(model);
	}

	@Override
	public int insert(DeviceActive model) {
		// TODO Auto-generated method stub
		return deviceActiveDao.insert(model);
	}

	@Override
	public int updateById(int id) {
		// TODO Auto-generated method stub
		return deviceActiveDao.updateById(id);
	}

	@Override
	public int update(DeviceActive model) {
		// TODO Auto-generated method stub
		return deviceActiveDao.update(model);
	}

	@Override
	public int batchUpdate(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(DeviceActive model) {
		// TODO Auto-generated method stub
		return deviceActiveDao.delete(model);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return deviceActiveDao.deleteById(id);
	}

	@Override
	public int batchDelete(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<DeviceActive> findDeviceGroups(DeviceActive da) {
		// TODO Auto-generated method stub
		return  deviceActiveDao.findDeviceGroups(da);
	}

	@Override
	public int insertHeartRate(DeviceActive l) {
		return deviceActiveDao.insertHeartRate(l);
	}

	@Override
	public int insertDeviceGroups(DeviceActive dav) {
		return deviceActiveDao.insertDeviceGroups(dav);
	}

	@Override
	public int updateTalkGroup(DeviceActive vo) {
		return deviceActiveDao.updateTalkGroup(vo);
	}

}
