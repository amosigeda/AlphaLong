package com.jfservice.common.bean.Jlt.request;

import java.util.List;

import net.sf.jsqlparser.schema.Database;

import com.jfservice.common.bean.alpha.other.ClockInfoAlp;
import com.jfservice.common.bean.alpha.other.ContactInfoAlp;
import com.jfservice.common.bean.alpha.other.DeviceInfoAlp;
import com.jfservice.common.bean.alpha.other.FamilyInfoAlpha;
import com.jfservice.common.bean.alpha.other.FriendInfoAlpha;
import com.jfservice.common.bean.alpha.other.LocationInfoAlp;
import com.jfservice.common.bean.alpha.other.TextInfoAlp;
import com.jfservice.common.bean.alpha.other.CountStepAlp;
import com.jfservice.sys.client.handler.alpha.DataBean;
public class ReqJsonData {

	private String cmd;     //命令指示
	
	private String devId;   //设备imei
	
	private String sCode;
	
	private String hbRate;     
	
	private DeviceInfoAlp devInfo;

	//上传请求添加联系人
	private Integer type;
	
	private String id;
	
	//上传定位数据和时间同步
	private Integer batPower;
	//上传计步数据
	private List<CountStepAlp> stepData;
	
	//上传SOS
	private String time;
	
	//上传移动状态
	private String state;
	
	//手表上报拦截的电话号码
	private String number;
	
	private String timeStamp;
	
	private Integer err;
	
	//上报电话号码
	private String result;
	
	//宝宝签到
	private String date;
	
	//睡眠检测结果
	private Integer totaltime;
	
	private Integer deeptime;
	
	//客户端请求参数
	private String userId;
	
	private String deviceId;
	
	private String bg;
	
	//客户端上传监听
	private String phoneNumber;
	
	private String msg;
	  	
	private List<TextInfoAlp> texts;
	
	private List<ClockInfoAlp> alarms;
	
	private List<LocationInfoAlp> lctDatas;
	
	private List<ContactInfoAlp> contacts;
	
	private List<FamilyInfoAlpha> familys;
	
	private List<FriendInfoAlpha> friends;
	
	private String operator;
	


	//上传语音
	private Integer len;
	
	private Integer duration;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBg() {
		return bg;
	}

	public void setBg(String bg) {
		this.bg = bg;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getHbRate() {
		return hbRate;
	}

	public void setHbRate(String hbRate) {
		this.hbRate = hbRate;
	}

	public DeviceInfoAlp getDevInfo() {
		return devInfo;
	}

	public void setDevInfo(DeviceInfoAlp devInfo) {
		this.devInfo = devInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBatPower() {
		return batPower;
	}

	public void setBatPower(Integer batPower) {
		this.batPower = batPower;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public void setLctData(List<LocationInfoAlp> lctDatas) {
		this.lctDatas = lctDatas;
	}

	public List<ContactInfoAlp> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactInfoAlp> contacts) {
		this.contacts = contacts;
	}

	public void setLctDatas(List<LocationInfoAlp> lctDatas) {
		this.lctDatas = lctDatas;
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

	public List<TextInfoAlp> getTexts() {
		return texts;
	}

	public void setTexts(List<TextInfoAlp> texts) {
		this.texts = texts;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getErr() {
		return err;
	}

	public void setErr(Integer err) {
		this.err = err;
	}

	public Integer getTotaltime() {
		return totaltime;
	}

	public void setTotaltime(Integer totaltime) {
		this.totaltime = totaltime;
	}

	public Integer getDeeptime() {
		return deeptime;
	}

	public void setDeeptime(Integer deeptime) {
		this.deeptime = deeptime;
	}
		public List<CountStepAlp> getStepData() {
		return stepData;
	}

	public void setStepData(List<CountStepAlp> stepData) {
		this.stepData = stepData;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	
}
