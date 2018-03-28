package com.jfservice.sys.deviceactiveinfo.service;

import java.util.List;

import com.jfservice.sys.core.IBaseService;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;

public interface DeviceActiveService extends IBaseService<DeviceActive>{

	List<DeviceActive> findDeviceGroups(DeviceActive da);

	int insertHeartRate(DeviceActive l);

	int insertDeviceGroups(DeviceActive dav);

	int updateTalkGroup(DeviceActive vo);

}
