package com.jfservice.common.bean.alpha.response;

import java.util.List;

import com.jfservice.common.bean.alpha.other.ClockInfoAlp;
import com.jfservice.common.bean.alpha.other.ContactInfoAlp;
import com.jfservice.common.bean.alpha.other.DeviceCfgInfoAlp;
import com.jfservice.common.bean.alpha.other.DeviceInfoAlp;
import com.jfservice.common.bean.alpha.other.FamilyInfoAlpha;
import com.jfservice.common.bean.alpha.other.FriendInfoAlpha;
import com.jfservice.common.bean.alpha.other.LocationInfoAlp;
import com.jfservice.common.bean.alpha.other.SceneModeInfoAlp;
import com.jfservice.common.bean.alpha.other.CountStepAlp;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;
public class RespJsonData {

	private String cmd;
	
	private int ret;
	
	private String msg;
	
	//心跳响应的和时间同步响应得
	private String devTime;
	
	private int haveUnread;

	//闹钟响应的
	private String msgId;
	
	private List<ClockInfoAlp> alarms;
	private List<ClockInfoAlp> disturb;
	
	private List<LocationInfoAlp> lctDatas;
	
	private List<ContactInfoAlp> contacts;
	
	private List<FamilyInfoAlpha> familys;
	
	private List<FamilyInfoAlpha> soss;
	
	private List<FriendInfoAlpha> friends;
	
	private List<SceneModeInfoAlp> profiles;
	
	private List<DeviceInfoAlp> group;
	
	
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
	//求救信息
	private String sosMsg;
	
	private String imei;
	private String high;
	private String weight;
	private String stepd;
	
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

	public String getStepd() {
		return stepd;
	}

	public void setStepd(String stepd) {
		this.stepd = stepd;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public List<DeviceInfoAlp> getGroup() {
		return group;
	}

	public void setGroup(List<DeviceInfoAlp> group) {
		this.group = group;
	}

	public List<ClockInfoAlp> getDisturb() {
		return disturb;
	}

	public void setDisturb(List<ClockInfoAlp> disturb) {
		this.disturb = disturb;
	}

	public String getSosMsg() {
		return sosMsg;
	}

	public void setSosMsg(String sosMsg) {
		this.sosMsg = sosMsg;
	}

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



	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public int getHaveUnread() {
		return haveUnread;
	}

	public void setHaveUnread(int haveUnread) {
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

	public List<FamilyInfoAlpha> getSoss() {
		return soss;
	}

	public void setSoss(List<FamilyInfoAlpha> soss) {
		this.soss = soss;
	} 
	
}
