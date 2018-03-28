package com.jfservice.common.bean.weike;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Weather {

	@XStreamAsAttribute
	@XStreamAlias("w_type")
	private String wType; //天气类型
	
	@XStreamAsAttribute
	@XStreamAlias("temp")
	private String temp;  //温度范围
	
	@XStreamAsAttribute
	@XStreamAlias("CurTemp")
	private String curTemp; //当前温度
	
	@XStreamAsAttribute
	@XStreamAlias("Warning")
	private String warning;  //警告

	public String getwType() {
		return wType;
	}

	public void setwType(String wType) {
		this.wType = wType;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getCurTemp() {
		return curTemp;
	}

	public void setCurTemp(String curTemp) {
		this.curTemp = curTemp;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}
}
