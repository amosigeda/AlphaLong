package com.jfservice.sys.client.handler.weike;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfservice.common.bean.weike.Body;
import com.jfservice.common.bean.weike.Common;
import com.jfservice.common.bean.weike.Function;
import com.jfservice.common.bean.weike.Location;
import com.jfservice.common.bean.weike.Parameters;
import com.jfservice.common.bean.weike.ResponseBean;
import com.jfservice.common.bean.weike.Result;
import com.jfservice.common.bean.weike.Weather;
import com.jfservice.common.http.HttpRequest;
import com.jfservice.common.json.WeatherBean;
import com.jfservice.common.lang.CalculatorUtils;
import com.jfservice.common.lang.Constant;
import com.jfservice.sys.client.handler.impl.ClientMessageEventImpl;
import com.jfservice.sys.clock.model.Clock;
import com.jfservice.sys.clock.service.ClockService;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;
import com.jfservice.sys.deviceactiveinfo.service.DeviceActiveService;
import com.jfservice.sys.family.model.Family;
import com.jfservice.sys.family.service.FamilyService;
import com.jfservice.sys.location.model.LocationInfo;
import com.jfservice.sys.location.service.LocationService;
import com.jfservice.sys.setting.model.Setting;
import com.jfservice.sys.setting.service.SettingService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Service
public class DeviceHandler extends ClientMessageEventImpl{

	private final static String TAG = DeviceHandler.class.getSimpleName();
	private Logger logger = Logger.getLogger(DeviceHandler.class);	
	
	@Autowired
	private DeviceActiveService mDeviceActiveService;
	
	@Autowired
	private ApplicationContext mApplicationContext;
	
	@Autowired
	private LocationService mLocationInfoService;
	
	@Autowired
	private FamilyService mFamilyService;
	
	@Autowired
	private SettingService mSettingService;
	
	@Autowired
	private ClockService mClockService;
	/**
	 * 客户协议
	 */
	private final static String WEIKE = "watch_id";
	private final static String WEIKE_GPS = "SubmitGPS";
	private final static String WEIKE_LBS = "SubmitMulGSM";
	private final static String WEIKE_WIFI = "SubmitWIFI";
	private final static String WEIKE_BATTERY = "SubmitBattery";
    private final static String WEIKE_POWERON = "PowerOn";
    private final static String D_POW = "D_POW";
	
