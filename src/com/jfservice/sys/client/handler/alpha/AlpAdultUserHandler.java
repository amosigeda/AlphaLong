package com.jfservice.sys.client.handler.alpha;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.jfservice.common.bean.alpha.other.ClockInfoAlp;
import com.jfservice.common.bean.alpha.other.ContactInfoAlp;
import com.jfservice.common.bean.alpha.other.DeviceCfgInfoAlp;
import com.jfservice.common.bean.alpha.other.DeviceInfoAlp;
import com.jfservice.common.bean.alpha.other.FamilyInfoAlpha;
import com.jfservice.common.bean.alpha.other.TextInfoAlp;
import com.jfservice.common.bean.alpha.request.ReqJsonData;
import com.jfservice.common.bean.alpha.response.RespJsonData;
import com.jfservice.common.bean.alpha.subcri.SubcriJsonData;
import com.jfservice.common.lang.CalculatorUtils;
import com.jfservice.common.lang.Constant;
import com.jfservice.sys.client.handler.impl.ClientMessageEventImpl;
import com.jfservice.sys.client.manager.ClientSessionManager;
import com.jfservice.sys.clock.model.Clock;
import com.jfservice.sys.clock.service.ClockService;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;
import com.jfservice.sys.deviceactiveinfo.service.DeviceActiveService;
import com.jfservice.sys.family.model.Family;
import com.jfservice.sys.family.service.FamilyService;
import com.jfservice.sys.location.model.LocationInfo;
import com.jfservice.sys.location.service.LocationService;
import com.jfservice.sys.media.model.Media;
import com.jfservice.sys.media.service.MediaService;
import com.jfservice.sys.setting.model.Setting;
import com.jfservice.sys.setting.service.SettingService;

public class AlpAdultUserHandler extends ClientMessageEventImpl {
	/*public static void main(String[] args) {
		String b="1232131#0#1#2@jdalfjd#ad#ad#sg@1232131#0#1#2";
	
		String[] receiveAll = CalculatorUtils.getSplitRegx(
				b, "@");
		System.out.println(receiveAll[0]);
		System.out.println(receiveAll[1]);
		System.out.println(receiveAll[2]);
		for(int z=0;z<receiveAll.length;z++){
			System.out.println(receiveAll.length);
		String[] receive= CalculatorUtils.getSplitRegx(receiveAll[z], "#");
		System.out.println(receive[0]);
		System.out.println(receive[1]);
		System.out.println(receive[2]);
		System.out.println(receive[3]);
		System.out.println("-------------------");
		}
	}*/

	private final static String TAG = AlpAdultUserHandler.class.getSimpleName();
	private Logger logger = Logger.getLogger(AlpAdultUserHandler.class);

	@Autowired
	private ApplicationContext mApplicationContext;

	@Autowired
	private MediaService mMediaService;

	@Autowired
	private DeviceActiveService mDeviceActiveService;

	@Autowired
	private FamilyService mFamilyService;

	@Autowired
	private SettingService mSettingService;

	@Autowired
	private ClockService mClockService;

	@Autowired
	private LocationService mLocationService;

	private ClientSessionManager mClientSessionManager; // 客户端session的保存
	SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddhhmms");

