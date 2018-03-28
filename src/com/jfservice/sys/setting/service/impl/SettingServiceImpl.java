package com.jfservice.sys.setting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfservice.sys.setting.dao.SettingDao;
import com.jfservice.sys.setting.model.Setting;
import com.jfservice.sys.setting.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService{

	@Autowired
	private SettingDao settingDao;
	
	@Override
	public int countOf(Setting model) {
		// TODO Auto-generated method stub
		return settingDao.countOf(model);
	}

	@Override
	public Setting findById(int id) {
		// TODO Auto-generated method stub
		return settingDao.findById(id);
	}

	@Override
	public List<Setting> findAll() {
		// TODO Auto-generated method stub
		return settingDao.findAll();
	}

	@Override
	public List<Setting> find(Setting model) {
		// TODO Auto-generated method stub
		return settingDao.find(model);
	}

	@Override
	public int insert(Setting model) {
		// TODO Auto-generated method stub
		return settingDao.insert(model);
	}

	@Override
	public int updateById(int id) {
		// TODO Auto-generated method stub
		return settingDao.updateById(id);
	}

	@Override
	public int update(Setting model) {
		// TODO Auto-generated method stub
		return settingDao.update(model);
	}

	@Override
	public int batchUpdate(List<Integer> models) {
		// TODO Auto-generated method stub
		return settingDao.batchUpdate(models);
	}

	@Override
	public int delete(Setting model) {
		// TODO Auto-generated method stub
		return settingDao.delete(model);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return settingDao.deleteById(id);
	}

	@Override
	public int batchDelete(List<Integer> models) {
		// TODO Auto-generated method stub
		return settingDao.batchDelete(models);
	}

}
