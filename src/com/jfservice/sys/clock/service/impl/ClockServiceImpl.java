package com.jfservice.sys.clock.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfservice.sys.clock.dao.ClockDao;
import com.jfservice.sys.clock.model.Clock;
import com.jfservice.sys.clock.service.ClockService;

@Service
public class ClockServiceImpl implements ClockService{

	@Autowired
	private ClockDao clockDao;
	
	@Override
	public int countOf(Clock model) {
		// TODO Auto-generated method stub
		return clockDao.countOf(model);
	}

	@Override
	public Clock findById(int id) {
		// TODO Auto-generated method stub
		return clockDao.findById(id);
	}

	@Override
	public List<Clock> findAll() {
		// TODO Auto-generated method stub
		return clockDao.findAll();
	}

	@Override
	public List<Clock> find(Clock model) {
		// TODO Auto-generated method stub
		return clockDao.find(model);
	}

	@Override
	public int insert(Clock model) {
		// TODO Auto-generated method stub
		return clockDao.insert(model);
	}

	@Override
	public int updateById(int id) {
		// TODO Auto-generated method stub
		return clockDao.updateById(id);
	}

	@Override
	public int update(Clock model) {
		// TODO Auto-generated method stub
		return clockDao.update(model);
	}

	@Override
	public int batchUpdate(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Clock model) {
		// TODO Auto-generated method stub
		return clockDao.delete(model);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return clockDao.deleteById(id);
	}

	@Override
	public int batchDelete(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Clock> findDisturb(Clock clock) {
		// TODO Auto-generated method stub
		return clockDao.findDisturb(clock);
	}

}
