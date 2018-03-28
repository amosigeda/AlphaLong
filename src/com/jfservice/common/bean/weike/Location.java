package com.jfservice.common.bean.weike;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Location {

	@XStreamAsAttribute
	@XStreamAlias("Time")
	private String time;  //上传时间
	
	@XStreamAsAttribute
	private String adorn; //设备是否佩戴标志，1表示佩戴,表示脱落
	
	@XStreamAsAttribute
	@XStreamAlias("Lat")
	private double lat;   //纬度
	
	@XStreamAsAttribute
	@XStreamAlias("Long")
	private double lon;   //经度	

	@XStreamAsAttribute
	@XStreamAlias("NS")
	private String nsStatus;  //N表示北纬,S表示南纬
	
	@XStreamAsAttribute
	@XStreamAlias("EW")
	private String ewStatus;  //E表示东经,S表示西经
	
	@XStreamAsAttribute
	@XStreamAlias("Altitude")
	private String altitude;  //海拔
	
	@XStreamAsAttribute
	private String speed;   //速度
	
	@XStreamAsAttribute
	private String mode;   //模式
	
	@XStreamAsAttribute
	@XStreamAlias("Battery")
	private String battery;  //电量
	
	@XStreamAsAttribute
	private String version;  //版本号
	
	@XStreamAsAttribute
	@XStreamAlias("Mcc1")
	private String mcc1;    //移动用户国家码
	
	@XStreamAsAttribute
	@XStreamAlias("Mcc2")
	private String mcc2;    //移动用户国家码
	
	@XStreamAsAttribute
	@XStreamAlias("Mcc3")
	private String mcc3;    //移动用户国家码

	@XStreamAsAttribute
	@XStreamAlias("Mnc1")
	private String mnc1;    //移动网号
	
	@XStreamAsAttribute
	@XStreamAlias("Mnc2")
	private String mnc2;    //移动网号
	
	@XStreamAsAttribute
	@XStreamAlias("Mnc3")
	private String mnc3;    //移动网号
	
	@XStreamAsAttribute
	@XStreamAlias("Lac1")
	private String lac1;    //位置区域码
	
	@XStreamAsAttribute
	@XStreamAlias("Lac2")
	private String lac2;    //位置区域码
	
	@XStreamAsAttribute
	@XStreamAlias("Lac3")
	private String lac3;    //位置区域码
	
	@XStreamAsAttribute
	@XStreamAlias("Cellid1")
	private String cellId1; //小区标示
	
	@XStreamAsAttribute
	@XStreamAlias("Cellid2")
	private String cellId2; //小区标示
	
	@XStreamAsAttribute
	@XStreamAlias("Cellid3")
	private String cellId3; //小区标示
	
	@XStreamAsAttribute
	@XStreamAlias("Rssi1")
	private String rssi1; //信号强度
	
	@XStreamAsAttribute
	@XStreamAlias("Rssi2")
	private String rssi2; //信号强度
	
	@XStreamAsAttribute
	@XStreamAlias("Rssi3")
	private String rssi3; //信号强度
	
	@XStreamAsAttribute
	@XStreamAlias("ssid")
	private String ssid; //信号强度
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAdorn() {
		return adorn;
	}

	public void setAdorn(String adorn) {
		this.adorn = adorn;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getNsStatus() {
		return nsStatus;
	}

	public void setNsStatus(String nsStatus) {
		this.nsStatus = nsStatus;
	}

	public String getEwStatus() {
		return ewStatus;
	}

	public void setEwStatus(String ewStatus) {
		this.ewStatus = ewStatus;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMcc1() {
		return mcc1;
	}

	public void setMcc1(String mcc1) {
		this.mcc1 = mcc1;
	}

	public String getMcc2() {
		return mcc2;
	}

	public void setMcc2(String mcc2) {
		this.mcc2 = mcc2;
	}

	public String getMcc3() {
		return mcc3;
	}

	public void setMcc3(String mcc3) {
		this.mcc3 = mcc3;
	}

	public String getMnc1() {
		return mnc1;
	}

	public void setMnc1(String mnc1) {
		this.mnc1 = mnc1;
	}

	public String getMnc2() {
		return mnc2;
	}

	public void setMnc2(String mnc2) {
		this.mnc2 = mnc2;
	}

	public String getMnc3() {
		return mnc3;
	}

	public void setMnc3(String mnc3) {
		this.mnc3 = mnc3;
	}

	public String getLac1() {
		return lac1;
	}

	public void setLac1(String lac1) {
		this.lac1 = lac1;
	}

	public String getLac2() {
		return lac2;
	}

	public void setLac2(String lac2) {
		this.lac2 = lac2;
	}

	public String getLac3() {
		return lac3;
	}

	public void setLac3(String lac3) {
		this.lac3 = lac3;
	}

	public String getCellId1() {
		return cellId1;
	}

	public void setCellId1(String cellId1) {
		this.cellId1 = cellId1;
	}

	public String getCellId2() {
		return cellId2;
	}

	public void setCellId2(String cellId2) {
		this.cellId2 = cellId2;
	}

	public String getCellId3() {
		return cellId3;
	}

	public void setCellId3(String cellId3) {
		this.cellId3 = cellId3;
	}

	public String getRssi1() {
		return rssi1;
	}

	public void setRssi1(String rssi1) {
		this.rssi1 = rssi1;
	}

	public String getRssi2() {
		return rssi2;
	}

	public void setRssi2(String rssi2) {
		this.rssi2 = rssi2;
	}

	public String getRssi3() {
		return rssi3;
	}

	public void setRssi3(String rssi3) {
		this.rssi3 = rssi3;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
}
