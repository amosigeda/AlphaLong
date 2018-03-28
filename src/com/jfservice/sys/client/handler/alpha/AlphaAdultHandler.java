package com.jfservice.sys.client.handler.alpha;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jfservice.common.bean.alpha.other.ClockInfoAlp;
import com.jfservice.common.bean.alpha.other.ContactInfoAlp;
import com.jfservice.common.bean.alpha.other.DeviceCfgInfoAlp;
import com.jfservice.common.bean.alpha.other.FamilyInfoAlpha;
import com.jfservice.common.bean.alpha.other.FriendInfoAlpha;
import com.jfservice.common.bean.alpha.other.LbsInfoAlp;
import com.jfservice.common.bean.alpha.other.LbsInfoAlp.Cell;
import com.jfservice.common.bean.alpha.other.LocationInfoAlp;
import com.jfservice.common.bean.alpha.other.SceneModeInfoAlp;
import com.jfservice.common.bean.alpha.request.ReqJsonData;
import com.jfservice.common.bean.alpha.response.RespJsonData;
import com.jfservice.common.bean.alpha.subcri.SubcriJsonData;
import com.jfservice.common.http.HttpRequest;
import com.jfservice.common.lang.CalculatorUtils;
import com.jfservice.common.lang.Constant;
import com.jfservice.sys.client.handler.impl.ClientMessageEventImpl;
import com.jfservice.sys.client.handler.weike.ImmediationHandler;
import com.jfservice.sys.client.manager.ClientSessionManager;
import com.jfservice.sys.clock.model.Clock;
import com.jfservice.sys.clock.service.ClockService;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;
import com.jfservice.sys.deviceactiveinfo.service.DeviceActiveService;
import com.jfservice.sys.family.model.Family;
import com.jfservice.sys.family.service.FamilyService;
import com.jfservice.sys.location.model.LocationInfo;
import com.jfservice.sys.location.service.LocationService;
import com.jfservice.sys.media.service.MediaService;
import com.jfservice.sys.setting.model.Setting;
import com.jfservice.sys.setting.service.SettingService;
import com.jfservice.common.bean.alpha.other.CountStepAlp;
import com.jfservice.common.bean.alpha.other.DeviceInfoAlp;

public class AlphaAdultHandler extends ClientMessageEventImpl {

	private Logger logger = Logger.getLogger(ImmediationHandler.class);

	@Autowired
	private ApplicationContext mApplicationContext;

	@Autowired
	private MediaService mMediaService;

	@Autowired
	private DeviceActiveService mDeviceActiveService;

	@Autowired
	private LocationService mLocationInfoService;

	@Autowired
	private ClockService mClockService;

	@Autowired
	private FamilyService mFamilyService;

	@Autowired
	private SettingService mSettingService;

	private ClientSessionManager mClientSessionManager; // 客户端session的保存

