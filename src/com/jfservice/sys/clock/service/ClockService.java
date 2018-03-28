package com.jfservice.sys.clock.service;

import java.util.List;

import com.jfservice.sys.clock.model.Clock;
import com.jfservice.sys.core.IBaseService;

public interface ClockService extends IBaseService<Clock>{

	List<Clock> findDisturb(Clock clock);


}
