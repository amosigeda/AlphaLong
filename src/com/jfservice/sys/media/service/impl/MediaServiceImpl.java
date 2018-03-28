package com.jfservice.sys.media.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfservice.sys.media.dao.MediaDao;
import com.jfservice.sys.media.model.Media;
import com.jfservice.sys.media.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService{

	@Autowired
	private MediaDao mediaDao;

	@Override
	public int countOf(Media model) {
		// TODO Auto-generated method stub
		return mediaDao.countOf(model);
	}

	@Override
	public Media findById(int id) {
		// TODO Auto-generated method stub
		return mediaDao.findById(id);
	}

	@Override
	public List<Media> findAll() {
		// TODO Auto-generated method stub
		return mediaDao.findAll();
	}

	@Override
	public List<Media> find(Media model) {
		// TODO Auto-generated method stub
		return mediaDao.find(model);
	}

	@Override
	public int insert(Media model) {
		// TODO Auto-generated method stub
		return mediaDao.insert(model);
	}

	@Override
	public int updateById(int id) {
		// TODO Auto-generated method stub
		return mediaDao.updateById(id);
	}

	@Override
	public int update(Media model) {
		// TODO Auto-generated method stub
		return mediaDao.update(model);
	}

	@Override
	public int batchUpdate(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Media model) {
		// TODO Auto-generated method stub
		return mediaDao.delete(model);
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return mediaDao.deleteById(id);
	}

	@Override
	public int batchDelete(List<Integer> models) {
		// TODO Auto-generated method stub
		return 0;
	}

}
