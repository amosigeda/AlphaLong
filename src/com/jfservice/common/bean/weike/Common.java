package com.jfservice.common.bean.weike;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Common {

	@XStreamAlias("result")
	private Result result;

	@XStreamAlias("function")
	private Function function;
	
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
}
