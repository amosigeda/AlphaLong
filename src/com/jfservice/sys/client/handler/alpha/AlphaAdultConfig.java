package com.jfservice.sys.client.handler.alpha;

public final class AlphaAdultConfig {

	/*
	 * 客户端上传协议接口
	 */
	public final static String reqConnect = "reqConnect";    //上传长连接请求
	public final static String heartbeat = "heartbeat";      //上传心跳
	public final static String devCfg = "devCfg";            //服务器下发终端可配置参数(设备登陆成功后)
	public final static String findDevice = "findDevice";    //找手表
	public final static String monitor = "monitor";          //监听
	public final static String rMonitor = "rMonitor";        //响应监听
	public final static String shutdown = "shutdown";          //关机
	public final static String reboot = "reboot";          //重启
	public final static String reset = "reset";          //恢复出产设置
	public final static String getDevCfg = "getDevCfg";    //建立长连接后,设备端会调用这个接口
	public final static String reqAlarms = "reqAlarms";    //请求闹钟
	public final static String rAlarms = "rAlarms";        //响应闹钟
	public final static String alarms = "alarms";          //推送闹钟
	public final static String rDevCfg = "rDevCfg";        //响应配置
	public final static String contacts ="contacts";       //推送联系人列表
	public final static String reqContacts = "reqContacts"; //请求联系人
	public final static String reqAddContacts="reqAddContacts"; //上传添加联系人请求
	public final static String rReqAddContacts="rReqAddContacts"; //响应添加联系人请求
	public final static String reqChildInfo = "reqChildInfo"; //请求孩子信息
	public final static String setChildInfo = "setChildInfo"; //设置孩子信息
	public final static String reqVoice = "reqVoice";    //请求新语音
	public final static String pushVoice = "pushVoice";  //推送语音
	public final static String rPushVoice = "rPushVoice";  //响应语音
	public final static String reqProfiles = "reqProfiles";  //请求情景模式
	public final static String profiles = "profiles";    //推送情景模式
	public final static String rProfiles = "rProfiles";  //响应推送情景模式
	public final static String uploadLctData = "uploadLctData"; //设备上传定位信息
	public final static String reqFamilyNumber = "reqFamilyNumber"; //请求推送亲情号码
	//public final static String setFamilyNumber = "setFamilyNumber"; //推送亲情号码
	public final static String rSetFamilyNumber = "rSetFamilyNumber";//响应亲情号码
	public final static String reqSosNumber = "reqSosNumber"; //请求推送sos号码
	public final static String setSosNumber = "setSosNumber"; //推送sos号码
	public final static String rSetSosNumber = "rSetSosNumber";//响应sos号码
	public final static String reqDisturb = "reqDisturb"; //请求推送防打扰
	public final static String setDisturb = "setDisturb"; //推送防打扰
	public final static String rSetDisturb = "rSetDisturb";//响应推送防打扰
	public final static String reqGroups  = "reqGroups"; //请求推送聊天列表
	public final static String setGroups  = "setGroups"; //推送聊天列表
	public final static String rSetGroups  = "rSetGroups";//响应推送聊天列表
	public final static String moveState = "moveState";   //终端移动状态
	public final static String reqTimeSync = "reqTimeSync"; //请求时间同步
	public final static String rReqTimeSync = "rReqTimeSync";  //响应请求时间同步
	public final static String reqLocate = "reqLocate";       //服务器端请求定位指令
	public final static String devPos = "devPos";             //推送经纬度
    public final static String getDevInfo="getDevInfo";   //得到终端设备相关信息
	public final static String  uploadDevInfo="uploadDevInfo";//上传终端设备相关信息
	public final static String uploadPedometerData="uploadPedometerData";//上传计步数据
	public final static String reqPedometerData="reqPedometerData";//请求计步数据
	public final static String uploadHeartRate ="uploadHeartRate ";//心率上传
	public final static String rUploadHeartRate ="rUploadHeartRate ";//心率上传
	public final static String reqMySelfInfo ="reqMySelfInfo";//请求个人信息
	public final static String rSetMyselfInfo  ="rSetMyselfInfo";//响应请求个人信息
	public final static String setMyselfInfo  ="setMyselfInfo  ";//推送个人信息
	
	public final static String reqCaptcha="reqCaptcha";//请求动态验证码
	public final static String rReqCaptcha="rReqCaptcha";//响应动态验证码
	public final static String fwUpgrade="fwUpgrade";//升级终端固件
	public final static String rejectNumber="rejectNumber";//拦截号码上报
	public final static String LeaveAlarm="LeaveAlarm";//上报脱手报警
	public final static String ResCharge="ResCharge";//响应查询话费指令
	public final static String SetCheckSms="SetCheckSms";//下发查询话费指令
	public final static String ChargeResult="ChargeResult";//上报查询话费结果
	public final static String BusCheckCharge="BusCheckCharge ";//下发查询公交余额指令
	public final static String BusChargeResult="BusChargeResult";//上报查询话费结果
	public final static String Weather="Weather";//下发天气预报信息
	public final static String Medal="Medal";//推送奖章
	public final static String Reminder="Reminder";//宝宝生活提醒
	public final static String Sign="Sign";//宝宝签到
	public final static String SetSleep="SetSleep";//设置睡眠监测
	public final static String SleepResult="SleepResult";//上传睡眠监测结果
	public final static String SetSit="SetSit";//久坐提醒
	public final static String uploadSos="uploadSos";  //上传SOS信息
	public final static String setFriendNumber="setFriendNumber"; //推送好友号码
	public final static String rSetFriendNumber="rSetFriendNumber"; //响应推送好友号码
	public final static String reqFriendNumber="reqFriendNumber";  //请求推送好友号码
	public final static String texts="texts";
	public final static String CheckCharge="CheckCharge";//下发查询话费指令
	public final static String rupLoadPedometer="rupLoadPedometer";  //响应计步数据
	public final static String rmonitor="rmonitor"; //响应监听
	public final static String uploadVoice = "uploadVoice";    //上传语音
	public final static String rUploadVoice = "rUploadVoice";  //响应上传语音
	public final static String voiceRead = "voiceRead";  //上传语音已读
	/**
	 * 客户端响应协议
	 */
	public final static String rReqConnect = "rReqConnect";
	public final static String rHeartbeat = "rHeartbeat";
	
