package com.jfservice.sys.deviceactiveinfo.dao;

import java.util.List;

import com.jfservice.sys.core.IBaseDao;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;

public interface DeviceActiveDao extends IBaseDao<DeviceActive>{

	List<DeviceActive> findDeviceGroups(DeviceActive da);

	int insertHeartRate(DeviceActive l);

	int insertDeviceGroups(DeviceActive dav);

	int updateTalkGroup(DeviceActive vo);

}
