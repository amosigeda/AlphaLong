package com.jfservice.common.bean.Jlt.other;

public class DeviceInfoAlp {

	private String IMEI;
	
	private String IMSI;
	
	private String productId;

	private String fwVer;
	
	private String fwBuild;
	
	private String mcuBuild;
	
	private String btName;
	
	private String btMac;
	
	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getFwVer() {
		return fwVer;
	}

	public void setFwVer(String fwVer) {
		this.fwVer = fwVer;
	}

	public String getFwBuild() {
		return fwBuild;
	}

	public void setFwBuild(String fwBuild) {
		this.fwBuild = fwBuild;
	}

	public String getMcuBuild() {
		return mcuBuild;
	}

	public void setMcuBuild(String mcuBuild) {
		this.mcuBuild = mcuBuild;
	}

	public String getBtName() {
		return btName;
	}

	public void setBtName(String btName) {
		this.btName = btName;
	}

	public String getBtMac() {
		return btMac;
	}

	public void setBtMac(String btMac) {
		this.btMac = btMac;
	}
	
}