	/**
	 * app上传协议
	 */
	public final static String T_U_RESTO = "T_U_RESTO";    //恢复出产设置
	public final static String T_U_MONITOR = "T_U_MONITOR";//监听
	public final static String T_U_RMONITOR="T_U_RMONITOR"; //响应监听
	public final static String T_U_FIND = "T_U_FIND";      //找手表
	public final static String T_U_SHUTDOWN = "T_U_SHUTDOWN";      //关机
	public final static String T_U_REBOOT = "T_U_REBOOT";      //重启
	public final static String T_U_RESET = "T_U_RESET";      //恢复出产设置
	public final static String T_U_LOCATE = "T_U_LOCATE";    //服务器端请求定位指令
	public final static String T_U_TEXT="T_U_TEXT";		  				//推送文本消息
	public final static String T_U_ALARMS="T_U_ALARMS";					//推送闹钟
	public final static String T_U_CONTACTS="T_U_CONTACTS"; 				//推送联系人列表
	public final static String T_U_RREQADDCONTACTS="T_U_RREQADDCONTACTS"; //响应"添加联系人请求"
	public final static String T_U_SETFAMILYNUMBER="T_U_SETFAMILYNUMBER"; //推送亲情号码
	public final static String T_U_SETSOSNUMBER="T_U_SETSOSNUMBER"; //推送sos号码
	public final static String T_U_SETGROUPS="T_U_SETGROUPS"; //推送聊天列表
	public final static String T_U_SETDISTURB="T_U_SETDISTURB"; //推送防打扰
	public final static String T_U_SETFRIENDNUMBER="T_U_SETFRIENDNUMBER"; //推送好友号码
    public final static String T_U_REQPEDOMETERDATA="T_U_REQPEDOMETERDATA";//请求计步数据
	public final static String T_U_FWUPGRADE="T_U_FWUPGRADE";//升级终端固件
	public final static String T_U_CHECKCHARGE="T_U_CHECKCHARGE";//下发查询话费请求
	public final static String T_U_SETCHECKSMS="T_U_SETCHECKSMS";//下发查询话费指令
	public final static String T_U_WEATHER="T_U_WEATHER";//下发天气预报信息
	public final static String T_U_MEDAL="T_U_MEDAL";//推送奖章
	public final static String T_U_REMINDER="T_U_REMINDER";//宝宝生活提醒
	public final static String T_U_SETSLEEP="T_U_SETSLEEP";//设置睡眠监测
	public final static String T_U_SETSIT="T_U_SETSIT";//设置久坐提醒
	public final static String T_U_LK = "T_U_LK";      //链路接口
	public final static String T_U_LOGIN = "T_U_LOGIN";//登陆接口
	public final static String T_U_GETDEVINFO="T_U_GETDEVINFO";
	public final static String T_U_DEV_ALARMS="T_U_DEV_ALARMS";			//推送闹钟
	public final static String T_U_DELALARMS = "T_U_DELALARMS";         //删除闹钟 
	public final static String T_U__DEV_CONTACTS="T_U_DEV_CONTACTS"; 		//推送联系人列表
	public final static String T_U_DEV_SETFRIENDNUMBER="T_U_DEV_SETFRIENDNUMBER"; //推送好友号码
	public final static String T_U_DELNUMBER = "T_U_DELNUMBER";  //删除亲情号码或者好友
	public final static String T_U_REQLOCATE="T_U_REQLOCATE";				//apk上传定位请求
	public final static String T_U_SETCHILDINFO="T_U_SETCHILDINFO";       //apk上传孩子信息
	public final static String T_U_RUPLOADPEDOMETER="T_U_RUPLOADPEDOMETER"; //响应计步数据
	public final static String T_U_RCHECKCHARGE = "T_U_RCHECKCHARGE";    //服务器下发话费
	public final static String reqVoiceTest = "reqVoiceTest";        //测试语音给app接口
	public final static String T_U_VOICE_TEST = "T_U_VOICE_TEST";        //测试语音下发给设备接口
	public final static String T_U_VOICE = "T_U_VOICE";     //语音下发
	public final static String T_U_BUSCHECKCHARGE = "T_U_BUSCHECKCHARGE";     //推送查询公交余额指令
	public final static String T_U_SETGROUP="T_U_SETGROUP";     //推送聊天列表
	public final static String T_U_SETDEVICEINFO="T_U_SETDEVICEINFO";     //推送设备使用者个人信息
}
