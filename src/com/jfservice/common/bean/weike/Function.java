package com.jfservice.common.bean.weike;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Function {

	@XStreamAsAttribute
	private String id;   //表示访问参数的接口识别
	
	@XStreamAsAttribute
	@XStreamAlias("watchid")
	private String watchId;  //手表id

	@XStreamAsAttribute
	@XStreamAlias("Verification")
	private String verification;  //加密串,暂时没有意义
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWatchId() {
		return watchId;
	}

	public void setWatchId(String watchId) {
		this.watchId = watchId;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}
}
