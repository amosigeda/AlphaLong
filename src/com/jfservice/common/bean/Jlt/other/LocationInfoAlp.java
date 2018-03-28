package com.jfservice.common.bean.Jlt.other;

import java.util.List;

public class LocationInfoAlp {

	private String time;
	
	private Integer pwr;
	
	private double lon;
	
	private double lat;
	
	private double accuracy;
	
	private Integer returnPos;
	
	private LbsInfoAlp lbs;
	
	private List<WifiInfoAlp> wifi;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getPwr() {
		return pwr;
	}

	public void setPwr(Integer pwr) {
		this.pwr = pwr;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public Integer getReturnPos() {
		return returnPos;
	}

	public void setReturnPos(Integer returnPos) {
		this.returnPos = returnPos;
	}

	public LbsInfoAlp getLbs() {
		return lbs;
	}

	public void setLbs(LbsInfoAlp lbs) {
		this.lbs = lbs;
	}

	public List<WifiInfoAlp> getWifi() {
		return wifi;
	}

	public void setWifi(List<WifiInfoAlp> wifi) {
		this.wifi = wifi;
	} 
}
