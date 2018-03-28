package com.jfservice.common.bean.Jlt.subsri;

import java.util.List;

import com.jfservice.common.bean.alpha.other.ClockInfoAlp;
import com.jfservice.common.bean.alpha.other.ContactInfoAlp;
import com.jfservice.common.bean.alpha.other.CountStepAlp;
import com.jfservice.common.bean.alpha.other.DeviceCfgInfoAlp;
import com.jfservice.common.bean.alpha.other.FamilyInfoAlpha;
import com.jfservice.common.bean.alpha.other.FriendInfoAlpha;
import com.jfservice.common.bean.alpha.other.TextInfoAlp;

public class SubcriJsonData {

	private String cmd;

	private String phoneNum;

	private List<TextInfoAlp> texts;

	private List<ClockInfoAlp> alarms;

	private List<ContactInfoAlp> contacts;

	private List<FamilyInfoAlpha> familys;

	private List<FriendInfoAlpha> friends;

	private List<CountStepAlp> pedometerData; // 计步数
	
	private String deviceStatus;//设备状态

	// 推送奖章
	private String number;

	// 宝宝生活
	private String index;

	// 推送设备配置
	private DeviceCfgInfoAlp devCfg;

	// 查询话费短信指令
	private String smscont;

	// 天气状态
	private Integer status;

	private String msg;

	// 语音
	private String id;

	private String other;
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	private String sender;

	private String senderId;

	private Integer duration;

	private Integer len;

	private String format;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIson() {
		return ison;
	}

	public void setIson(String ison) {
		this.ison = ison;
	}

	private String repeat;

	private String time;

	private String ison;

	// 久坐提醒
	private String dura;

	private Integer sex;

	private String birthday;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public List<TextInfoAlp> getTexts() {
		return texts;
	}

	public void setTexts(List<TextInfoAlp> texts) {
		this.texts = texts;
	}

	public List<ClockInfoAlp> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<ClockInfoAlp> alarms) {
		this.alarms = alarms;
	}

	public List<ContactInfoAlp> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactInfoAlp> contacts) {
		this.contacts = contacts;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public List<FamilyInfoAlpha> getFamilys() {
		return familys;
	}

	public void setFamilys(List<FamilyInfoAlpha> familys) {
		this.familys = familys;
	}

	public List<FriendInfoAlpha> getFriends() {
		return friends;
	}

	public void setFriends(List<FriendInfoAlpha> friends) {
		this.friends = friends;
	}

	public List<CountStepAlp> getPedometerData() {
		return pedometerData;
	}

	public void setPedometerData(List<CountStepAlp> pedometerData) {
		this.pedometerData = pedometerData;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDura() {
		return dura;
	}

	public void setDura(String dura) {
		this.dura = dura;
	}

	public DeviceCfgInfoAlp getDevCfg() {
		return devCfg;
	}

	public void setDevCfg(DeviceCfgInfoAlp devCfg) {
		this.devCfg = devCfg;
	}

	public String getSmscont() {
		return smscont;
	}

	public void setSmscont(String smscont) {
		this.smscont = smscont;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

}
