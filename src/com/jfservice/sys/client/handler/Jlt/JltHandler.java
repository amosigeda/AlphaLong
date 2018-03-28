package com.jfservice.sys.client.handler.Jlt;

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
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import com.jfservice.common.bean.alpha.other.CountStepAlp;
import com.jfservice.common.bean.alpha.other.DeviceInfoAlp;

public class JltHandler extends ClientMessageEventImpl {

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
		SimpleDateFormat dataFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss.sss");
		System.out.println("dataFormat:---------------"+dataFormat);
		// 数据库bean文件操作
		DeviceActive deviceActive = new DeviceActive();
		LocationInfo info = new LocationInfo(); // 定位数据保存

		String sub = "";
		String userId = "0";
		String devId = "0";
		SubcriJsonData subData = new SubcriJsonData();

		
		if (message != null && session != null) {
			System.out.println("CCCCCCCCCCCC-----aaaa");
			System.out.println("MESSSAGE------------" + message);
			StringBuffer backSb = new StringBuffer();
			// DataBean test = (DataBean) message;
			String text = (String) message;
			String quk = text.replaceAll(" ", "");
			System.out.println("aaaaaaaaaaaaa-----" + quk);
			
			Gson gson=new Gson();
			  logger.info("gson后");
			  logger.info("反序列化前");
		      ReqJsonData reqJsonData = gson.fromJson(quk,ReqJsonData.class);
		      logger.info("反序列化后");
			// byte[] a=test.getVoice();
			/*
			 * String[] receive =
			 * CalculatorUtils.getSplitRegx(test.getContent(), "\\|");
			 */
			/*
			 * System.out.println("bbbbbbbbbbbbbbbb-----------"+receive[9]);
			 * String reqString =
			 * CalculatorUtils.getSubStr(test.getContent(),"|", 2).replace("|",
			 * ""); logger.info("第二个|");
			 */
			/*
			 * String reqString =
			 * CalculatorUtils.getSubStr(test.getContent(),"||", 1).replace("|",
			 * ""); if(reqString.contains("#!AMR")){
			 * reqString=reqString.substring(reqString.indexOf("{"),
			 * reqString.indexOf("#")); }
			 */
			// int a=text.length();
			logger.info("处理过后：quk---------------------"+quk);
			String cmd = quk.substring(12, 14);
			System.out.println("cmd--------" + cmd);
			String front = quk.substring(0, 14);
			System.out.println("front---------------" + front);
			String gotfront = quk.replace(front, "");
			System.out.println("去前14位后---------"+gotfront);
			String over = quk.substring(quk.length()-2,quk.length());
			System.out.println("over:--------------------"+over);
			String resolve = ChangeDecode(gotfront);
			System.out.println("11111111111111tttt:" + resolve);
			String noImei = gotfront.substring(0,30);
			System.out.println("noImei-------------:"+noImei);
			
//			// ReqJsonData reqJsonData =
//			// JSON.parseObject(reqString,ReqJsonData.class);
//			String resolve = resolve.replace("|", "").replace(" ", "");
//			String resolve = resolve.replace("|", "");
//			System.out.println("gggggggggg----" + resolve);
//			int len = resolve.length();
			System.out.println("gggggggggg----" + resolve);
			int len = resolve.length();
			System.out.println("长度---" + len);
			long time = new Date().getTime();
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			String tm = sdf.format(date).replaceAll(" ", "").replaceAll(":", "").replaceAll("-", "");
			/*
			 * logger.info("mapper前"); ObjectMapper mapper=new ObjectMapper();
			 * logger.info("mapper后-----------"); ReqJsonData reqJsonData =
			 * mapper.readValue(reqString, ReqJsonData.class);
			 * logger.info("reqJsonData-----------------------lalalalala"
			 * +reqJsonData); String cmd = reqJsonData.getCmd();
			 * logger.info("CMDCMD--------------------------------------"+cmd);
			 */
			if (cmd.equals("01")) {
//				String front = quk.substring(0, 14);
//				System.out.println("front---------------" + front);
//				String gotfront = quk.replace(front, "");
//				String resolve = ChangeDecode(gotfront);
//				System.out.println("11111111111111tttt:" + resolve);
//				// ReqJsonData reqJsonData =
//				// JSON.parseObject(reqString,ReqJsonData.class);
//				String resolve = resolve.replace("|", "").replace(" ", "");
//				System.out.println("gggggggggg----" + resolve);
//				int len = resolve.length();
//				System.out.println("长度---" + len);
				System.out.println("dataFormat--------------:"+dataFormat);
				String deviceimei = resolve.substring(0, 15).replaceAll(" ", "");
				System.out.println("deviceimei--------" + deviceimei);
				String fW = resolve.substring(15, 25).replaceAll(" ", "");
				System.out.println("fw" + fW);
				String pm = resolve.substring(25, 30).replaceAll(" ", "");
				System.out.println(pm);
				String bg = resolve.substring(30, 32).replaceAll(" ", "");
				System.out.println(bg);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					
					
					/*
					 * int id = deviceList.get(0).getId(); String did = "d_" +
					 * id; session.setAttributeIfAbsent("id", did); // 每一个通道的id
					 * session.setAttributeIfAbsent("device_info",
					 * deviceList.get(0)); // 记录设备信息
					 * 
					 * if (mClientSessionManager.getSessionId(did) != null) {
					 * mClientSessionManager.removeSessionId(did); }
					 * mClientSessionManager.addSessionId(did, session);
					 */
					String resultCode = ChangeEncode("01");
					System.out.println("返回结果-------------："+resultCode);
					resp = front + resultCode+tm+noImei+over;
					System.out.println("resp---------------:"+resp);
				} else {
					String resultCode = ChangeEncode("-1");
					System.out.println("返回结果-------------："+resultCode);
					resp = front + resultCode + tm +noImei+over;
				}

			}else if(cmd.equals("02")){
				String type = resolve.substring(0, 1).replaceAll(" ", "");
				System.out.println("imei--------" + type);
				String imei = resolve.substring(1, 16).replaceAll(" ", "");
				System.out.println("imei--------" + imei);
				String lon = resolve.substring(16,21).replaceAll(" ", "");
				System.out.println("lon--------"+lon);
				String lat = resolve.substring(21,25).replaceAll(" ", "");
				System.out.println("lat--------"+lat);
				String gltype = resolve.substring(25,26).replaceAll(" ", "");
				System.out.println("gltype--------"+gltype);
				String dateUTC = resolve.substring(26, 30).replaceAll(" ", "");
				System.out.println("fw" + dateUTC);
				String timeUTC = resolve.substring(30, 33).replaceAll(" ", "");
				System.out.println(timeUTC);

//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				System.out.println("deviceList--------:"+deviceList);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front+resultCode+over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front+resultCode+over;
				}
			}else if(cmd.equals("03")){
				for(int i=1;i<=4;i++){
					String s = resolve.substring(0,36*(i-1));
					String str = resolve.replace(s, "");
					String familyID = "familyID"+i+1;
					String name = "name"+i+1;
					String phone = "phone"+i+1;
					String type = "type"+i+1;
					
					familyID = str.substring(0, 4).replaceAll(" ", "");
					System.out.println("family"+i+"--------" + familyID);
					name = str.substring(4, 24).replaceAll(" ", "");
					System.out.println("name"+i+"--------" + name);
					phone = str.substring(24, 35).replaceAll(" ", "");
					System.out.println("phone"+i+"--------" +phone);
					type = str.substring(35, 36).replaceAll(" ", "");
					System.out.println("type"+i+"--------" +type);
					
				}
				

//				deviceActive.setDeviceImei();
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					 resp = front + resultCode+over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode+over;
				}
			}else if(cmd.equals("04")){
				String type = resolve.substring(0, 1).replaceAll(" ", "");
				System.out.println("type--------" + type);

//				deviceActive.setDeviceImei(type);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("05")){
				String imei = resolve.substring(0, 15).replaceAll(" ", "");
				System.out.println("imei--------" + imei);
				String type = resolve.substring(15, 16).replaceAll(" ", "");
				System.out.println("type" + type);

//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("06")){
				String projectCode = resolve.substring(0, 1);
				System.out.println("projectCode--------" + projectCode);
				String projectSet = resolve.substring(1, 2);
				System.out.println("projectSet" + projectSet);
//
//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("07")){
				String f_l = resolve.substring(0, 1);
				System.out.println("f_l--------" + f_l);
				String f_n = resolve.substring(1, len-2);
				System.out.println("f_n" + f_n);

//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("08")){
				String itemNumber = resolve.substring(0, 1);
				System.out.println("itemNumber--------" + itemNumber);
				String itemName = resolve.substring(1, 2);
				System.out.println("itemName" + itemName);
				String itemTime = resolve.substring(2,14);
				System.out.println("itemTime--------" + itemTime);
				String type = resolve.substring(14,15);
				System.out.println("type--------"+type);

//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("09")){
//				String imei = resolve.substring(0, 15);
//				System.out.println("imei--------" + imei);
//				String type = resolve.substring(15, 16);
//				System.out.println("type" + type);
//
//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("10")){
				String bel = resolve.substring(0, 1);
				System.out.println("bel--------" + bel);
//				String type = resolve.substring(15, 16);
//				System.out.println("type" + type);

//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("11")){
//				String imei = resolve.substring(0, 15);
//				System.out.println("imei--------" + imei);
//				String type = resolve.substring(15, 16);
//				System.out.println("type" + type);

//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode +over;
				} else {
					String resultCode = ChangeEncode("-1");
					resp = front + resultCode +over;
				}
			}else if(cmd.equals("12")){
				String groPackage = resolve.substring(0, len-1);
				System.out.println("groPackage--------" + groPackage);
//				String type = resolve.substring(15, 16);
//				System.out.println("type" + type);

//				deviceActive.setDeviceImei(imei);
				// deviceActive.setDeviceDisable("1"); // 并且属于激活状态
				List<DeviceActive> deviceList = mDeviceActiveService
						.find(deviceActive);
				if (deviceList.size() > 0) {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode + over;
				} else {
					String resultCode = ChangeEncode("01");
					resp = front + resultCode + over;
				}
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
				
				
				/*
				 * int temp = resp.length(); int lenTotal =
				 * String.valueOf(temp).length() + temp + 8; if (resp != null &&
				 * receive != null && receive.length > 0) { lenTotal +=
				 * receive[3].length() + receive[4].length() +
				 * receive[5].length() + receive[6].length() +
				 * respJsonData.getCmd().length() + receive[8].length(); for
				 * (int i = 0; i < receive.length; i++) { if (i != 0)
				 * backSb.append("|"); if (i == 1) backSb.append(lenTotal); else
				 * if (i == 9) backSb.append(resp); else if (i == 7)
				 * backSb.append(respJsonData.getCmd()); else if (i == 2)
				 * backSb.append(temp); else backSb.append(receive[i]); }
				 * backSb.append("|");
				 */
				// resp = backSb.toString();
//				DataBean dataBean = new DataBean();
//				dataBean.setContent(resp);
				session.write(resp);
			} else {
				session.write("-2"); // 传参数错误
			}
			logger.info("AlphaHandler------resp-----------------"+resp);
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

	/*
	 * 将十六进制编码转化为字符串格式（适用于中文）
	 */
	public static String ChangeDecode(String str) {
		str = str.replaceAll(" ", "");
		byte[] bs = new byte[str.length() / 2];
		for (int i = 0; i < bs.length; i++) {
			try {
				bs[i] = (byte) (0xff & Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			str = new String(bs, "utf-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return str;
	}
	/*
	 * 将字符串编码成16进制数字，适用于所有字符（包括中文）
	 */
	private static String hexString = "0123456789ABCDEF";
	
	public static String ChangeEncode(String str){
		//根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length*2);
		//将字节数组中每个字节拆解成2位16进制整数
		for(int i= 0;i<bytes.length;i++){
			sb.append(hexString.charAt((bytes[i]&0x0f)>>4));
			sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
		}
		
		return str;
	}
}
