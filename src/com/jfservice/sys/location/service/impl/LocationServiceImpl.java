package com.jfservice.sys.location.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfservice.sys.location.dao.LocationDao;
import com.jfservice.sys.location.model.LocationInfo;
import com.jfservice.sys.location.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService{

	@Autowired
	private LocationDao locationDao;
	
	@Override
	public int countOf(LocationInfo model) {
		// TODO Auto-generated method stub
		return locationDao.countOf(model);
	}

	@Override
	public LocationInfo findById(int id) {
		// TODO Auto-generated method stub
		return locationDao.findById(id);
	}

	@Override
	public List<LocationInfo> findAll() {
		// TODO Auto-generated method stub
		return locationDao.findAll();
	}

	@Override
	public List<LocationInfo> find(LocationInfo model) {
		// TODO Auto-generated method stub
		return locationDao.find(model);
	}

	@Override
	public int insert(LocationInfo model) {
		// TODO Auto-generated method stub
		return locationDao.insert(model);
	}

	@Override
	public int updateById(int id) {
		// TODO Auto-generated method stub
		return locationDao.updateById(id);
	}

	@Override
	public int update(LocationInfo model) {
		// TODO Auto-generated method stub
		return locationDao.update(model);
	}

	@Override
	public int batchUpdate(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(LocationInfo model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return locationDao.deleteById(id);
	}

	@Override
	public int batchDelete(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

}
