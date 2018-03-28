package com.jfservice.common.bean.Jlt.response;

import java.util.List;

import com.jfservice.common.bean.alpha.other.ClockInfoAlp;
import com.jfservice.common.bean.alpha.other.ContactInfoAlp;
import com.jfservice.common.bean.alpha.other.DeviceCfgInfoAlp;
import com.jfservice.common.bean.alpha.other.FamilyInfoAlpha;
import com.jfservice.common.bean.alpha.other.FriendInfoAlpha;
import com.jfservice.common.bean.alpha.other.LocationInfoAlp;
import com.jfservice.common.bean.alpha.other.SceneModeInfoAlp;
import com.jfservice.common.bean.alpha.other.CountStepAlp;
public class RespJsonData {

	private String cmd;
	
	private String ret;
	
	private String msg;
	
	//心跳响应的和时间同步响应得
	private String devTime;
	
	private String haveUnread;

	//闹钟响应的
	private String msgId;
	
	private List<ClockInfoAlp> alarms;
	
	private List<LocationInfoAlp> lctDatas;
	
	private List<ContactInfoAlp> contacts;
	
	private List<FamilyInfoAlpha> familys;
	
	private List<FriendInfoAlpha> friends;
	
	private List<SceneModeInfoAlp> profiles;
	
	
	//响应小孩信息
	private String name;
		
	private Integer sex;
		
	private String birthday;
		
	//响应动态验证码
	private String captcha;
	
	//响应话费查询
	private String operator;
	
	//对客户端的响应操作
	private String resp;
	
	private String result;
	
	//推送设备配置
	private DeviceCfgInfoAlp devCfg;
		
	//推送定位数据
	private List<Double> position;
	//电池百分比
	private String batPower;
	
	//查询话费短信指令
	private String smscont;
	
	private String id;
	
	public String getSmscont() {
		return smscont;
	}

	public void setSmscont(String smscont) {
		this.smscont = smscont;
	}
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDevTime() {
		return devTime;
	}

	public void setDevTime(String devTime) {
		this.devTime = devTime;
	}

	public String getHaveUnread() {
		return haveUnread;
	}

	public void setHaveUnread(String haveUnread) {
		this.haveUnread = haveUnread;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public List<ClockInfoAlp> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<ClockInfoAlp> alarms) {
		this.alarms = alarms;
	}

	public List<LocationInfoAlp> getLctDatas() {
		return lctDatas;
	}

	public void setLctDatas(List<LocationInfoAlp> lctDatas) {
		this.lctDatas = lctDatas;
	}

	public List<ContactInfoAlp> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactInfoAlp> contacts) {
		this.contacts = contacts;
	}

	public List<FamilyInfoAlpha> getFamilys() {
		return familys;
	}

	public void setFamilys(List<FamilyInfoAlpha> familys) {
		this.familys = familys;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public List<FriendInfoAlpha> getFriends() {
		return friends;
	}

	public void setFriends(List<FriendInfoAlpha> friends) {
		this.friends = friends;
	}

	public List<SceneModeInfoAlp> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<SceneModeInfoAlp> profiles) {
		this.profiles = profiles;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getResp() {
		return resp;
	}

	public void setResp(String resp) {
		this.resp = resp;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public DeviceCfgInfoAlp getDevCfg() {
		return devCfg;
	}

	public void setDevCfg(DeviceCfgInfoAlp devCfg) {
		this.devCfg = devCfg;
	}

	public List<Double> getPosition() {
		return position;
	}

	public void setPosition(List<Double> position) {
		this.position = position;
	} 

	public String getBatPower() {
		return batPower;
	}

	public void setBatPower(String batPower) {
		this.batPower = batPower;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 
}