	/**
	 * 根据alpha指定的协议
	 */
	@Override
	public void handler(Object message, IoSession session) {
		// TODO Auto-generated method stub
		mClientSessionManager = (ClientSessionManager) mApplicationContext
				.getBean("clientSessionManager");
		final RespJsonData respJsonData = new RespJsonData();
		
		SubcriJsonData subData = new SubcriJsonData();

		// 其他参数
		DeviceActive deviceActive = new DeviceActive();
		String resp = "";
		String sub = "";
		if (message != null && session != null) {
			try {
				DataBean mDataBean = new DataBean();
				logger.info("message-------------"+message);
				DataBean dataBean = (DataBean) message;
				logger.info("dataBean是-------"+dataBean);
				/*ReqJsonData reqJsonData = JSON.parseObject(
						dataBean.getContent(), ReqJsonData.class);*/
				try {
				logger.info("mapper前");
				ObjectMapper mapper=new ObjectMapper();
				logger.info("mapper后");
				String dg=dataBean.getContent();
				System.out.println("dddddddddddddddddddddddddddd------------"+dg);
				logger.info("(DataBean) message是-----------"+dg);
				ReqJsonData	reqJsonData = mapper.readValue(dg, ReqJsonData.class);
				logger.info("weitechao******************");
				
				String uid = reqJsonData.getUserId();
				String did = reqJsonData.getDeviceId();
				String b_g = reqJsonData.getBg();
				String cmd = reqJsonData.getCmd();
				logger.info("AlpUserHandler请求接口=" + cmd + ",uid=" + uid
						+ ",did=" + did + ",b_g=" + b_g);

				if (uid == null || did == null || b_g == null || cmd == null
						|| uid.equals("0") || did.equals("0")
						|| b_g.equals("0")) {
					respJsonData.setResult("-2");
					throw new NullPointerException();
				}
				boolean bolSub = true; // 是否需要下发
				boolean bolClose = false;  //是否关机、重启
				StringBuffer backSb = new StringBuffer();
				respJsonData.setResp(cmd + "_RE");
				if (cmd.equals(AlphaAdultConfig.T_U_LK)) {
					uid = "u_" + uid;
					String tempUid = (String) session.setAttributeIfAbsent(
							"id", uid); // 格式:u_+用户id,若有值,则获取,否则set进去
					logger.info("链路接口的sessionId=" + session.getId());
					logger.info("保存的=" + tempUid);
					if (mClientSessionManager.getSessionId(uid) != null) {
						mClientSessionManager.removeSessionId(uid);
					}
					mClientSessionManager.addSessionId(uid, session);
					respJsonData.setResult("1");
					bolSub = false; // 不需要下发
				} else if (cmd.equals(AlphaAdultConfig.T_U_LOGIN)) {
					Clock clock = new Clock();
					StringBuffer sb = new StringBuffer();

					clock.setDid(Integer.valueOf(did));
					List<Clock> mTempClockList = mClockService.find(clock);
					int length = mTempClockList.size();
					for (int i = 0; i < length; i++) {
						Clock tempClo = mTempClockList.get(i);
						if (i != 0) {
							sb.append("#");
						}
						sb.append(tempClo.getType()).append("@")
								.append(tempClo.getId()).append("@")
								.append(tempClo.getName()).append("@")
								.append(tempClo.getRepeatTimes()).append("@")
								.append(tempClo.getClock()).append("@")
								.append(tempClo.getVibrate()).append("@")
								.append(tempClo.getRing()).append("@")
								.append(tempClo.getRingId()).append("@")
								.append(tempClo.getVol()).append("@")
								.append(tempClo.getStatu());
					}
					respJsonData.setMsg(sb.toString());
					respJsonData.setResult("1");
					bolSub = false;
				} else if (cmd.equals(AlphaAdultConfig.T_U_RESTO)) {
					logger.info("恢复出厂设置");
					deviceActive.setId(Integer.valueOf(did));
					deviceActive.setDeviceDisable("1"); // 激活状态
					List<DeviceActive> deviceOne = mDeviceActiveService
							.find(deviceActive);
					logger.info("---------------deviceOne"+deviceOne);
					if (deviceOne.size() <= 0) {
						logger.info("deviceOne的长度"+deviceOne.size());
						logger.info("请求接口cmd0=" + cmd);
						respJsonData.setResult("0");
					} else {
						logger.info("请求接口cmd1=" + cmd);
						List<String> sosNums = new ArrayList<String>();
						DeviceCfgInfoAlp deviceCfg = new DeviceCfgInfoAlp();
						DeviceCfgInfoAlp.DataConfig dataCfg = new DeviceCfgInfoAlp.DataConfig();
						DeviceCfgInfoAlp.CmdConfig cmdCfg = new DeviceCfgInfoAlp.CmdConfig();

						deviceCfg.setIsStepOn(0);
						deviceCfg.setStepRate(60);
						deviceCfg.setIsAutoLctOn(1);
						deviceCfg.setAutoLctRate(45);
						deviceCfg.setBtName("test");
						deviceCfg.setIsNotLctPeriodOn(0);
						deviceCfg.setIsAccurateLctOn(1);
						deviceCfg.setIsAllowPowerOff(1);
						deviceCfg.setIsAllowOtherCall(1);
						deviceCfg.setGpsMode(1);
						deviceCfg.setIsMoveOn(0);
						deviceCfg.setHbRate(30);
						deviceCfg.setSafePower(15);
						deviceCfg.setIsAutoAnswer(0);
						deviceCfg.setIsCtrLcd(1);

						sosNums.add("15019420562");
						sosNums.add("15019434605");
						deviceCfg.setSosNum(sosNums);

						dataCfg.setHost("www.gpscarecare.com");
						dataCfg.setPort(1306);

						cmdCfg.setHost("www.gpscarecare.com");
						cmdCfg.setPort(1306);

						subData.setCmd(AlphaAdultConfig.devCfg);
						deviceCfg.setDataConnCfg(dataCfg);
						deviceCfg.setCmdConnCfg(cmdCfg);

						subData.setDevCfg(deviceCfg);

						sub = JSON.toJSONString(subData);
						logger.info("要配置设备的数据id=" + did);
						
						Family family=new Family();
						String IMEI=deviceOne.get(0).getDeviceImei();//先拿到IMEI
						logger.info("--------------------------"+IMEI);
						if(!IMEI.equals("")){
						family.setSerieNo(IMEI);
						List<Family> familyOne = mFamilyService.find(family);
                        List<Integer> Is=new ArrayList<Integer>();
						for(int i=0;i<familyOne.size();i++){
							Is.add(familyOne.get(i).getId());
							logger.info("-----------------"+Is);
							logger.info("-----------------");
						}
						
						int deleteAll = mFamilyService.batchDelete(Is);
						logger.info("++-------------------"+deleteAll);
						if(deleteAll>0){
							logger.info("-------------------------批量删除成功");
						}else{
							logger.info("-------------------------批量删除失败");
						}
	      				}else{
	      					logger.info("+++++++++++++++++++IMEI为空");
	      				}
						}
									
				} else if (cmd.equals(AlphaAdultConfig.T_U_FIND)) {
					subData.setCmd(AlphaAdultConfig.findDevice);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_SETGROUP)) {//推送联系人列表
					String imei=reqJsonData.getSerieNo();
					subData.setCmd(AlphaAdultConfig.setGroups);
					subData.setImei(imei);
					//String msg=reqJsonData.getMsg();
					String[] receive = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "#");//name#type

					DeviceActive da=new DeviceActive();
                    da.setUid(Integer.valueOf(uid));
					da.setDid(Integer.valueOf(did));
					da.setDeviceImei(imei);
					List<DeviceActive> groupList =mDeviceActiveService.findDeviceGroups(da);
					List<DeviceInfoAlp> dis = new ArrayList<DeviceInfoAlp>();
					DeviceInfoAlp fa = new DeviceInfoAlp();
					logger.info("groupSize前");
					if(groupList.size()<=0){
						logger.info("增加group");
						DeviceActive dav=new DeviceActive();
						dav.setDeviceImei(imei);
						dav.setName(receive[0]);
						dav.setType(receive[1]);
						dav.setUid(Integer.valueOf(uid));
						dav.setDid(Integer.valueOf(did));
						dav.setUpdateTime(new Date());
						int a=mDeviceActiveService.insertDeviceGroups(dav);
					}
					fa.setId(uid);
					fa.setName(receive[0]);
					dis.add(fa);
					subData.setGroup(dis);
					sub = JSON.toJSONString(subData);
				} else if (cmd.equals(AlphaAdultConfig.T_U_MONITOR)) {
					subData.setCmd(AlphaAdultConfig.monitor);
					subData.setPhoneNum(reqJsonData.getMsg()); // msg存放电话号码
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_SHUTDOWN)) {
					bolClose = true;
					subData.setCmd(AlphaAdultConfig.shutdown);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_REBOOT)) {
					bolClose = true;
					subData.setCmd(AlphaAdultConfig.reboot);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_RESET)) {
					subData.setCmd(AlphaAdultConfig.reset);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_LOCATE)) {
					subData.setCmd(AlphaAdultConfig.reqLocate);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_TEXT)) { // 推送文本消息
					subData.setCmd(AlphaAdultConfig.texts);
					List<TextInfoAlp> textList = new ArrayList<TextInfoAlp>();
					TextInfoAlp mTextInfoAlp = new TextInfoAlp();
					String msg = reqJsonData.getMsg();

					String[] receive = CalculatorUtils.getSplitRegx(msg, "#");
					mTextInfoAlp.setId(receive[0]); // 暂时不存数据库
					mTextInfoAlp.setSender(receive[1]);
					mTextInfoAlp.setTime(receive[2]);
					mTextInfoAlp.setText(receive[3]);
					textList.add(mTextInfoAlp);

					subData.setTexts(textList);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_ALARMS)) { // 推送闹钟:APK发指令后台给
					subData.setCmd(AlphaAdultConfig.alarms);//若闹钟有更改则APP推
					Clock clock = new Clock();
					clock.setDid(Integer.parseInt(did));
					String[] receive = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "#");

					/*
					 * ClockInfoAlp mClockInfoAlp = new ClockInfoAlp();
					 * mClockInfoAlp.setType(Integer.valueOf(receive[0]));
					 * mClockInfoAlp.setName(receive[2]);
					 * mClockInfoAlp.setRepeat(receive[3]);
					 * mClockInfoAlp.setTime(receive[4]);
					 * mClockInfoAlp.setVibrate(Integer.valueOf(receive[5]));
					 * mClockInfoAlp.setRing(Integer.valueOf(receive[6]));
					 * mClockInfoAlp.setRingId(Integer.valueOf(receive[7]));
					 * mClockInfoAlp.setVol(Integer.valueOf(receive[8]));
					 * mClockInfoAlp.setIsOn(Integer.valueOf(receive[9]));
					 */

					clock.setType(Integer.valueOf(receive[0]));
					clock.setName(receive[2]);
					clock.setRepeatTimes(receive[3]);
					clock.setClock(receive[4]);
					clock.setVibrate(receive[5]);
					clock.setRing(receive[6]);
					clock.setRingId(receive[7]);
					clock.setVol(receive[8]);
					clock.setStatu(receive[9]);

					int newId = 0;
					if (receive[1] != null && !"0".equals(receive[1])) {
						newId = Integer.valueOf(receive[1]);
						clock.setId(newId);
						mClockService.update(clock);
					} else {
						mClockService.insert(clock);
						newId = clock.getId();
					}
					//clock.setId(0); // 查询只查设备关联的
				//	clock.setId(Integer.valueOf(receive[1]));
					Clock clockX = new Clock();
					clockX.setDid(Integer.valueOf(reqJsonData.getDeviceId()));
				//	clock.setDid(Integer.valueOf(receive[0]));
					List<Clock> clocks = mClockService.find(clockX);
					List<ClockInfoAlp> alarms = new ArrayList<ClockInfoAlp>();
					int length = clocks.size();
					for (int i = 0; i < length; i++) {//推送多个
						Clock c = clocks.get(i);
						ClockInfoAlp ci = new ClockInfoAlp();
						ci.setId(String.valueOf(c.getId()));
						ci.setType(c.getType());
						ci.setRepeat(c.getRepeatTimes());
						ci.setName(c.getName());
						ci.setTime(c.getClock());
						ci.setVibrate(Integer.parseInt(c.getVibrate()));
						ci.setRing(Integer.parseInt(c.getRing()));
						ci.setRingId(Integer.parseInt(c.getRing()));
						ci.setVol(Integer.parseInt(c.getVol()));
						ci.setIsOn(Integer.valueOf(c.getStatu()));

						alarms.add(ci);
					}
					if (newId != 0) {
						respJsonData.setMsg(String.valueOf(newId));
					}
					subData.setAlarms(alarms);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_DELALARMS)) {
					subData.setCmd(AlphaAdultConfig.alarms);
					String clockId = reqJsonData.getMsg();

					mClockService.deleteById(Integer.valueOf(clockId));
					Clock clock = new Clock();
					clock.setDid(Integer.parseInt(did));
					List<Clock> clocks = mClockService.find(clock);
					List<ClockInfoAlp> alarms = new ArrayList<ClockInfoAlp>();
					int length = clocks.size();
					for (int i = 0; i < length; i++) {
						Clock c = clocks.get(i);
						ClockInfoAlp ci = new ClockInfoAlp();
						ci.setId(String.valueOf(c.getId()));
						ci.setType(c.getType());
						ci.setRepeat(c.getRepeatTimes());
						ci.setName(c.getName());
						ci.setTime(c.getClock());
						ci.setVibrate(Integer.parseInt(c.getVibrate()));
						ci.setRing(Integer.parseInt(c.getRing()));
						ci.setRingId(Integer.parseInt(c.getRing()));
						ci.setVol(Integer.parseInt(c.getVol()));
						ci.setIsOn(Integer.valueOf(c.getStatu()));

						alarms.add(ci);
					}

					subData.setAlarms(alarms);
					sub = JSON.toJSONString(subData);
				} else if (cmd.equals(AlphaAdultConfig.T_U_CONTACTS)) { // 推送联系人列表:APK发指令后台给
					ContactInfoAlp contact = new ContactInfoAlp();
					List<ContactInfoAlp> contacts = new ArrayList<ContactInfoAlp>();
					deviceActive.setId(Integer.parseInt(did));
					List<DeviceActive> mList = mDeviceActiveService.find(deviceActive);
					
					contact.setDevId(mList.get(0).getDeviceImei());
					contact.setIcon(0);
					contact.setId(mList.get(0).getUserId());
					contact.setType(1);
					// temp.setMobile("15019420562");
					contact.setName("看不到");
					
					subData.setCmd(AlphaAdultConfig.contacts);
					subData.setContacts(contacts);
					sub = JSON.toJSONString(subData);
				} else if (cmd.equals(AlphaAdultConfig.T_U_RREQADDCONTACTS)) { // 响应"添加联系人请求"
					String devId = "";
					if (session.containsAttribute("id")) {
						devId = (String) session.getAttribute("id");
					}
					ContactInfoAlp contact = new ContactInfoAlp();
					contact.setDevId(devId);
					List<ContactInfoAlp> contacts = null;
					subData.setCmd(AlphaAdultConfig.rReqAddContacts);
					subData.setContacts(contacts);
					sub = JSON.toJSONString(subData);

				} /*else if (cmd.equals(AlphaAdultConfig.T_U_SETFAMILYNUMBER)) { // 推送亲情号码:APK发指令后台给
					logger.info("推送亲情");
					subData.setCmd(AlphaAdultConfig.setFamilyNumber);
					String[] receiveAll = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "@");
					logger.info("+++++++++++++++++++++++++++++"+receiveAll.length);
					for(int z=0;z<receiveAll.length;z++){
					String[] receive= CalculatorUtils.getSplitRegx(
							receiveAll[z], "#");
				logger.info("+------------------------------"+receiveAll[z]);

					deviceActive.setId(Integer.valueOf(did));
					deviceActive.setBelongProject(b_g);
					deviceActive.setDeviceDisable("1"); // 保证激活状态
					List<DeviceActive> deviceList = mDeviceActiveService
							.find(deviceActive);

					String deviceImei = "0";
					if (deviceList.size() > 0) {
						deviceImei = deviceList.get(0).getDeviceImei();
					}
					if (!deviceImei.equals("0")) {
						Family family = new Family();

						family.setSerieNo(deviceImei);
						family.setPhoneNumber(receive[0]);
						family.setIcon(Integer.valueOf(receive[1]));
						logger.info("啊啊啊啊啊啊啊啊啊啊啊啊啊啊receive[1]"+receive[1]);
						family.setNickName(receive[2]);
						family.setRelativeType("0");
						family.setBelongProject(b_g);
						family.setStatus("1");
						family.setAddTime(new Date());
						family.setUserId(uid);
						if (receive[3] != null && !"0".equals(receive[3])) {
							family.setId(Integer.valueOf(receive[3]));
							mFamilyService.update(family);
						} else {
							mFamilyService.insert(family);
						}
						family.setId(0);
						List<Family> familys = mFamilyService.find(family);
						List<FamilyInfoAlpha> familyInfos = new ArrayList<FamilyInfoAlpha>();
						for (int i = 0; i < familys.size(); i++) {
							FamilyInfoAlpha fa = new FamilyInfoAlpha();
							Family f = familys.get(i);
							fa.setName(f.getNickName());
							fa.setIcon(f.getIcon());
							fa.setNumber(f.getPhoneNumber());
							familyInfos.add(fa);
						}
						logger.info("--------------走到这里没啊");
                        logger.info("-------------------------"+familyInfos);
						subData.setFamilys(familyInfos);
						sub = JSON.toJSONString(subData);
					}
					}
				}*/else if (cmd.equals(AlphaAdultConfig.T_U_SETSOSNUMBER)) { // 推送sos号码:APK发指令后台给
					logger.info("推送sos");
					subData.setCmd(AlphaAdultConfig.setSosNumber);
					String[] receiveAll = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "@");
					logger.info("+++++++++++++++++++++++++++++"+receiveAll.length);
					for(int z=0;z<receiveAll.length;z++){
					String[] receive= CalculatorUtils.getSplitRegx(
							receiveAll[z], "#");
				logger.info("+------------------------------"+receiveAll[z]);

					deviceActive.setId(Integer.valueOf(did));
					deviceActive.setBelongProject(b_g);
					deviceActive.setDeviceDisable("1"); // 保证激活状态
					List<DeviceActive> deviceList = mDeviceActiveService
							.find(deviceActive);

					String deviceImei = "0";
					if (deviceList.size() > 0) {
						deviceImei = deviceList.get(0).getDeviceImei();
					}
					if (!deviceImei.equals("0")) {
						Family family = new Family();

						family.setSerieNo(deviceImei);
						family.setPhoneNumber(receive[0]);
						family.setIcon(Integer.valueOf(receive[1]));
						logger.info("啊啊啊啊啊啊啊啊啊啊啊啊啊啊receive[1]"+receive[1]);
						family.setNickName(receive[2]);
						family.setRelativeType("2");//sos类型
						family.setBelongProject(b_g);
						family.setStatus("1");
						family.setAddTime(new Date());
						family.setUserId(uid);
						if (receive[3] != null && !"0".equals(receive[3])) {
							family.setId(Integer.valueOf(receive[3]));
							mFamilyService.update(family);
						} else {
							mFamilyService.insert(family);
						}
						family.setId(0);
						List<Family> familys = mFamilyService.find(family);
						List<FamilyInfoAlpha> familyInfos = new ArrayList<FamilyInfoAlpha>();
						for (int i = 0; i < familys.size(); i++) {
							FamilyInfoAlpha fa = new FamilyInfoAlpha();
							Family f = familys.get(i);
							fa.setName(f.getNickName());
							fa.setIcon(f.getIcon());
							fa.setNumber(f.getPhoneNumber());
							familyInfos.add(fa);
						}
						logger.info("--------------推送sos号码");
                        logger.info("-------------------------"+familyInfos);
                        subData.setSoss(familyInfos);
                        subData.setSosMsg("救救我");
						sub = JSON.toJSONString(subData);
					}
					}
				}else if (cmd.equals(AlphaAdultConfig.T_U_SETDISTURB)) { //推送防打扰
					 // 请求推送防打扰
					String imei = reqJsonData.getSerieNo();
					List<ClockInfoAlp> dis = new ArrayList<ClockInfoAlp>();
				/*	if (session.containsAttribute("id")) {
						deviceActive = (DeviceActive) session.getAttribute("device_info");
						logger.info("AlphaAdultConfig.reqFamilyNumber的sos号码对应的设备imei"+deviceActive.getDeviceImei());
					}*/
					if(deviceActive != null){
						Clock clock = new Clock();
						clock.setSerieNo(imei);
						List<Clock> clocks =mClockService.findDisturb(clock); 
						for (int i = 0; i < clocks.size(); i++) {
							Clock f = clocks.get(i);
							ClockInfoAlp fa = new ClockInfoAlp();
							fa.setId(f.getId()+"");
							fa.setRepeat(f.getRepeat());
							fa.setTime(f.getTime());
							dis.add(fa);
						}
					}
					
					logger.info("推送防打扰");
					subData.setCmd(AlphaAdultConfig.setDisturb);
					subData.setDisturb(dis);
					sub = JSON.toJSONString(subData);
				} else if (cmd.equals(AlphaAdultConfig.T_U_SETFRIENDNUMBER)) { // 推送好友号码:APK发指令后台给
					subData.setCmd(AlphaAdultConfig.setFriendNumber);
					String[] receive = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "#");

					deviceActive.setId(Integer.valueOf(did));
					deviceActive.setBelongProject(b_g);
					deviceActive.setDeviceDisable("1"); // 保证激活状态
					List<DeviceActive> deviceList = mDeviceActiveService
							.find(deviceActive);

					String deviceImei = "0";
					if (deviceList.size() > 0) {
						deviceImei = deviceList.get(0).getDeviceImei();
					}
					if (!deviceImei.equals("0")) {
						Family family = new Family();

						family.setSerieNo(deviceImei);
						family.setPhoneNumber(receive[0]);
						family.setIcon(Integer.valueOf(receive[1]));
						family.setNickName(receive[2]);
						family.setRelativeType("1");
						family.setBelongProject(b_g);
						family.setStatus("1");
						family.setAddTime(new Date());
						family.setUserId(uid);
						if (receive[3] != null && !"0".equals(receive[3])) {
							family.setId(Integer.valueOf(receive[3]));
							mFamilyService.update(family);
						} else {
							mFamilyService.insert(family);
						}
						family.setId(0);
						List<Family> familys = mFamilyService.find(family);
						List<FamilyInfoAlpha> familyInfos = new ArrayList<FamilyInfoAlpha>();
						int length = familys.size();
						for (int i = 0; i < length; i++) {
							FamilyInfoAlpha fa = new FamilyInfoAlpha();
							Family f = familys.get(i);
							fa.setName(f.getNickName());
							fa.setIcon(f.getIcon());
							fa.setNumber(f.getPhoneNumber());
							fa.setIsBinding(0);
							familyInfos.add(fa);
						}

						subData.setFamilys(familyInfos);
						sub = JSON.toJSONString(subData);
					}

				} /*else if (cmd.equals(AlphaAdultConfig.T_U_DELNUMBER)) {
					String[] receive = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "#");
					Family mFamily = new Family();
					mFamily.setId(Integer.valueOf(receive[0]));
					mFamilyService.delete(mFamily);

					mFamily.setId(0);
					mFamily.setRelativeType(receive[1]);
					mFamily.setSerieNo(receive[2]);

					if ("0".equals(receive[1])) {
						subData.setCmd(AlphaAdultConfig.setFamilyNumber);
					} else {
						subData.setCmd(AlphaAdultConfig.setFriendNumber);
					}
					List<Family> familys = mFamilyService.find(mFamily);
					List<FamilyInfoAlpha> familyInfos = new ArrayList<FamilyInfoAlpha>();
					for (int i = 0; i < familys.size(); i++) {
						FamilyInfoAlpha fa = new FamilyInfoAlpha();
						Family f = familys.get(i);
						fa.setName(f.getNickName());
						fa.setIcon(f.getIcon());
						fa.setNumber(f.getPhoneNumber());
						familyInfos.add(fa);
					}

					subData.setFamilys(familyInfos);
					sub = JSON.toJSONString(subData);
				}*/ else if (cmd.equals(AlphaAdultConfig.T_U_FWUPGRADE)) {
					subData.setCmd(AlphaAdultConfig.fwUpgrade);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_MEDAL)) {
					subData.setCmd(AlphaAdultConfig.Medal);
					subData.setNumber("1");
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_REMINDER)) {// 宝宝生活提醒
					subData.setCmd(AlphaAdultConfig.Reminder);
					subData.setIndex("0");
					subData.setRepeat("1110011");
					subData.setTime("1830");
					subData.setIson(1);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_SETSLEEP)) {// 睡眠监测
					subData.setCmd(AlphaAdultConfig.SetSleep);
					subData.setIson(1);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_SETSIT)) {// 久坐提醒啊
					subData.setCmd(AlphaAdultConfig.SetSit);
					String msg = reqJsonData.getMsg();
					String[] receive = CalculatorUtils.getSplitRegx(msg, "#");
					subData.setIson(Integer.valueOf(receive[0]));
					subData.setDura(Integer.valueOf(receive[1]));
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_REQPEDOMETERDATA)) {
					// 请求计步数据
					subData.setCmd(AlphaAdultConfig.reqPedometerData);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_GETDEVINFO)) {
					// 得到终端设备相关信息
					subData.setCmd(AlphaAdultConfig.getDevInfo);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_CHECKCHARGE)) {
					// 下发查询话费请求
					subData.setCmd(AlphaAdultConfig.CheckCharge);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_SETCHECKSMS)) {
					// 下发查询话费指令
					subData.setCmd(AlphaAdultConfig.SetCheckSms);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_BUSCHECKCHARGE)) {
						// 下发查询公交余额指令
						subData.setCmd(AlphaAdultConfig.BusCheckCharge);
						sub = JSON.toJSONString(subData);

			  } else if (cmd.equals(AlphaAdultConfig.T_U_WEATHER)) {
					// 天气
					subData.setCmd(AlphaAdultConfig.Weather);
					subData.setTemperature("35");
					subData.setStatus(0);

				} else if (cmd.equals(AlphaAdultConfig.T_U_REQLOCATE)) { // apk上传定位请求
					LocationInfo location = new LocationInfo();
					location.setSerieNo(did);
					location.setBelongProject(b_g);
					subData.setCmd(AlphaAdultConfig.reqLocate);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_SETCHILDINFO)) { // 设置孩子信息
					String msg = reqJsonData.getMsg();
					String[] msgs = msg.split("#");

					subData.setCmd(AlphaAdultConfig.setChildInfo);
					subData.setSex(Integer.valueOf(msgs[0]));
					subData.setBirthday(msgs[1]);
					sub = JSON.toJSONString(subData);

				} else if (cmd.equals(AlphaAdultConfig.T_U_VOICE_TEST)) {
					String path = Constant.MEDIA_PATH + "test"
							+ System.getProperty("file.separator") + "test.amr";
					byte[] voiceData = Constant.getContent(path);
					subData.setCmd(AlphaAdultConfig.pushVoice);
					subData.setId(dataFormat.format(new Date()));
					subData.setTime(dataFormat.format(new Date()));
					subData.setSender("device");
					subData.setSenderId("2");
					subData.setDuration(4);
					subData.setLen(voiceData.length);
					subData.setFormat("amr");

					mDataBean.setVoice(voiceData);
					sub = JSON.toJSONString(subData);
				} else if (cmd.equals(AlphaAdultConfig.T_U_VOICE)) {
					String[] arr = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "#");
					
					subData.setCmd(AlphaAdultConfig.pushVoice);

					int id = Integer.valueOf(arr[0]);
					int sid = Integer.valueOf(arr[1]);
					String voiceContent = arr[2];
					String length = arr[3];
					String type = arr[4]; // 预留字段,0表示单聊,1表示群聊
					if (id != 0) {
						DeviceActive temp = mDeviceActiveService.findById(id);
						if (temp != null && "1".equals(temp.getDeviceDisable())) {
							String path = Constant.MEDIA_PATH + sid
									+ System.getProperty("file.separator");
							//路径
             				byte[] voiceData = Base64.decodeBase64(voiceContent);
							
							String fileName = Constant.getUniqueCode(String
									.valueOf(sid)) + ".amr";
							//文件夹的名字
							Constant.createFileContent(path, fileName,voiceData);

							final Media media = new Media();
							media.setFromId(String.valueOf(sid)); // 来自哪个用户
							media.setToId(String.valueOf(id)); // 发给哪个设备
							media.setMsgContent(path + fileName);
							media.setSendType("0"); // 未发送
							media.setSendTime(new Date());
							media.setTimeLength(length);
							media.setBelongProject(b_g);
							mMediaService.insert(media);
							
							String time = dataFormat.format(new Date());
							//subData.setId(time+"-"+String.valueOf(media.getId()));
							subData.setId(time+String.valueOf(media.getId()));
							subData.setTime(time);
							subData.setSender("device");
							subData.setSenderId(uid);
							subData.setDuration(Integer.valueOf(length));
							subData.setLen(voiceData.length);
							subData.setFormat("amr");
							
							mDataBean.setVoice(voiceData);
							sub = JSON.toJSONString(subData);
						}
					}
				}else if(cmd.equals(AlphaAdultConfig.T_U_SETDEVICEINFO)){
					subData.setCmd(AlphaAdultConfig.setMyselfInfo);
					Setting seti=new Setting();
				//	Clock clock = new Clock();
				//	clock.setDid(Integer.parseInt(did));
					//昵称#性别#生日#身高#体重#步长#电话号码#IMEI
					String[] receive = CalculatorUtils.getSplitRegx(
							reqJsonData.getMsg(), "#");
					seti.setSerieNo(receive[7]);
					seti.setBelongProject(b_g);
					if(mSettingService.find(seti).size()>0){
						seti.setSerieNo(receive[7]);
						seti.setBelongProject(b_g);
						seti.setNickName(receive[0]);
						seti.setSex(receive[1]);
						seti.setBirthday(receive[2]);
						seti.setHigh(receive[3]);
						seti.setWeight(receive[4]);
						seti.setStepd(receive[5]);
						seti.setPhone(receive[6]);
                    int gaibian=mSettingService.update(seti);
					}else{
						seti.setSerieNo(receive[7]);
						seti.setBelongProject(b_g);
						seti.setNickName(receive[0]);
						seti.setSex(receive[1]);
						seti.setBirthday(receive[2]);
						seti.setHigh(receive[3]);
						seti.setWeight(receive[4]);
						seti.setStepd(receive[5]);
						seti.setPhone(receive[6]);
					int zengjia=mSettingService.insert(seti);	
					}

					/*
					 * ClockInfoAlp mClockInfoAlp = new ClockInfoAlp();
					 * mClockInfoAlp.setType(Integer.valueOf(receive[0]));
					 * mClockInfoAlp.setName(receive[2]);
					 * mClockInfoAlp.setRepeat(receive[3]);
					 * mClockInfoAlp.setTime(receive[4]);
					 * mClockInfoAlp.setVibrate(Integer.valueOf(receive[5]));
					 * mClockInfoAlp.setRing(Integer.valueOf(receive[6]));
					 * mClockInfoAlp.setRingId(Integer.valueOf(receive[7]));
					 * mClockInfoAlp.setVol(Integer.valueOf(receive[8]));
					 * mClockInfoAlp.setIsOn(Integer.valueOf(receive[9]));
					 */
					subData.setImei(receive[7]);
					subData.setHigh(receive[3]);
					subData.setWeight(receive[4]);
					subData.setSex(Integer.valueOf(receive[1]));
					sub = JSON.toJSONString(subData);
				}else{
					
				}
				if (bolSub) {
					backSb.append("ALPHA").append("|");
					final IoSession tempSession = mClientSessionManager
							.getSessionId("d_" + did);
					int temp = sub.length();
					int lenTotal = String.valueOf(temp).length() + temp + subData.getCmd().length() + 27 ;
					for (int i = 0; i < 8; i++) {
						if (i == 0) {
							if (mDataBean.getVoice() != null
									&& mDataBean.getVoice().length > 0) {
								lenTotal += mDataBean.getVoice().length;
							}
							backSb.append(lenTotal);
						} else if (i == 1) {
							backSb.append(temp);
						} else if (i == 2) {
							if (mDataBean.getVoice() != null
									&& mDataBean.getVoice().length > 0) {
								backSb.append(mDataBean.getVoice().length);
							} else {
								backSb.append(0);
							}
						} else if (i == 3) {
							backSb.append("BSW1");
						} else if (i == 4) {
							backSb.append("20");
						} else if (i == 5) {
							backSb.append(dataFormat.format(new Date()));
						} else if (i == 6) {
							backSb.append(subData.getCmd());
							backSb.append("|");
						} else if (i == 7) {
							backSb.append(sub);
						}
						backSb.append("|");
					}
					if (tempSession != null) {
						mDataBean.setContent(backSb.toString());
						WriteFuture writeFuture = tempSession.write(mDataBean);
						writeFuture
								.addListener(new IoFutureListener<IoFuture>() {

									@Override
									public void operationComplete(
											IoFuture future) {
										// TODO Auto-generated method stub
										if (((WriteFuture) future).isWritten()) { // 发送成功
											respJsonData.setResult("1");
										} else {
											respJsonData.setResult("0");
										}
									}
								});
						if(respJsonData.getResult() != null && respJsonData.getResult().equals("1")){
							deviceActive.setId(Integer.valueOf(did));
							if(bolClose){
								tempSession.close(true);
								deviceActive.setDeviceStatus("0");   //关机或者重启都是离线状态
							}else{
								deviceActive.setDeviceStatus("1");   //关机或者重启都是离线状态
							}																					
							mDeviceActiveService.update(deviceActive);
						}
					} else {
						respJsonData.setResult("0");
					}
				}
			}catch (JsonParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			} catch (NullPointerException nullE) {
				logger.error("异常报错", nullE);
				respJsonData.setResult("-4");
			} catch (JSONException jsonE) {
				logger.error("异常报错", jsonE);
				respJsonData.setResult("-2");
			} catch (DataAccessException dataE) {
				logger.error("异常报错", dataE);
				respJsonData.setResult("-1");
			} catch (ArrayIndexOutOfBoundsException arrE){
				logger.error("异常报错", arrE);
				respJsonData.setResult("-5"); // 数组越界
			}finally {
				resp = JSON.toJSONString(respJsonData);
				session.write(resp.toString());
			}
		}
	}
}
