package com.jfservice.common.bean.weike;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Parameters {

	@XStreamAsAttribute
	@XStreamAlias("SrvNum")
	private String srvNum;      //SOS求助号码
	
	@XStreamAsAttribute
	@XStreamAlias("GpsStatus")
	private String gpsStatus;   //GPS开启状态(0关,1开)
	
	@XStreamAsAttribute
	@XStreamAlias("GpsInterval")
	private int gpsInterval; //Gps上传间隔(分钟)传255表示自动模式
	
	@XStreamAsAttribute
	@XStreamAlias("GpsOff")
	private String gpsOff;   //关闭GPS的时间段
	
	@XStreamAsAttribute
	@XStreamAlias("VOICE")
	private String voice;   //亲情号码
	
	@XStreamAsAttribute
	@XStreamAlias("Whitelist")
	private String whiteList; //闹钟
	
	@XStreamAsAttribute
	@XStreamAlias("DNS")
	private String dns;  //dns
	
	@XStreamAsAttribute
	@XStreamAlias("IP")
	private String ip;  //ip
	
	@XStreamAsAttribute
	@XStreamAlias("time")
	private long time;  //时间戳

	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public int getGpsInterval() {
		return gpsInterval;
	}

	public void setGpsInterval(int gpsInterval) {
		this.gpsInterval = gpsInterval;
	}

	public String getGpsOff() {
		return gpsOff;
	}

	public void setGpsOff(String gpsOff) {
		this.gpsOff = gpsOff;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(String dns) {
		this.dns = dns;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
