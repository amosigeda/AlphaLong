package com.jfservice.sys.clock.dao;

import java.util.List;

import com.jfservice.sys.clock.model.Clock;
import com.jfservice.sys.core.IBaseDao;

public interface ClockDao extends IBaseDao<Clock>{

	List<Clock> findDisturb(Clock clock);

}
