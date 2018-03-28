package com.jfservice.common.bean.weike;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Result {

	@XStreamAsAttribute
	@XStreamAlias("status")
	private String status;   //状态编码
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
