package com.jfservice.sys.deviceactiveinfo.model;

import java.util.Date;

import com.jfservice.common.bean.weike.PageBean;

public class DeviceActive extends PageBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2651599528701240468L;
	
	private int id;

	private String devicePhone;  //电话号码
	
	private String deviceImei;   //imei
	
	private String deviceName;   //名称
	
	private String deviceHead;   //头像
	
	private String deviceSex;    //性别
	
	private String deviceAge;    //年龄
	
	private String deviceHeight; //身高
	
	private String deviceWeight; //体重
	
	private String deviceDisable; //不可用
	
	private Date deviceUpdateTime;   //更新时间

	private String userId;       //用户id
	
	private String listenType;   //监听״̬
	
	private String count;        //绑定次数
	
    private String belongProject;
    
    private String userName;
	
    private String deviceStatus; //ZST@20151104
    
    private String balance;//公交余额
    private String name;
    private String heartRate;
    private String time;
    private int did;
    private int uid;
    private Date updateTime;
    
    private String type;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(String heartRate) {
		this.heartRate = heartRate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public String getListenType() {
		return listenType;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setListenType(String listenType) {
		this.listenType = listenType;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Date getDeviceUpdateTime() {
		return deviceUpdateTime;
	}

	public void setDeviceUpdateTime(Date deviceUpdateTime) {
		this.deviceUpdateTime = deviceUpdateTime;
	}

	public String getDeviceDisable() {
		return deviceDisable;
	}

	public void setDeviceDisable(String deviceDisable) {
		this.deviceDisable = deviceDisable;
	}

	public String getDeviceWeight() {
		return deviceWeight;
	}

	public void setDeviceWeight(String deviceWeight) {
		this.deviceWeight = deviceWeight;
	}

	public String getDeviceHeight() {
		return deviceHeight;
	}

	public void setDeviceHeight(String deviceHeight) {
		this.deviceHeight = deviceHeight;
	}

	public String getDeviceAge() {
		return deviceAge;
	}

	public void setDeviceAge(String deviceAge) {
		this.deviceAge = deviceAge;
	}

	public String getDeviceSex() {
		return deviceSex;
	}

	public void setDeviceSex(String deviceSex) {
		this.deviceSex = deviceSex;
	}

	public String getDeviceHead() {
		return deviceHead;
	}

	public void setDeviceHead(String deviceHead) {
		this.deviceHead = deviceHead;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDevicePhone() {
		return devicePhone;
	}

	public void setDevicePhone(String devicePhone) {
		this.devicePhone = devicePhone;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
}