	@Override
	public void handler(Object message, IoSession session) {
		// TODO Auto-generated method stub
		mClientSessionManager = (ClientSessionManager) mApplicationContext
				.getBean("clientSessionManager");
		// 响应的数据
		String resp = "";
		boolean isResponse = true; // 是否响应
		boolean isSub = false; // 是否推送给apk
		RespJsonData respJsonData = new RespJsonData();
	/*SimpleDateFormat dataFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.sss");*/
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 数据库bean文件操作
		DeviceActive deviceActive = new DeviceActive();
		LocationInfo info = new LocationInfo(); // 定位数据保存

		String sub = "";
		String userId = "0";
		String devId = "0";
		SubcriJsonData subData = new SubcriJsonData();
		System.out.println("阿尔法青少年设备端");
		if (message != null && session != null) {
			StringBuffer backSb = new StringBuffer();
			DataBean test = (DataBean) message;
			//	byte[] a = test.getVoice();
			if(test.getContent().contains("cmd")&&test.getContent().contains("ALPHA")&&test.getContent().contains("||")){
				 String[] 	 receive = CalculatorUtils.getSplitRegx(test.getContent(),
						"\\|");
			String	 reqString = CalculatorUtils.getSubStr(test.getContent(),
						"|", 2).replace("|", "");
				logger.info("第二个|");
			
		
			/*
			 * String reqString =
			 * CalculatorUtils.getSubStr(test.getContent(),"||", 1).replace("|",
			 * ""); if(reqString.contains("#!AMR")){
			 * reqString=reqString.substring(reqString.indexOf("{"),
			 * reqString.indexOf("#")); }
			 */

			logger.info("reqString---------------------" + reqString);
			// ReqJsonData reqJsonData =
			// JSON.parseObject(reqString,ReqJsonData.class);

			Gson gson = new Gson();
			logger.info("gson后");
			logger.info("反序列化前");
			ReqJsonData reqJsonData = gson.fromJson(reqString,
					ReqJsonData.class);
			logger.info("反序列化后");
			/*
			 * try { logger.info("mapper前"); ObjectMapper mapper=new
			 * ObjectMapper(); logger.info("mapper后-----------"); ReqJsonData
			 * reqJsonData = mapper.readValue(reqString, ReqJsonData.class);
			 */
			logger.info("reqJsonData-----------------------lalalalala"
					+ reqJsonData);
			String cmd = reqJsonData.getCmd();
			logger.info("CMDCMD--------------------------------------" + cmd);
			if (cmd.equals(AlphaAdultConfig.reqConnect)) {
				String deviceId = reqJsonData.getDevId();
				deviceActive.setDeviceImei(deviceId);
				deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					int id = deviceList.get(0).getId();
					String did = "d_" + id;
					session.setAttributeIfAbsent("id", did); // 每一个通道的id
					session.setAttributeIfAbsent("device_info",
							deviceList.get(0)); // 记录设备信息

					if (mClientSessionManager.getSessionId(did) != null) {
						mClientSessionManager.removeSessionId(did);
					}
					mClientSessionManager.addSessionId(did, session);

					respJsonData.setRet(0);
					respJsonData.setHaveUnread(0);
				} else {
					respJsonData.setRet(-1);
					respJsonData.setHaveUnread(0);
				}
				respJsonData.setCmd(AlphaAdultConfig.rReqConnect);
				respJsonData.setMsg("success!");
				respJsonData.setDevTime(dataFormat.format(new Date()));
			} else if (cmd.equals(AlphaAdultConfig.heartbeat)) {
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				logger.info("保存的用户id为=" + devId);
				if (devId != null
						&& mClientSessionManager.getSessionId(devId) != null) {
					logger.info("心跳数据的通道为=" + session);
					mClientSessionManager.removeSessionId(devId);
					deviceActive.setId(Integer.valueOf(devId.split("_")[1]));
					deviceActive.setDeviceStatus("1");
					mDeviceActiveService.update(deviceActive);

					mClientSessionManager.addSessionId(devId, session);
				}

				respJsonData.setCmd(AlphaAdultConfig.rHeartbeat);
			} else if (cmd.equals(AlphaAdultConfig.getDevCfg)) {
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
				deviceCfg.setHbRate(60);
				deviceCfg.setSafePower(15);
				deviceCfg.setIsAutoAnswer(0);
				deviceCfg.setIsCtrLcd(1);

				sosNums.add("13823384905");
				sosNums.add("15889462464");
				deviceCfg.setSosNum(sosNums);

				dataCfg.setHost("www.gpscarecare.com");
				dataCfg.setPort(1306);

				cmdCfg.setHost("www.gpscarecare.com");
				cmdCfg.setPort(1306);

				deviceCfg.setDataConnCfg(dataCfg);
				deviceCfg.setCmdConnCfg(cmdCfg);

				respJsonData.setCmd(AlphaAdultConfig.devCfg);
				respJsonData.setDevCfg(deviceCfg);
			} else if (cmd.equals(AlphaAdultConfig.rDevCfg)) {//rHeartbeat
				isResponse = false;
			}else if (cmd.equals(AlphaAdultConfig.rHeartbeat)) {//rHeartbeat
				isResponse = false;
			}else if (cmd.equals(AlphaAdultConfig.rReqConnect)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqAlarms)) {// 请求推送闹钟
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				if (devId.equals("")) {
					devId = "d_91";
				}
				Clock clock = new Clock();
				clock.setDid(Integer.parseInt(devId.split("_")[1]));
				List<Clock> clocks = mClockService.find(clock);
				List<ClockInfoAlp> alarms = new ArrayList<ClockInfoAlp>();
				for (int i = 0; i < clocks.size(); i++) {
					Clock ck = clocks.get(i);
					ClockInfoAlp clockInfoAlp = new ClockInfoAlp();
					clockInfoAlp.setId(ck.getAlarmId());
					clockInfoAlp.setType(ck.getType());
					clockInfoAlp.setRepeat(ck.getRepeatTimes());
					clockInfoAlp.setName(ck.getName());
					clockInfoAlp.setTime(ck.getClock());
					clockInfoAlp.setVibrate(Integer.parseInt(ck.getVibrate()));
					clockInfoAlp.setRing(Integer.parseInt(ck.getRing()));
					clockInfoAlp.setRingId(Integer.parseInt(ck.getRing()));
					clockInfoAlp.setVol(Integer.parseInt(ck.getVol()));
					clockInfoAlp.setIsOn(Integer.valueOf(ck.getStatu()));
					alarms.add(clockInfoAlp);
				}

				respJsonData.setCmd(AlphaAdultConfig.alarms);
				respJsonData.setAlarms(alarms);
			} else if (cmd.equals(AlphaAdultConfig.rAlarms)) { // 响应“推送闹钟”
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqContacts)) { // 请求推送联系人列表
				List<ContactInfoAlp> contacts = new ArrayList<ContactInfoAlp>();
				ContactInfoAlp temp = new ContactInfoAlp();
				if (session.containsAttribute("id")) {
					logger.info("有信息");
					if (session.containsAttribute("id")) {
						devId = (String) session.getAttribute("id");
					}
					deviceActive.setId(Integer.parseInt(devId.split("_")[1]));
					List<DeviceActive> mList = mDeviceActiveService
							.find(deviceActive);

					temp.setDevId(mList.get(0).getDeviceImei());
					temp.setIcon(0);
					temp.setId(mList.get(0).getUserId());
					temp.setType(1);
					// temp.setMobile("15019420562");
					temp.setName("看不到");
				} else {
					temp.setDevId("868219000003134");
					temp.setIcon(0);
					temp.setId("20141118142132");
					temp.setType(1);
					// temp.setMobile("15019420562");
					temp.setName("看不到");
				}

				contacts.add(temp);
				respJsonData.setCmd(AlphaAdultConfig.contacts);
				respJsonData.setContacts(contacts);

			} else if (cmd.equals(AlphaAdultConfig.reqAddContacts)) { // 上传添加联系人请求
				String id = "";
				String type = "";

				id = reqJsonData.getId();
				if (reqJsonData.getType() != null
						&& !"".equals(reqJsonData.getType())) {
					type = String.valueOf(reqJsonData.getType());
				}

				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				respJsonData.setCmd(AlphaAdultConfig.rReqAddContacts);
				respJsonData.setRet(0);// 成功
				respJsonData.setRet(-2);// ID错误
				respJsonData.setRet(-3);// 联系人已经达到上线
				respJsonData.setRet(-4);// 联系人已经存在
			} else if (cmd.equals(AlphaAdultConfig.uploadSos)) { // 上传SOS信息
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				List<LocationInfoAlp> lctDatas = reqJsonData.getLctData();
				for (int i = 0; i < lctDatas.size(); i++) {
					LocationInfoAlp lct = lctDatas.get(i);
					LocationInfo l = new LocationInfo();
					l.setSerieNo(devId);
				}
				isResponse = false;
				;
			} else if (cmd.equals(AlphaAdultConfig.uploadHeartRate)) { // 上传心率
				String imei = reqJsonData.getSerieNo();
				if (imei != null) {
					//DeviceActive vo = new DeviceActive();
					List<DeviceActive> lctDatas = reqJsonData.getDatas();
					for (int i = 0; i < lctDatas.size(); i++) {
						DeviceActive lct = lctDatas.get(i);
						String heartRate = lct.getHeartRate();
						String time = lct.getTime();
						DeviceActive l = new DeviceActive();
						l.setDeviceImei(imei);
						l.setHeartRate(heartRate);
						l.setTime(time);
						mDeviceActiveService.insertHeartRate(l);
					}
					respJsonData.setRet(1);
				} else {
					respJsonData.setRet(0);
				}
				respJsonData.setCmd(AlphaAdultConfig.rUploadHeartRate);

			} /*else if (cmd.equals(AlphaAdultConfig.reqFamilyNumber)) { // 请求推送亲情号码
				String imei=reqJsonData.getImei();
				List<FamilyInfoAlpha> familyInfos = new ArrayList<FamilyInfoAlpha>();
//				if (session.containsAttribute("id")) {
//					deviceActive = (DeviceActive) session
//							.getAttribute("device_info");
//					logger.info("AlphaAdultConfig.reqFamilyNumber的亲情号码对应的设备imei"
//							+ deviceActive.getDeviceImei());
//				}
				if (imei != null) {
					//String devImei = deviceActive.getDeviceImei();
					Family family = new Family();
					family.setSerieNo(imei);
					List<Family> familys = mFamilyService.find(family);
					for (int i = 0; i < familys.size(); i++) {
						Family f = familys.get(i);
						FamilyInfoAlpha fa = new FamilyInfoAlpha();
						fa.setName(f.getNickName());
						fa.setNumber(f.getPhoneNumber());
						fa.setIcon(f.getIcon());
						familyInfos.add(fa);
					}
				}

				respJsonData.setCmd(AlphaAdultConfig.setFamilyNumber);
				respJsonData.setFamilys(familyInfos);
				respJsonData.setImei(imei);
			}*/ else if (cmd.equals(AlphaAdultConfig.rSetFamilyNumber)) { // 响应推送亲情号码
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqSosNumber)) { // 请求推送sos号码
				List<FamilyInfoAlpha> soss = new ArrayList<FamilyInfoAlpha>();
				if (session.containsAttribute("id")) {
					deviceActive = (DeviceActive) session
							.getAttribute("device_info");
					logger.info("AlphaAdultConfig.reqFamilyNumber的sos号码对应的设备imei"
							+ deviceActive.getDeviceImei());
				}
				if (deviceActive != null) {
					String devImei = deviceActive.getDeviceImei();
					Family family = new Family();
					family.setSerieNo(devImei);
					family.setRelativeType("2");
					List<Family> familys = mFamilyService.find(family);
					for (int i = 0; i < familys.size(); i++) {
						Family f = familys.get(i);
						FamilyInfoAlpha fa = new FamilyInfoAlpha();
						fa.setName(f.getNickName());
						fa.setNumber(f.getPhoneNumber());
						fa.setIcon(f.getIcon());
						soss.add(fa);
					}
				}

				respJsonData.setCmd(AlphaAdultConfig.setSosNumber);
				respJsonData.setSoss(soss);
				respJsonData.setSosMsg("救救我");
			} else if (cmd.equals(AlphaAdultConfig.rSetSosNumber)) { // 响应推送sos号码
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.rUploadHeartRate)) { // 响应心率上传
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqDisturb)) { // 请求推送防打扰
				List<ClockInfoAlp> dis = new ArrayList<ClockInfoAlp>();
				if (session.containsAttribute("id")) {
					deviceActive = (DeviceActive) session
							.getAttribute("device_info");
					logger.info("AlphaAdultConfig.reqFamilyNumber的sos号码对应的设备imei"
							+ deviceActive.getDeviceImei());
				}
				if (deviceActive != null) {
					String devImei = deviceActive.getDeviceImei();
					Clock clock = new Clock();
					clock.setSerieNo(devImei);
					List<Clock> clocks = mClockService.findDisturb(clock);
					for (int i = 0; i < clocks.size(); i++) {
						Clock f = clocks.get(i);
						ClockInfoAlp fa = new ClockInfoAlp();
						fa.setId(f.getId() + "");
						fa.setRepeat(f.getRepeat());
						fa.setTime(f.getTime());
						dis.add(fa);
					}
				}

				respJsonData.setCmd(AlphaAdultConfig.setDisturb);
				respJsonData.setDisturb(dis);
			} else if (cmd.equals(AlphaAdultConfig.reqGroups)) { // 请求联系人列表
             	String imei=reqJsonData.getImei();
				List<DeviceInfoAlp> dis = new ArrayList<DeviceInfoAlp>();
			/*	if (session.containsAttribute("id")) {
					deviceActive = (DeviceActive) session
							.getAttribute("device_info");
					logger.info("AlphaAdultConfig.reqFamilyNumber的sos号码对应的设备imei"
							+ deviceActive.getDeviceImei());
				}*/
				if (imei != null&&!"".equals(imei)) {
				//	String deviceImei = deviceActive.getDeviceImei();
					DeviceActive da = new DeviceActive();
					da.setDeviceImei(imei);
					List<DeviceActive> devices = mDeviceActiveService
							.findDeviceGroups(da);
					for (int i = 0; i < devices.size(); i++) {
						DeviceInfoAlp fa = new DeviceInfoAlp();
//						fa.setId(devices.get(i).getId());
						fa.setId(devices.get(i).getUid()+"");
						fa.setName(devices.get(i).getName());
						dis.add(fa);
					}
				}
				respJsonData.setCmd(AlphaAdultConfig.setGroups);
				respJsonData.setGroup(dis);
				respJsonData.setImei(imei);
			} /*else if (cmd.equals(AlphaAdultConfig.reqFriendNumber)) { // 请求推送好友号码
				List<FriendInfoAlpha> friendInfos = reqJsonData.getFriends();
				for (int i = 0; i <= friendInfos.size(); i++) {
					FriendInfoAlpha friend = friendInfos.get(i);
				}
				respJsonData.setFriends(friendInfos);
				reqJsonData.setCmd(AlphaAdultConfig.setFriendNumber);
			}*/ else if (cmd.equals(AlphaAdultConfig.rSetFriendNumber)) { // 响应推送好友号码
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqChildInfo)) {
				respJsonData.setCmd(AlphaAdultConfig.setChildInfo);
				respJsonData.setSex(1);
				respJsonData.setName("bobo");
				respJsonData.setBirthday("20140321");
			} else if (cmd.equals(AlphaAdultConfig.reqVoice)) {
				respJsonData.setCmd(AlphaAdultConfig.pushVoice);
			} else if (cmd.equals(AlphaAdultConfig.rPushVoice)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.rSetFamilyNumber)) {
				isResponse = false;
			}/*else if (cmd.equals(AlphaAdultConfig.setFamilyNumber)) {
				isResponse = false;
			}*/ else if (cmd.equals(AlphaAdultConfig.reqSosNumber)) {
				respJsonData.setCmd(AlphaAdultConfig.setSosNumber);
			} else if (cmd.equals(AlphaAdultConfig.reqDisturb)) {
				respJsonData.setCmd(AlphaAdultConfig.setDisturb);
			} else if (cmd.equals(AlphaAdultConfig.rSetDisturb)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.rSetGroups)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqProfiles)) {
				List<SceneModeInfoAlp> mProfiles = new ArrayList<SceneModeInfoAlp>();

				for (int i = 0; i < 2; i++) {
					SceneModeInfoAlp profiles = new SceneModeInfoAlp();
					switch (i) {
					case 0:
						profiles.setId("20141118142132");
						profiles.setRepeat("1100010");
						profiles.setStart("0630");
						profiles.setEnd("1130");
						profiles.setVibrate(1);
						profiles.setRing(0);
						profiles.setIsOn(1);
						break;
					case 1:
						profiles.setId("20141118142133");
						profiles.setRepeat("1100010");
						profiles.setStart("1430");
						profiles.setEnd("1830");
						profiles.setVibrate(0);
						profiles.setRing(1);
						profiles.setIsOn(1);
						break;
					}
					mProfiles.add(profiles);
				}
				respJsonData.setCmd(AlphaAdultConfig.profiles);
				respJsonData.setProfiles(mProfiles);
			} else if (cmd.equals(AlphaAdultConfig.rProfiles)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.uploadPedometerData)) {
				// // 上传计步数据
				// String userId = "";
				// if (session.containsAttribute("id")) {
				// userId = (String) session.getAttribute("id");
				// }
				reqJsonData.getBatPower();
				List<CountStepAlp> stepData = reqJsonData.getStepData();

				subData.setCmd(AlphaAdultConfig.T_U_RUPLOADPEDOMETER); // 响应请求计步数据
				subData.setPedometerData(stepData);
				sub = JSON.toJSONString(subData);
				IoSession tempSession = mClientSessionManager.getSessionId("u_"
						+ userId);
				if (tempSession != null) {
					WriteFuture writeFuture = tempSession.write(sub.toString());
					writeFuture.addListener(new IoFutureListener<IoFuture>() {

						@Override
						public void operationComplete(IoFuture future) {
							// TODO Auto-generated method stub
							if (((WriteFuture) future).isWritten()) { // 发送成功
								logger.info("推送数据配置文件成功");
							} else {
								logger.info("推送数据配置文件失败=设备不在线");
							}
						}
					});
				} else {
					logger.info("推送数据配置文件失败=设备没有上传心跳");
				}
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.uploadLctData)) {
				DeviceActive deviceInfo = (DeviceActive) session
						.getAttribute("device_info");
				if (deviceInfo == null) {
					return;
				}
				int type = reqJsonData.getType();
				String imei = reqJsonData.getImei();
				int batPower = reqJsonData.getBatPower();
				String jsonToString = "-1";
				String[] str = null;

				StringBuffer lbs = new StringBuffer();
				List<LocationInfoAlp> mLocationInfoAlpList = reqJsonData
						.getLctData();

				if (mLocationInfoAlpList.get(0).getLon() != 0
						&& mLocationInfoAlpList.get(0).getLat() != 0) {
					jsonToString = HttpRequest
							.sendGet(
									Constant.LOCATION_URL,
									"locations="
											+ mLocationInfoAlpList.get(0)
													.getLon()
											+ ","
											+ mLocationInfoAlpList.get(0)
													.getLat()
											+ "&coordsys=gps&output=json&key=801df1e9132e2151cd9ad435ecc59858");
					if (!"-1".equals(jsonToString)) {
						info.setLocationType("1");
						logger.info("jsonToString++" + jsonToString);
						JSONObject object = JSONObject
								.parseObject(jsonToString);
						String location = object.getString("locations");
						str = location.split(",");
						info.setChangeLatitude(str[1]);
						info.setChangeLongitude(str[0]);
					} else {
						/*
						 * str = new String[2]; str[0] =
						 * String.valueOf(mLocationInfoAlpList.get(0).getLon());
						 * str[1] =
						 * String.valueOf(mLocationInfoAlpList.get(0).getLat());
						 */
					}
					info.setLongitude(String.valueOf(mLocationInfoAlpList
							.get(0).getLon()));
					info.setLatitude(String.valueOf(mLocationInfoAlpList.get(0)
							.getLat()));
					info.setLocationType("1");
				} else {
					LbsInfoAlp mLbsInfoAlp = mLocationInfoAlpList.get(0)
							.getLbs();

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("accesstype", "0");
					map.put("network", "GSM/EDGE");
					map.put("cdma", "0"); // 非cdma卡
					map.put("imei", deviceInfo.getDeviceImei()); // 手机imei号
					map.put("smac", deviceInfo.getDeviceImei());
					int lbsLength = mLbsInfoAlp.getCells().size();
					for (int i = 0; i < lbsLength; i++) {
						Cell temp = mLbsInfoAlp.getCells().get(i);
						lbs.append(temp.getMc()).append(",")
								.append(temp.getMn()).append(",")
								.append(temp.getL()).append(",")
								.append(temp.getC()).append(",")
								.append(temp.getS());
						if (i == 0) {
							map.put("bts", lbs.toString());
						}
						lbs.append("|");
					}
					map.put("nearbts", lbs.toString());
					map.put("key", Constant.KEYS);
					jsonToString = HttpRequest.sendGetToGaoDe(
							Constant.LOCATION_LBS_URL, map);
					logger.info("jsonToString++" + jsonToString);
					if (!"-1".equals(jsonToString)) {
						JSONObject jsons = JSONObject.parseObject(jsonToString);
						String status = jsons.getString("status"); // 回传状态,1表示成功
						if (status.equals("1")) {
							String results = jsons.getString("result"); // 结果级
							JSONObject jsonResult = JSONObject
									.parseObject(results);
							String locationJson = jsonResult
									.containsKey("location") ? jsonResult
									.getString("location") : null; // 经纬度
							if (locationJson != null) {
								info.setLocationType("0");
								logger.info("locationJson++" + locationJson);
								str = locationJson.split(",");
								info.setLongitude(str[0]);
								info.setLatitude(str[1]);
							}
						}
					}
				}
				if (str != null) {
					// info.setLocationType("0");
					info.setSerieNo(deviceInfo.getDeviceImei()); // 手表id
					info.setBelongProject(deviceInfo.getBelongProject());

					info.setShowType("1"); // 保证点是存在的
					info.setPage(0);
					info.setRows(1);
					info.setSort("id");
					info.setOrder("DESC");
					List<LocationInfo> mLocationList = mLocationInfoService
							.find(info);
					int LocationId = 0;
					String date = "0"; // 上一个点的定位时间
					boolean bool_is_update = true;
					if (mLocationList.size() > 0) {
						LocationId = mLocationList.get(0).getId();
						date = "" + mLocationList.get(0).getUploadTime();

						double long1 = Double.parseDouble(str[0]);
						double lat1 = Double.parseDouble(str[1]);
						double long2 = Double.parseDouble(mLocationList.get(0)
								.getLongitude());
						double lat2 = Double.parseDouble(mLocationList.get(0)
								.getLatitude());
						
						 /* bool_is_update = Constant.getDistance(lat1, long1,
						  lat2, long2,Constant.EFFERT_DATA);*/
						 
					}
					info.setSerieNo(deviceInfo.getDeviceImei());
					info.setBattery(reqJsonData.getBatPower());

					info.setChangeLongitude(str[0]);
					info.setChangeLatitude(str[1]);
					info.setAccuracy(10);
					info.setUploadTime(new Date());
					info.setFall("1");
					info.setSportType(type + "");

					if (bool_is_update || CalculatorUtils.isSameDay(date)) {
						info.setShowType("1");
						info.setEndTime(new Date());
					} else {
						info.setShowType("0");
						info.setEndTime(null);
					}
					mLocationInfoService.insert(info);
					if (info.getShowType().equals("0")) {
						info.setId(LocationId);
						info.setUploadTime(null);
						info.setEndTime(new Date());

						mLocationInfoService.update(info);
					}
				}
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.moveState)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqTimeSync)) {
				respJsonData.setCmd(AlphaAdultConfig.rReqTimeSync);
				respJsonData.setDevTime(dataFormat.format(new Date()));
			} else if (cmd.equals(AlphaAdultConfig.rMonitor)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.devPos)) {
				respJsonData.setCmd(AlphaAdultConfig.devPos);
				List<Double> mList = new ArrayList<Double>();
				mList.add(114.235468);
				mList.add(22.21548);

				respJsonData.setPosition(mList);
			} else if (cmd.equals(AlphaAdultConfig.uploadVoice)) {
				StringBuffer msg = new StringBuffer();
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
					logger.info("devId！1---------" + devId);
				}
				System.out.println("devId2----------" + devId);
				logger.info("devId---------" + devId);
				if (devId == null || test.getVoice().length == 0) {
					// devId = "d_11"; //调试用
					logger.info("测试语音的值为null++");
					respJsonData.setCmd(AlphaAdultConfig.rUploadVoice); // 告诉设备语音已推至服务器端
					return;
				}
				String did = devId.split("_")[1];
				String path = Constant.MEDIA_PATH + did
						+ System.getProperty("file.separator");
				String fileName = Constant.getUniqueCode(did) + ".amr";
				try {
					Constant.createFileContent(path, fileName, test.getVoice());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				respJsonData.setCmd(AlphaAdultConfig.rUploadVoice); // 告诉设备语音已推至服务器端
				respJsonData.setMsgId(receive[6]);
				respJsonData.setId(respJsonData.getId());
				respJsonData.setRet(0);
				respJsonData.setMsg("ok");
				respJsonData.setHaveUnread(0);

				deviceActive.setId(Integer.valueOf(did));
				deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					DeviceActive mDeviceActive = deviceList.get(0);
					userId = mDeviceActive.getUserId();
					subData.setCmd(AlphaAdultConfig.T_U_VOICE); // ID#SID#DATA#T_L#TYPE
					msg.append(did).append("#")
							.append(mDeviceActive.getUserId()).append("#")
							.append(Base64.encodeBase64String(test.getVoice()))
							.append("#").append(reqJsonData.getDuration())
							.append("#").append("0");
					subData.setMsg(msg.toString());

					msg.delete(0, msg.length()); // headUrl#st#nick#ft
					msg.append(mDeviceActive.getDeviceHead()).append("#")
							.append(System.currentTimeMillis()).append("#")
							.append(mDeviceActive.getDeviceName()).append("#")
							.append("2");
					subData.setOther(msg.toString());
					isSub = true; // 下发给app
					isResponse = true;
				} else {
					respJsonData.setCmd(AlphaAdultConfig.rUploadVoice); // 告诉设备语音已推至服务器端
				}

			} else if (cmd.equals(AlphaAdultConfig.reqVoiceTest)) {
				StringBuffer msg = new StringBuffer();
				String path = Constant.MEDIA_PATH + "test"
						+ System.getProperty("file.separator") + "test.amr";

				try {
					byte[] voiceData = Constant.getContent(path);
					devId = reqJsonData.getDevId();
					deviceActive.setId(Integer.valueOf(devId));
					deviceActive.setDeviceDisable("1"); // 并且属于激活状态

					List<DeviceActive> deviceList = mDeviceActiveService
							.find(deviceActive);
					if (deviceList.size() > 0) {
						DeviceActive mDeviceActive = deviceList.get(0);
						userId = mDeviceActive.getUserId();
						subData.setCmd(AlphaAdultConfig.T_U_VOICE); // ID#SID#DATA#T_L#TYPE
						msg.append(devId).append("#")
								.append(mDeviceActive.getUserId()).append("#")
								.append(Base64.encodeBase64String(voiceData))
								.append("#").append("3").append("#")
								.append("0");
						subData.setMsg(msg.toString());

						msg.delete(0, msg.length()); // headUrl#st#nick#ft
						msg.append(mDeviceActive.getDeviceHead()).append("#")
								.append(System.currentTimeMillis()).append("#")
								.append(mDeviceActive.getDeviceName())
								.append("#").append("2");
						subData.setOther(msg.toString());
						isSub = true; // 下发给app
						isResponse = true; // 是否响应
						// respJsonData.setCmd(AlphaAdultConfig.reqVoiceTest+"_RE");
						respJsonData.setCmd(AlphaAdultConfig.rUploadVoice); // 告诉设备语音已推至服务器端
						respJsonData.setMsgId(receive[6]);
						respJsonData.setId(respJsonData.getId());
						respJsonData.setRet(0);
						respJsonData.setMsg("ok");
						respJsonData.setHaveUnread(0);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (cmd.equals(AlphaAdultConfig.reqCaptcha)) {
				respJsonData.setCmd(AlphaAdultConfig.rReqCaptcha);
				respJsonData.setCaptcha("1234");
			} else if (cmd.equals(AlphaAdultConfig.rReqCaptcha)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.uploadDevInfo)) {
				// 上传设备信息
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				DeviceInfoAlp deviInfo = reqJsonData.getDevInfo();
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.rejectNumber)) {
				// 拦截号码上报
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				reqJsonData.getNumber();
				reqJsonData.getTimeStamp();
				reqJsonData.getErr();
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.LeaveAlarm)) {
				// 上报脱手报警
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				reqJsonData.getTimeStamp();
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.ChargeResult)) {
				// 上报查询话费结果
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				reqJsonData.getResult();
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.BusChargeResult)) {
				// 上报查询公交余额结果
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				String balance = reqJsonData.getBalance();
				deviceActive.setId(Integer.valueOf(devId));
				deviceActive.setBalance(balance);
				mDeviceActiveService.update(deviceActive);
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.ResCharge)) {
				// 响应查询话费指令
				String oper = reqJsonData.getOperator();
				devId = (String) session.getAttribute("id");

				if (devId != null) {
					String did = CalculatorUtils.getSplitRegx(devId, "_")[1]; // 第二个id为设备id
					deviceActive.setId(Integer.valueOf(did));
					deviceActive.setDeviceDisable("1"); // 保证激活状态
					List<DeviceActive> deviceList = mDeviceActiveService
							.find(deviceActive);
					if (deviceList.size() > 0) {
						userId = deviceList.get(0).getUserId();
					}
				}
				if (!"0".equals(userId)) {
					subData.setCmd(AlphaAdultConfig.T_U_RCHECKCHARGE);
					subData.setMsg(oper);
					isSub = true;
				}
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.Sign)) {
				// 宝宝签到，上报签到信息
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				reqJsonData.getDate();
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.SleepResult)) {
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				// 上传睡眠监测结果
				reqJsonData.setTotaltime(300);
				reqJsonData.setDeeptime(150);
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.voiceRead)) {
				isResponse = false;
			} else if (cmd.equals(AlphaAdultConfig.reqMySelfInfo)) {
				respJsonData.setCmd(AlphaAdultConfig.setMyselfInfo);
				if (session.containsAttribute("id")) {
					devId = (String) session.getAttribute("id");
				}
				String imei = reqJsonData.getImei();
				respJsonData.setImei(imei);
				Setting so = new Setting();
				so.setSerieNo(imei);
				List<Setting> seList = mSettingService.find(so);
				if (seList.size() > 0) {
					String high = seList.get(0).getHigh();
					String weight = seList.get(0).getWeight();
					String sex = seList.get(0).getSex();
					String stepd = seList.get(0).getStepd();
					
				/*	reqJsonData.setHigh(high);
					reqJsonData.setWeight(weight);
					reqJsonData.setSex(sex);
					reqJsonData.setStepd(stepd);*/
					respJsonData.setHigh(high);
					respJsonData.setWeight(weight);
					respJsonData.setSex(Integer.valueOf(sex));
					respJsonData.setStepd(stepd);
				} else {
					respJsonData.setHigh("170");
					respJsonData.setWeight("45");
					respJsonData.setSex(1);
					respJsonData.setStepd("45");
				}
			} else if (cmd.equals(AlphaAdultConfig.rSetMyselfInfo)) {
				isResponse = false;
			}else{
				
			}
			if (isSub) {
				deviceActive.setId(Integer.valueOf(devId.split("_")[1]));
				deviceActive.setDeviceStatus("1");
				mDeviceActiveService.update(deviceActive);

				IoSession tempSession = mClientSessionManager.getSessionId("u_"
						+ userId);// 回传给用户

				sub = JSON.toJSONString(subData);
				if (tempSession != null) {
					tempSession.write(sub);
				}
			}
			if (isResponse) {
				resp = JSON.toJSONString(respJsonData);
				int temp = resp.length();
				int lenTotal = String.valueOf(temp).length() + temp + 8;
				if (resp != null && receive != null && receive.length > 0) {
					lenTotal += receive[3].length() + receive[4].length()
							+ receive[5].length() + receive[6].length()
							+ respJsonData.getCmd().length()
							+ receive[8].length();
					for (int i = 0; i < receive.length; i++) {
						if (i != 0)
							backSb.append("|");
						if (i == 1)
							backSb.append(lenTotal);
						else if (i == 9)
							backSb.append(resp);
						else if (i == 7)
							backSb.append(respJsonData.getCmd());
						else if (i == 2)
							backSb.append(temp);
						else
							backSb.append(receive[i]);
					}
					backSb.append("|");
					resp = backSb.toString();
					logger.info("阿尔法青少年服务器回复设备------resp-----------------" + resp);
					DataBean dataBean = new DataBean();
					dataBean.setContent(resp);
					session.write(dataBean);
				} else {
					session.write("-2"); // 传参数错误
				}
			}
			}
			/*
			 * } catch (JsonParseException e1) { // TODO Auto-generated catch
			 * block e1.printStackTrace(); } catch (JsonMappingException e1) {
			 * // TODO Auto-generated catch block e1.printStackTrace(); } catch
			 * (IOException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); }
			 */

		}
	}

	/*
	 * public static void main(String[] args) { String
	 * a="dasfpakf\\|4557\\|hha|2"; String[] receive =
	 * CalculatorUtils.getSplitRegx(a, "\\|"); System.out.println(receive[0]);
	 * System.out.println(receive[1]); System.out.println(receive[2]);
	 * System.out.println(receive[3]); String b="hhhhh2|";
	 * System.out.println(b.replace("|","44")); String reqString =
	 * CalculatorUtils.getSubStr(a, "|", 2).replace("|", ""); String
	 * d="adfa|dcc|d01f||qqqq||aa"; String cc=CalculatorUtils.getSubStr(d, "||",
	 * 1); System.out.println(reqString); System.out.println("++++"+cc); }
	 */
	/*
	 * public static void main(String[] args) { String s="c房估字第c5445465号";
	 * String a=s.substring(s.indexOf("c"), s.indexOf("号"));
	 * System.out.println(a); }
	 */
/*public static void main(String[] args) {
	SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println("aa"+dataFormat.format(new Date()));
System.out.println("aa"+new Date());

}*/
}
