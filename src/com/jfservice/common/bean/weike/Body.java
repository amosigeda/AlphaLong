package com.jfservice.common.bean.weike;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Body {

	@XStreamAlias("Location")
	private Location location;
	
	@XStreamAlias("Battery")
	private Battery battery;
	
	@XStreamAlias("Weather")
	private Weather weather;
	
	@XStreamAlias("Parameters")
	private Parameters parameters;
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Battery getBattery() {
		return battery;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
}
