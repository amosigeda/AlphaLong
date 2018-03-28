package com.jfservice.sys.location.model;

import java.util.Date;

import com.jfservice.common.bean.weike.PageBean;

public class LocationInfo extends PageBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8297130591252019954L;

	private Integer id;
	private String serieNo;
	private Integer battery;//电量
	private String longitude;//原始经度
	private String latitude;//原始纬度
	private String locationType;
	private Integer accuracy;//精确度
	private Date uploadTime;
	private String changeLongitude;//改变的经度
	private String changeLatitude;//改变的纬度
    private String belongProject;
	private String showType;  //显示类型
	private Date endTime;     //超过范围后的时间
	private String fall;
	private String sportType;
	
	public String getSportType() {
		return sportType;
	}
	public void setSportType(String sportType) {
		this.sportType = sportType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerieNo() {
		return serieNo;
	}
	public void setSerieNo(String serieNo) {
		this.serieNo = serieNo;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(Integer accuracy) {
		this.accuracy = accuracy;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getChangeLongitude() {
		return changeLongitude;
	}
	public void setChangeLongitude(String changeLongitude) {
		this.changeLongitude = changeLongitude;
	}
	public String getChangeLatitude() {
		return changeLatitude;
	}
	public void setChangeLatitude(String changeLatitude) {
		this.changeLatitude = changeLatitude;
	}
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getFall() {
		return fall;
	}
	public void setFall(String fall) {
		this.fall = fall;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
