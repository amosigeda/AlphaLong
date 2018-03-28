package com.jfservice.sys.setting.model;

public class Setting {

	private Integer id;
	private String serieNo;
	private Integer volume;
	private String map;
	private String fallOn;
	private String light;    //间隔时间
	private String gps_on;   //gps开关
	private String light_sensor;
	private String fall;
	
	private String shutdown;//远程关机
	private String repellent;
	private String heart;//心跳

    private String seac;
	private String listen;//监听
	private String rest;//恢复出厂设置
	private String res;
	private String veri;
	private String high;
	private String weight;
	private String sex;
	private String stepd;
	private String phone;
	private String nickName;
	private String birthday;
	
	
	
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStepd() {
		return stepd;
	}
	public void setStepd(String stepd) {
		this.stepd = stepd;
	}
	public String getShutdown() {
		return shutdown;
	}
	public void setShutdown(String shutdown) {
		this.shutdown = shutdown;
	}
	public String getRepellent() {
		return repellent;
	}
	public void setRepellent(String repellent) {
		this.repellent = repellent;
	}
	public String getHeart() {
		return heart;
	}
	public String getSeac() {
		return seac;
	}
	public void setSeac(String seac) {
		this.seac = seac;
	}
	public String getListen() {
		return listen;
	}
	public void setListen(String listen) {
		this.listen = listen;
	}
	public String getRest() {
		return rest;
	}
	public void setRest(String rest) {
		this.rest = rest;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getVeri() {
		return veri;
	}
	public void setVeri(String veri) {
		this.veri = veri;
	}
	public void setHeart(String heart) {
		this.heart = heart;
	}
    private String belongProject;
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerieNo() {
		return serieNo;
	}
	public void setSerieNo(String serieNo) {
		this.serieNo = serieNo;
	}
	public Integer getVolume() {
		return volume;
	}
	public String getGps_on() {
		return gps_on;
	}
	public void setGps_on(String gps_on) {
		this.gps_on = gps_on;
	}
	public String getLight_sensor() {
		return light_sensor;
	}
	public void setLight_sensor(String light_sensor) {
		this.light_sensor = light_sensor;
	}
	public String getFall() {
		return fall;
	}
	public void setFall(String fall) {
		this.fall = fall;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getFallOn() {
		return fallOn;
	}
	public void setFallOn(String fallOn) {
		this.fallOn = fallOn;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
}