	@Override
	public void handler(Object message, IoSession session) {
		// TODO Auto-generated method stub
		final JSONObject responseJson = new JSONObject();
	
		if(message != null && session != null){
			try{
				String temp = (String)message;
				if(temp.contains(WEIKE)){  //威科的协议
					StringBuffer locationSb = new StringBuffer();
					StringBuffer sb = new StringBuffer();
					ResponseBean response = (ResponseBean)mApplicationContext.getBean("resp");
					Result result = (Result)mApplicationContext.getBean("result");
					Common commom = (Common)mApplicationContext.getBean("common");
					result.setStatus("1");
					commom.setResult(result);
					response.setCommon(commom);
					XStream xstream = new XStream(new DomDriver());
					xstream.processAnnotations(ResponseBean.class);
					
					temp = temp.replaceAll("_", "");
					ResponseBean test = (ResponseBean)xstream.fromXML(temp);
					Function function = test.getCommon().getFunction();
					
					DeviceActive deviceActive = new DeviceActive();
					deviceActive.setDeviceImei(function.getWatchId());
					List<DeviceActive> mList = mDeviceActiveService.find(deviceActive);
					int resultCode = Constant.FAIL_CODE;
					boolean bool_location = false;
					if(mList.size() > 0){   //说明存在
						int id = mList.get(0).getId();
						String belongProject = mList.get(0).getBelongProject();
						
						LocationInfo info = new LocationInfo();      //定位数据保存		
						Body body = test.getBody();
						Location location = null;
						String battery = "100";
						if(body != null){
							location = body.getLocation();   //定位的xml文件解析
						}									
						if(location != null){
							battery = location.getBattery().replace("%", "");
						}
						//判断接口属于哪一个
						if(function.getId().equals(WEIKE_BATTERY)){   //电池上传
							String batterys = test.getBody().getBattery().getContent();
						}else if(function.getId().equals(WEIKE_GPS)){
							double[] data = CalculatorUtils.calculatorLonAndLat(location.getLon(), location.getLat());
							//先拿到经纬度的数组
							logger.info(TAG+",获取的经纬度分别为="+data[0]+","+data[1]);
							
							info.setBelongProject(belongProject);
							info.setSerieNo(function.getWatchId());
							info.setPage(0);
							info.setRows(1);
							info.setSort("id");
							info.setOrder("DESC");
							List<LocationInfo> mLocationList = mLocationInfoService.find(info);
							//根据条件着信息
							int LocationId = 0;
							boolean bool_is_update = true;
							info.setBattery(Integer.parseInt(battery));
							info.setLongitude(""+data[0]);
							info.setLatitude(""+data[1]);
							info.setChangeLongitude(""+data[0]);
							info.setChangeLatitude(""+data[1]);
							info.setAccuracy(10);
							info.setLocationType("1");
							
							info.setUploadTime(new Date());
							info.setShowType("1"); // 都是显示的
							info.setEndTime(new Date());
							info.setFall(location.getAdorn());
							//String resultCodes = "{\"locations\":\"113.4582,23.1687\"}";    //调试
							String resultCodes = HttpRequest
										.sendGet(Constant.LOCATION_URL,
												"locations="
														+ info.getLongitude()
														+ ","
														+ info.getLatitude()
														+ "&coordsys=gps&output=json&key="+Constant.KEY);
							logger.info("resultCodes++" + resultCodes);
							if ("-1".equals(resultCodes)) {
								result.setStatus("0");
								resultCode = Constant.FAIL_CODE;
								info.setChangeLongitude("0");
								info.setChangeLatitude("0");
							}else{
								result.setStatus("1");
								JSONObject object = JSONObject.parseObject(resultCodes);
								String locationJson = object.getString("locations");  //经纬度							
								
								String[] str = locationJson.split(",");
								if (str.length == 2 && mLocationList.size() > 0) {
									locationSb.append(str[0]).append(":").append(str[1]);
									LocationId = mLocationList.get(0).getId();
									double long1 = Double.parseDouble(str[0]);
									double lat1 = Double.parseDouble(str[1]);
									
									double long2 = Double.parseDouble(
											mLocationList.get(0).getLongitude());
									double lat2 = Double.parseDouble(
											mLocationList.get(0).getLatitude());
									
									info.setChangeLongitude(str[0]);
									info.setChangeLatitude(str[1]);
									/*bool_is_update = Constant.getDistance(lat1,
											long1, lat2, long2,
											Constant.EFFERT_DATA);*/
								}
							}
							if (bool_is_update) {
								mLocationInfoService.insert(info);
							}else{
								info.setId(LocationId);
								mLocationInfoService.update(info);
							}
							resultCode = Constant.SUCCESS_CODE;
							bool_location = true;
						}else if(function.getId().equals(WEIKE_LBS)){
							StringBuffer lbs = new StringBuffer();
							lbs.append(location.getMcc1()).append(",")
							.append(location.getMnc1()).append(",")
							.append(location.getLac1()).append(",")
							.append(location.getCellId1()).append(",")
							.append(location.getRssi1());
							
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("accesstype", "0");
							map.put("network", "GSM/EDGE");
							map.put("cdma", "0");  //非cdma卡
							map.put("imei", function.getWatchId());  //手机imei号
							map.put("smac", function.getWatchId());
							map.put("bts", lbs.toString());
							
							lbs.append("|");
							lbs.append(location.getMcc2()).append(",")
							.append(location.getMnc2()).append(",")
							.append(location.getLac2()).append(",")
							.append(location.getCellId2()).append(",")
							.append(location.getRssi2());
							lbs.append("|");
							lbs.append(location.getMcc3()).append(",")
							.append(location.getMnc3()).append(",")
							.append(location.getLac3()).append(",")
							.append(location.getCellId3()).append(",")
							.append(location.getRssi3());
							
							map.put("nearbts", lbs.toString());
							map.put("key", Constant.KEYS);
							String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_LBS_URL,map);
							logger.info("jsonToString++" + jsonToString);
							if ("-1".equals(jsonToString)) {
								resultCode = Constant.FAIL_CODE;
							}else{
								JSONObject jsons = JSONObject.parseObject(jsonToString);
								String status = jsons.getString("status");   //回传状态,1表示成功
								if (status.equals("1")) {
									String results = jsons.getString("result"); //结果级
									JSONObject jsonResult = JSONObject.parseObject(results);
									String locationJson = jsonResult.containsKey("location") ? jsonResult
											.getString("location") : null;   //经纬度
									
											
									if (locationJson != null) {
										logger.info("locationJson++" + locationJson);
										String str[] = locationJson.split(",");
										info.setSerieNo(function.getWatchId());  //手表id
										info.setBelongProject(belongProject);
										info.setLocationType("0");
										info.setShowType("1");   //保证点事存在的
										info.setPage(0);
										info.setRows(1);
										info.setSort("id");
										info.setOrder("DESC");
										List<LocationInfo> mLocationList = mLocationInfoService.find(info);
										logger.info("mLocationList.size++" + mLocationList.size());
										int LocationId = 0;
										String date = "0";   //上一个点的定位时间
										boolean bool_is_update = true;
										if(mLocationList.size() > 0 && str != null){
											locationSb.append(str[1]).append(":").append(str[0]);
											LocationId = mLocationList.get(0).getId();
											date = ""+mLocationList.get(0).getUploadTime();
											
											double long1 = Double.parseDouble(str[0]);
											double lat1 = Double.parseDouble(str[1]);
											double long2 = Double.parseDouble(
													mLocationList.get(0).getLongitude());
											double lat2 = Double.parseDouble(
													mLocationList.get(0).getLatitude());
											/*bool_is_update = Constant.getDistance(lat1,
													long1, lat2, long2,Constant.EFFERT_DATA);*/
										}
										info.setSerieNo(function.getWatchId());
										info.setBattery(Integer.parseInt(battery));
										info.setLongitude(str[0]);
										info.setLatitude(str[1]);
										info.setChangeLongitude(str[0]);
										info.setChangeLatitude(str[1]);
										info.setAccuracy(10);
										info.setUploadTime(new Date());
										info.setFall(location.getAdorn());
										
										if(bool_is_update || CalculatorUtils.isSameDay(date)){
											info.setShowType("1");								
											info.setEndTime(new Date());
										}else{
											info.setShowType("0");								
											info.setEndTime(null);
										}
										mLocationInfoService.insert(info);
										if(info.getShowType().equals("0")){
											info.setId(LocationId);
											info.setUploadTime(null);
											info.setEndTime(new Date());
											
											mLocationInfoService.update(info);
										}
										resultCode = Constant.SUCCESS_CODE;
									}
								}else{
									result.setStatus("0");
								}
							}
							bool_location = true;
						}else if(function.getId().equals(WEIKE_POWERON)){
							StringBuffer familySb = new StringBuffer();
							Family family = new Family();
							Setting setting = new Setting();
							setting.setSerieNo(function.getWatchId());
							
							Parameters parameters = new Parameters();
							family.setSerieNo(function.getWatchId());
							List<Family> mFamily = mFamilyService.find(family);
							int length = mFamily.size();
							for(int i =0;i<length;i++){
								if(i == 3){
									parameters.setSrvNum(familySb.toString());
								}
								if(i != 0){
									familySb.append(",");
								}
								familySb.append(mFamily.get(i).getPhoneNumber());
							}
							parameters.setWhiteList(familySb.toString());
							
							familySb.delete(0, familySb.length());
							for(int i=0;i<length;i++){
	                            if(i != 0){
	                            	familySb.append("#");
								}
								familySb.append("FQ")
								        .append("~")
								        .append(mFamily.get(i).getPhoneNumber())
								        .append("*")
								        .append(mFamily.get(i).getNickName());
							}
							parameters.setVoice(familySb.toString());
							List<Setting> mListSet = mSettingService.find(setting);
							length = mListSet.size();
							for(int i=0;i<length;i++){
								parameters.setGpsStatus(mListSet.get(i).getGps_on());
								parameters.setGpsInterval(Integer.valueOf(mListSet.get(i).getLight()));
							}
							parameters.setDns("0");
							parameters.setGpsOff("18-24");
							parameters.setIp("0.0.0.0");					
							parameters.setTime(System.currentTimeMillis()/1000);						
							
							Body bodys = new Body();
							bodys.setParameters(parameters);
							response.setBody(bodys);
						}else if(function.getId().equals(WEIKE_WIFI)){
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("accesstype", "1");
							map.put("imei", function.getWatchId());
							map.put("key", Constant.KEYS);
							
							String mac = location.getMcc1();
							String rssi = location.getRssi1();
							String ssid = location.getSsid();
							String[] masc = CalculatorUtils.getSplitRegx(mac, "#");
							String[] rssis = CalculatorUtils.getSplitRegx(rssi, "#");
							String[] ssids = CalculatorUtils.getSplitRegx(ssid, "#");
							
							StringBuffer sbWifi = new StringBuffer();
							for(int i=0;i<masc.length;i++){
								sbWifi.append(masc[i]).append(",")
								.append(rssis[i]).append(",")
								.append(ssids[i]);
								if(i == 0){
									map.put("mmac", sbWifi.toString());   //单个的情况
								}
								if(i < masc.length - 1){
									sbWifi.append("|");
								}						
							}
							map.put("macs", sbWifi.toString());
							String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_LBS_URL,map);
							logger.info("jsonToString++" + jsonToString);
							if ("-1".equals(jsonToString)) {
								resultCode = Constant.FAIL_CODE;
							}else{
								JSONObject jsons = JSONObject.parseObject(jsonToString);
								String status = jsons.getString("status");   //回传状态,1表示成功
								if (status.equals("1")) {
									String results = jsons.getString("result"); //结果级
									JSONObject jsonResult = JSONObject.parseObject(results);
									String locationJson = jsonResult.containsKey("location") ? jsonResult
											.getString("location") : null;
									if (locationJson != null) {
										logger.info("locationJson++" + locationJson);
										String str[] = locationJson.split(",");
										info.setSerieNo(function.getWatchId());  //手表id
										info.setBelongProject(belongProject);
										info.setLocationType("0");
										info.setShowType("1");   //保证点事存在的
										info.setPage(0);
										info.setRows(1);
										info.setSort("id");
										info.setOrder("DESC");
										List<LocationInfo> mLocationList = mLocationInfoService.find(info);
										logger.info("mLocationList.size++" + mLocationList.size());
										int LocationId = 0;
										String date = "0";   //上一个点的定位时间
										boolean bool_is_update = true;
										if(mLocationList.size() > 0 && str != null){
											locationSb.append(str[1]).append(":").append(str[0]);
											LocationId = mLocationList.get(0).getId();
											date = ""+mLocationList.get(0).getUploadTime();
											
											double long1 = Double.parseDouble(str[0]);
											double lat1 = Double.parseDouble(str[1]);
											double long2 = Double.parseDouble(
													mLocationList.get(0).getLongitude());
											double lat2 = Double.parseDouble(
													mLocationList.get(0).getLatitude());
											/*bool_is_update = Constant.getDistance(lat1,
													long1, lat2, long2,Constant.EFFERT_DATA);*/
										}
										info.setSerieNo(function.getWatchId());
										info.setBattery(Integer.parseInt(battery));
										info.setLongitude(str[0]);
										info.setLatitude(str[1]);
										info.setChangeLongitude(str[0]);
										info.setChangeLatitude(str[1]);
										info.setAccuracy(10);
										info.setUploadTime(new Date());
										info.setFall(location.getAdorn());
										
										if(bool_is_update || CalculatorUtils.isSameDay(date)){
											info.setShowType("1");								
											info.setEndTime(new Date());
										}else{
											info.setShowType("0");								
											info.setEndTime(null);
										}
										int count = mLocationInfoService.insert(info);
										logger.info("count++" + count);
										if(info.getShowType().equals("0")){
											info.setId(LocationId);
											info.setUploadTime(null);
											info.setEndTime(new Date());
											
											mLocationInfoService.update(info);
										}
										resultCode = Constant.SUCCESS_CODE;
									}
								}else{
									result.setStatus("0");
								}
							}
							bool_location = true;
						}
					}else{
						resultCode = Constant.FAIL_CODE;  //设备不存在
					}				
					//bool_location = true;  //测试
					if(bool_location){     //定位数据多回传一个天气
						logger.info("bool_location++"+locationSb.length());
						Body body = new Body();
						Weather weather = new Weather();
						HashMap<String, String> weatherMap = new HashMap<String, String>();
						if(locationSb.length() > 0){
							weatherMap.put("key", Constant.WEATHER_KEY);
							weatherMap.put("location", locationSb.toString());
							weatherMap.put("language", "zh-Hans");
							
							weatherMap.put("unit", "c");
							String jsonToString = HttpRequest.sendGetToGaoDe(Constant.WEATHER_URL,weatherMap);
							logger.info("weather++" + jsonToString);
							if(!"-1".equals(jsonToString)){
								WeatherBean json = JSON.parseObject(jsonToString,WeatherBean.class);
								weather.setCurTemp(json.getResults().get(0).getNow().getTemperature());
								weather.setTemp("16~28");
								weather.setwType(json.getResults().get(0).getNow().getText());
								System.out.println();
							}
						}else{
							weather.setwType("晴天");
							weather.setTemp("16~28");
							weather.setCurTemp("20");
							
						}	
						weather.setWarning("0");
						body.setWeather(weather);
						response.setBody(body);
						/*Parameters parameters = new Parameters();
						parameters.setDns("0");
						parameters.setGpsInterval(30);
						parameters.setGpsOff("18-24");
						parameters.setGpsStatus("0");
						parameters.setIp("0.0.0.0");
						parameters.setSrvNum("13854255791,053258701936");
						parameters.setTime(System.currentTimeMillis());
						parameters.setVoice("XX~13854255791*嘿#XX~15806545205*哈#XX~15192050781*呵#XX~18553298658*哇#");
						parameters.setWhiteList("13854255791,15806545205,053258701936");
						
						Body bodys = new Body();
						bodys.setParameters(parameters);
						response.setBody(bodys);*/
						
					}
					logger.info("DeviceHandler="+(String)message.toString());
					sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
					sb.append(xstream.toXML(response));
					String resp = sb.toString().replace("__", "_");
					
					logger.info("回传="+resp);
					session.write(resp);
					session.close(true);
				}else if(temp.contains(D_POW)){
					JSONObject result = JSONObject.parseObject(temp);
					responseJson.put("RESP", D_POW+"_RE");
					StringBuffer sb = new StringBuffer();
					
					String id = result.getString("id");    //设备id
					String b_g = result.getString("b_g");
					String deviceImei = "0";
					
					DeviceActive deviceActive = new DeviceActive();
					Family family = new Family();
					Setting setting = new Setting();    //设置类
					
					deviceActive.setId(Integer.valueOf(id));
					deviceActive.setBelongProject(b_g);
					deviceActive.setDeviceDisable("1");   //保证激活状态
					List<DeviceActive> deviceList = mDeviceActiveService.find(deviceActive);
					
					if(deviceList.size() > 0){
						deviceImei = deviceList.get(0).getDeviceImei();
					}
					if(!deviceImei.equals("0")){
						family.setBelongProject(b_g);
						family.setSerieNo(deviceImei);
						family.setOrder("asc");
						family.setSort("id");
						List<Family> familyList = mFamilyService.find(family);
						int length = familyList.size();
						for(int i=0;i<length;i++){
							Family mtemp = familyList.get(i);
							if(i != 0){
								sb.append("#");
							}					
							sb.append(mtemp.getId()).append("@")
							.append(mtemp.getPhoneNumber()).append("@")
							.append(mtemp.getNickName()).append("@")
							.append(mtemp.getRelativeType());
						}
						if(length > 0){
							sb.append("#");
						}				
						responseJson.put("D_FN", sb.toString());						
						sb.delete(0, sb.length());
						
						setting.setBelongProject(b_g);
						setting.setSerieNo(deviceImei);
						List<Setting> mSettingList = mSettingService.find(setting);
						if(mSettingList.size() > 0){
							Setting mTempSetting = mSettingList.get(0);
							sb.append(6)
							.append("@").append(mTempSetting.getLight());
						}
						responseJson.put("D_S", sb.toString());
						sb.delete(0, sb.length());
						
						Clock clock = new Clock();
						clock.setDid(Integer.valueOf(id));
						clock.setOrder("asc");
						clock.setSort("id");
						List<Clock> mTempClockList = mClockService.find(clock);
						int cloLength = mTempClockList.size();
						for(int i=0;i<cloLength;i++){
							Clock tempClo = mTempClockList.get(i);
							if(i != 0){
								sb.append("#");
							}					
							sb.append(tempClo.getId()).append("@")
							.append(tempClo.getStatu()).append("@")
							.append(tempClo.getClock()).append("@")
							.append(tempClo.getType()).append("@")
							.append(tempClo.getRemainType());
						}
						if(cloLength > 0){
							sb.append("#");
						}
						responseJson.put("D_CL", sb.toString());
						responseJson.put("R_C", "1");
					}else{
						responseJson.put("R_C", "0");
					}								
				}
			}catch(Exception nullE){
				logger.error("异常报错", nullE);
				responseJson.put("R_C", "-2");
			}finally{
				session.write(responseJson.toString());
			}
			
		}
	}

}
