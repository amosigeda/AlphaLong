package com.jfservice.common.bean.alpha.other;

import java.util.List;

public class DeviceCfgInfoAlp {

	private Integer isStepOn;
	
	private Integer stepRate;
	
	private Integer isAutoLctOn;
	
	private Integer autoLctRate;
	
	private String btName;
	
	private Integer hbRate;
		
	private Integer isNotLctPeriodOn;
	
	private String notLctPeriod;
	
	private Integer isAccurateLctOn;
	
	private Integer isAllowPowerOff;
	
	private Integer isAllowOtherCall;
	
	private Integer gpsMode;
	
	private Integer isMoveOn;
	
	private Integer safePower;
	
	private Integer isAutoAnswer;
	
	private Integer isCtrLcd;
	
	private CmdConfig cmdConnCfg;
	
	private DataConfig dataConnCfg;
	
	private List<String> sosNum;
	
	public Integer getIsStepOn() {
		return isStepOn;
	}

	public void setIsStepOn(Integer isStepOn) {
		this.isStepOn = isStepOn;
	}

	public Integer getStepRate() {
		return stepRate;
	}

	public void setStepRate(Integer stepRate) {
		this.stepRate = stepRate;
	}

	public Integer getIsAutoLctOn() {
		return isAutoLctOn;
	}

	public void setIsAutoLctOn(Integer isAutoLctOn) {
		this.isAutoLctOn = isAutoLctOn;
	}

	public Integer getAutoLctRate() {
		return autoLctRate;
	}

	public void setAutoLctRate(Integer autoLctRate) {
		this.autoLctRate = autoLctRate;
	}

	public String getBtName() {
		return btName;
	}

	public void setBtName(String btName) {
		this.btName = btName;
	}

	public Integer getHbRate() {
		return hbRate;
	}

	public void setHbRate(Integer hbRate) {
		this.hbRate = hbRate;
	}

	public Integer getIsNotLctPeriodOn() {
		return isNotLctPeriodOn;
	}

	public void setIsNotLctPeriodOn(Integer isNotLctPeriodOn) {
		this.isNotLctPeriodOn = isNotLctPeriodOn;
	}

	public String getNotLctPeriod() {
		return notLctPeriod;
	}

	public void setNotLctPeriod(String notLctPeriod) {
		this.notLctPeriod = notLctPeriod;
	}

	public Integer getIsAccurateLctOn() {
		return isAccurateLctOn;
	}

	public void setIsAccurateLctOn(Integer isAccurateLctOn) {
		this.isAccurateLctOn = isAccurateLctOn;
	}

	public Integer getIsAllowPowerOff() {
		return isAllowPowerOff;
	}

	public void setIsAllowPowerOff(Integer isAllowPowerOff) {
		this.isAllowPowerOff = isAllowPowerOff;
	}

	public Integer getIsAllowOtherCall() {
		return isAllowOtherCall;
	}

	public void setIsAllowOtherCall(Integer isAllowOtherCall) {
		this.isAllowOtherCall = isAllowOtherCall;
	}

	public Integer getGpsMode() {
		return gpsMode;
	}

	public void setGpsMode(Integer gpsMode) {
		this.gpsMode = gpsMode;
	}

	public Integer getIsMoveOn() {
		return isMoveOn;
	}

	public void setIsMoveOn(Integer isMoveOn) {
		this.isMoveOn = isMoveOn;
	}

	public Integer getSafePower() {
		return safePower;
	}

	public void setSafePower(Integer safePower) {
		this.safePower = safePower;
	}

	public Integer getIsAutoAnswer() {
		return isAutoAnswer;
	}

	public void setIsAutoAnswer(Integer isAutoAnswer) {
		this.isAutoAnswer = isAutoAnswer;
	}

	public Integer getIsCtrLcd() {
		return isCtrLcd;
	}

	public void setIsCtrLcd(Integer isCtrLcd) {
		this.isCtrLcd = isCtrLcd;
	}

	public CmdConfig getCmdConnCfg() {
		return cmdConnCfg;
	}

	public void setCmdConnCfg(CmdConfig cmdConnCfg) {
		this.cmdConnCfg = cmdConnCfg;
	}

	public DataConfig getDataConnCfg() {
		return dataConnCfg;
	}

	public void setDataConnCfg(DataConfig dataConnCfg) {
		this.dataConnCfg = dataConnCfg;
	}

	public List<String> getSosNum() {
		return sosNum;
	}

	public void setSosNum(List<String> sosNum) {
		this.sosNum = sosNum;
	}

	public static class DataConfig{
		private Integer port;
		
		private String ip;
		
		private String host;

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}
	}
	
	public static class CmdConfig{
		private Integer port;
		
		private String ip;
		
		private String host;

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}
	}
}
