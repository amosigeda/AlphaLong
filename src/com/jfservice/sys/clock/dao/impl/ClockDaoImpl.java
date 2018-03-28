package com.jfservice.sys.clock.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.jfservice.sys.clock.dao.ClockDao;
import com.jfservice.sys.clock.model.Clock;

@Repository
public class ClockDaoImpl implements ClockDao{

	private final String COUNT_CLOCK = "countClock";	
	private final String FIND_BYID_CLOCK = "findByIdClock";
	private final String FIND_ALL_CLOCK = "findAllClock";
	private final String FIND_CLOCK = "findClock";
	private final String FIND_CLOCK_DISTURB = "findClockDisturb";
	private final String INSERT_CLOCK = "insertClock";
	private final String UPDATE_BYID_CLOCK = "updateByIdClock";	
	private final String UPDATE_CLOCK = "updateClock";	
	private final String BATCH_UPDATE_CLOCK = "batchUpdateClock";
	private final String DELETE_BYID_CLOCK = "deleteByIdClock";
	private final String BATCH_DELETE_CLOCK = "batchDeleteClock";
	private final String DELETE_CLOCK = "deleteClock";
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int countOf(Clock model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(COUNT_CLOCK, model);
	}

	@Override
	public Clock findById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(FIND_BYID_CLOCK, FIND_BYID_CLOCK);
	}

	@Override
	public List<Clock> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Clock> find(Clock model) throws DataAccessException {
		// TODO Auto-generated method stub
		PageHelper.startPage(model.getPage(),model.getRows(),model.getSort()+" "+model.getOrder());
		return sqlSessionTemplate.selectList(FIND_CLOCK, model);
	}

	@Override
	public int insert(Clock model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(INSERT_CLOCK, model);
	}

	@Override
	public int updateById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_BYID_CLOCK, id);
	}

	@Override
	public int update(Clock model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(UPDATE_CLOCK, model);
	}

	@Override
	public int batchUpdate(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Clock model) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_CLOCK, model);
	}

	@Override
	public int deleteById(@Param("id")int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(DELETE_BYID_CLOCK, id);
	}

	@Override
	public int batchDelete(List<Integer> models) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Clock> findDisturb(Clock clock)throws DataAccessException {
		// TODO Auto-generated method stub
		PageHelper.startPage(clock.getPage(),clock.getRows(),clock.getSort()+" "+clock.getOrder());
		return sqlSessionTemplate.selectList(FIND_CLOCK_DISTURB, clock);
	}

}
