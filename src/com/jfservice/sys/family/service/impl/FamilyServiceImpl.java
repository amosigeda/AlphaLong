package com.jfservice.sys.family.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfservice.sys.family.dao.FamilyDao;
import com.jfservice.sys.family.model.Family;
import com.jfservice.sys.family.service.FamilyService;

@Service
public class FamilyServiceImpl implements FamilyService{

	@Autowired
	private FamilyDao familyDao;
	
	@Override
	public int countOf(Family model) {
		// TODO Auto-generated method stub
		return familyDao.countOf(model);
	}

	@Override
	public Family findById(int id) {
		// TODO Auto-generated method stub
		return familyDao.findById(id);
	}

	@Override
	public List<Family> findAll() {
		// TODO Auto-generated method stub
		return familyDao.findAll();
	}

	@Override
	public List<Family> find(Family model) {
		// TODO Auto-generated method stub
		return familyDao.find(model);
	}

	@Override
	public int insert(Family model) {
		// TODO Auto-generated method stub
		return familyDao.insert(model);
	}

	@Override
	public int updateById(int id) {
		// TODO Auto-generated method stub
		return familyDao.updateById(id);
	}

	@Override
	public int update(Family model) {
		// TODO Auto-generated method stub
		return familyDao.update(model);
	}

	@Override
	public int batchUpdate(List<Integer> models) {
		// TODO Auto-generated method stub
		return familyDao.batchUpdate(models);
	}

	@Override
	public int delete(Family model) {
		// TODO Auto-generated method stub
		return familyDao.delete(model);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return familyDao.deleteById(id);
	}

	@Override
	public int batchDelete(List<Integer> models) {
		// TODO Auto-generated method stub
		return familyDao.batchDelete(models);
	}

}
