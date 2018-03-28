package com.jfservice.sys.client.handler.weike;

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

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jfservice.common.bean.alpha.request.ReqJsonData;
import com.jfservice.common.bean.weike.Common;
import com.jfservice.common.bean.weike.ResponseBean;
import com.jfservice.common.bean.weike.Result;
import com.jfservice.common.http.HttpRequest;
import com.jfservice.common.lang.CalculatorUtils;
import com.jfservice.common.lang.Constant;
import com.jfservice.sys.client.handler.alpha.DataBean;
import com.jfservice.sys.client.handler.impl.ClientMessageEventImpl;
import com.jfservice.sys.client.manager.ClientSessionManager;
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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ImmediationHandler extends ClientMessageEventImpl{

	private Logger logger = Logger.getLogger(ImmediationHandler.class);	
	
	@Autowired
	private ApplicationContext mApplicationContext;
	
	@Autowired
	private MediaService mMediaService;
	
	@Autowired
	private DeviceActiveService mDeviceActiveService;
	
	@Autowired
	private SettingService sService;
	
	@Autowired
	private FamilyService fService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private LocationService mLocationInfoService;
	
	private ClientSessionManager mClientSessionManager;  //客户端session的保存
	
	private static final String LK = "D_LK";          //链路保持
	private static final String LOGIN = "D_LOGIN";    //设备登陆
	private static final String VOICE = "D_VOICE";    //设备语音
	private static final String V_TEST = "D_V_TEST";  //测试服务器语音下行
	private static final String D_S_TEST = "D_S_TEST";//测试设备下行数据
	private static final String D_S = "D_S";//测试设备下行数据
	private static final String D_FN_TEST = "D_FN_TEST";//测试亲情号码下行数据
	private static final String D_FN = "D_FN";//测试亲情号码下行数据
	private static final String D_CL_TEST = "D_CL_TEST";  //测试闹钟
	private static final String D_FALL = "D_FALL";  //防脱落报警
	private static final String D_LOW = "D_LOW";    //低电报警
	private static final String D_LOCATION = "D_LOCATION";//定位
	
 	
	@Override
	public void handler(Object message, IoSession session) throws JSONException {
		
		
		// TODO Auto-generated method stub
		mClientSessionManager = (ClientSessionManager)mApplicationContext.getBean("clientSessionManager");
		
		ResponseBean rsp = (ResponseBean)mApplicationContext.getBean("resp");
		Result res = (Result)mApplicationContext.getBean("result");
		Common commom = (Common)mApplicationContext.getBean("common");
		res.setStatus("1");
		commom.setResult(res);
		rsp.setCommon(commom);
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(ResponseBean.class);
		
		if(message != null && session != null){
			final JSONObject response = new JSONObject();
			String req = "";		
			//String sub="";
			try{			
				
				DataBean dataBean = (DataBean) message;
				JSONObject result = JSONObject.parseObject(dataBean.getContent());
				
				
				/*Gson gson=new Gson();
				  logger.info("gson后");
				  logger.info("反序列化前");
			   //   ReqJsonData reqJsonData = gson.fromJson(reqString,ReqJsonData.class);
			      
			      JSONObject result = gson.toJson(dataBean.getContent());*/
				
				req = result.getString("REQ");
				if(req.equals(LK)){    //链路保持
					String did = result.getString("D_I");
					did = "d_"+did;
					session.setAttributeIfAbsent("id", did);  //每一个通道的id
					if(mClientSessionManager.getSessionId(did) != null){
						mClientSessionManager.removeSessionId(did);
					}
					mClientSessionManager.addSessionId(did, session);
					response.put("RESP",req+"_RE");
					response.put("R_C", "1");
				}else if(req.equals(LOGIN)){
					String deviceImei = result.getString("D_I");
					String b_g = result.getString("b_g");
					String f_w = result.getString("F_W");
					String p_m = result.getString("P_M");
					DeviceActive deviceActive = new DeviceActive();
					deviceActive.setDeviceImei(deviceImei);
					deviceActive.setBelongProject(b_g);
					
					List<DeviceActive> mList = mDeviceActiveService.find(deviceActive);
					if(mList.size() > 0){					
						int id = mList.get(0).getId();
						String sid = mList.get(0).getUserId();
						response.put("id", String.valueOf(id));
						response.put("sid", String.valueOf(sid));
						response.put("time", String.valueOf(System.currentTimeMillis()/1000));
						response.put("R_C", "1");	
						session.setAttributeIfAbsent("id", "d_"+id);  //每一个通道的id
						mClientSessionManager.addSessionId("d_"+id, session);
					}else{
						response.put("R_C", "0");
					}
					response.put("RESP",req+"_RE");
				}else if(req.equals(VOICE)){
					DeviceActive deviceActive = new DeviceActive();
					String msg = result.getString("MSG");
					String b_g = result.getString("b_g");
					String[] arr = CalculatorUtils.getSplitRegx(msg, "#");
					int id = Integer.valueOf(arr[0]);
					int sid = Integer.valueOf(arr[1]);    //给绑定者发消息
					String voiceContent = arr[2];
					String length = arr[3];
					String type = arr[4];      //预留字段,0表示单聊,1表示群聊
					
					deviceActive.setId(id);
					deviceActive.setDeviceDisable("1");  //保证激活
					List<DeviceActive> mDeviceList = mDeviceActiveService.find(deviceActive);
					if(mDeviceList.size() > 0){
						StringBuffer sb = new StringBuffer();
						
						DeviceActive mTempDevice = mDeviceList.get(0);
						String path = Constant.MEDIA_PATH+sid+System.getProperty("file.separator");
						String fileName = Constant.getUniqueCode(String.valueOf(sid)) + ".amr";
						Constant.createFileContent(path, fileName, Base64.decodeBase64(voiceContent));
						
						final Media media = new Media();
						media.setFromId(mTempDevice.getDeviceImei());
						media.setToId(String.valueOf(sid));
						media.setMsgContent(path + fileName);
						media.setSendType("0");
						media.setSendTime(new Date());
						media.setTimeLength(length);
						media.setBelongProject(b_g);
						//要给设备发送语音
						logger.info("需要调用通道的的uid="+sid);
						IoSession tempSession = mClientSessionManager.getSessionId("u_"+sid);
						
						JSONObject deviceSub = new JSONObject();
						
						sb.append(mTempDevice.getDeviceHead())
						.append("#").append(System.currentTimeMillis())
						.append("#").append(mTempDevice.getDeviceName())
						.append("#").append("2");
						deviceSub.put("SUB", "U_VOICE");
						deviceSub.put("MSG", msg);
						deviceSub.put("OTHER", sb.toString());
						
						if(tempSession != null){
							logger.info("sessionId为="+tempSession.getId());
							WriteFuture writeFuture = tempSession.write(deviceSub.toString());
							writeFuture.addListener(new IoFutureListener<IoFuture>() {

								@Override
								public void operationComplete(IoFuture future) {
									// TODO Auto-generated method stub
									if(((WriteFuture)future).isWritten()){   //发送成功
										logger.info("D_VOICE发送成功=");
										media.setSendType("1");
										response.put("R_C", "1");
									}else{
										logger.info("D_VOICE通道关闭=");
										media.setSendType("0");
										response.put("R_C", "-2");
									}
								}
							});
						}else{
							logger.info("获取的session为空");
							media.setSendType("0");
							response.put("R_C", "-2");
						}
						mMediaService.insert(media);
					}else{
						response.put("R_C", "0");
					}
					response.put("RESP",req+"_RE");
				}else if(req.equals(V_TEST)){
					String msg = "0";
					response.put("SUB", "D_VOICE");
					StringBuffer sb = new StringBuffer();
					
					String id = result.getString("id");
					String b_g = result.getString("b_g");
					Media media = new Media();
					media.setToId(id);    //发给谁
					media.setSendType("0"); //未发送状态
					media.setSort("id");  //按id
					media.setOrder("desc"); //降序
					List<Media> mediaList = mMediaService.find(media);
					if(mediaList.size() > 0){
						Media temp = mediaList.get(0);   //暂时第一条数据
						String content = temp.getMsgContent();
						String length = temp.getTimeLength();
						String sid = temp.getFromId();   //目前单聊的id是绑定者的id
						
						content = Base64.encodeBase64String(Constant.getContent(content));
						sb.append(id).append("#").append(sid).append("#")
						.append(content).append("#").append(length).append("#").append("0");
						msg = sb.toString();
					}
					response.put("MSG", msg);
					response.put("R_C", "1");
				}else if(req.equals(D_S_TEST)){
					response.put("SUB", "D_S");
					String type = result.getString("type");
					String responseType = "-1";
					if(type.equals("0")){
						responseType = "0";
					}else if(type.equals("1")){
						responseType = "1";
					}else if(type.equals("2")){
						responseType = "2@3";
					}else if(type.equals("3")){
						responseType = "3";
					}else if(type.equals("4")){
						responseType = "4";
					}else if(type.equals("5")){
						responseType = "5";
					}else if(type.equals("6")){
						responseType = "6@10";
					}else if(type.equals("7")){
						responseType = "7";
					}else if(type.equals("8")){
						responseType = "8";
					}
					response.put("type", responseType);
					response.put("R_C", "1");
				}else if(req.equals("D_S")){

					String deviceImei = result.getString("D_I");
					Setting vo=new Setting();
					vo.setSerieNo(deviceImei);
					//List<DeviceActive> mList = mDeviceActiveService.find(deviceActive);
					List<Setting> sList=sService.find(vo);
					if(sList.size() > 0){					
						String type = result.getString("type");
						String responseType = "-1";
						if(type.equals("0")){//远程关机
						String shutDown=sList.get(0).getShutdown();	
							responseType = shutDown;
						}else if(type.equals("1")){//驱蚊指令
							String qw=sList.get(0).getRepellent();
							responseType = qw;
						}else if(type.equals("2")){//爱心奖励
							String ax=sList.get(0).getHeart();
							responseType = "2@"+ax;
						}else if(type.equals("3")){///找宝贝
							String fb=sList.get(0).getSeac();
							responseType = fb;
						}else if(type.equals("4")){//监听
							String jt=sList.get(0).getListen();
							responseType = jt;
						}else if(type.equals("5")){//恢复出厂设置
							String cc=sList.get(0).getRest();
							responseType = cc;
						}else if(type.equals("6")){//gps开关
							String gps=sList.get(0).getGps_on();							
							responseType = "6@"+gps;
						}else if(type.equals("7")){//终端版本查询
							String bb=sList.get(0).getVeri();
							responseType = bb;
						}else if(type.equals("8")){//重启
							String cq=sList.get(0).getRes();
							responseType = cq;
						}
						
						int id = sList.get(0).getId();
						response.put("id", String.valueOf(id));
						response.put("R_C", "1");
						response.put("RESP",req+"_RE");
						response.put("type", responseType);
						//String sid = sList.get(0).getUserId();
						//response.put("sid", String.valueOf(sid));
						//response.put("time", String.valueOf(System.currentTimeMillis()/1000));
					//	response.put("R_C", "1");	
						//session.setAttributeIfAbsent("id", "d_"+id);  //每一个通道的id
						//mClientSessionManager.addSessionId("d_"+id, session);
					}else{
						response.put("R_C", "0");
					}
					response.put("RESP",req+"_RE");
				
				
					
				/*	String type = result.getString("type");
					String responseType = "-1";
					if(type.equals("0")){
						responseType = "0";
					}else if(type.equals("1")){
						responseType = "1";
					}else if(type.equals("2")){
						responseType = "2@3";
					}else if(type.equals("3")){
						responseType = "3";
					}else if(type.equals("4")){
						responseType = "4";
					}else if(type.equals("5")){
						responseType = "5";
					}else if(type.equals("6")){
						responseType = "6@10";
					}else if(type.equals("7")){
						responseType = "7";
					}else if(type.equals("8")){
						responseType = "8";
					}
					
					response.put("RESP",req+"_RE");
					response.put("type", responseType);
					response.put("R_C", "1");*/
				}else if(req.equals(D_FN)){

					String responseType = "-1";
					String deviceImei = result.getString("D_I");
					if(deviceImei.equals("")){
						response.put("R_C", "-2");
					}else{
					Family vo=new Family();
					vo.setSerieNo(deviceImei);
					List<Family> sList=fService.find(vo);
					StringBuffer sb = new StringBuffer();
					if(sList.size() > 0){
						for(int i=0;i<sList.size();i++){
						String r_t=sList.get(i).getRelativeType();
								String p_n=sList.get(i).getPhoneNumber();
								String n_n=sList.get(i).getNickName();
							/*if(i != 0){
								if(i != 0){
									sb.append("#");
								}
								}*/
								if(r_t.equals("0")){
									sb.append(0).append("@").append(p_n).append("@")
									.append(n_n);
									sb.append("#");
								}else if(r_t.equals("1")){
									if(i != 0){
										if(i != 0){
											sb.append("#");
										}
										}
									sb.append(1).append("@").append(p_n).append("@")
									.append(n_n);
								}
								/*sb.append(i).append("@")
								.append(p_n).append("@")
								.append(n_n).append("@")
								.append("0");*/
							
							//sb.append("#");
							
							/*if(r_t.equals(0)){
								sb.append(b)
							}else if(r_t.equals(1)){
								
							}*/
						}
						responseType = sb.toString();
						System.out.println("responseType---"+responseType);
						logger.info("responseType------------"+responseType);
						response.put("msg", responseType);
						response.put("R_C", "1");
						
					}else{
						response.put("R_C", "2");
					}
					}
					response.put("RESP",req+"_RE");
					
				}else if(req.equals(D_FN_TEST)){
					response.put("SUB", "D_FN");
					String responseType = "-1";
					StringBuffer sb = new StringBuffer();
					for(int i=0;i<3;i++){
						if(i != 0){
							sb.append("#");
						}					
						sb.append(i).append("@")
						.append("13000000000").append("@")
						.append("爸爸").append("@")
						.append("0");
					}
					sb.append("#");
					responseType = sb.toString();
					response.put("msg", responseType);
					response.put("R_C", "1");
				}else if(req.equals(D_CL_TEST)){
					response.put("SUB", "D_CL");
					String responseType = "-1";
					StringBuffer sb = new StringBuffer();
					for(int i=0;i<3;i++){
						if(i != 0){
							sb.append("#");
						}					
						sb.append("1").append("@")
						.append("0").append("@")
						.append("08:00").append("@")
						.append("-1").append("@")
						.append("2");
					}
					sb.append("#");
					responseType = sb.toString();
					response.put("msg", responseType);
					response.put("R_C", "1");
				}else if(req.equals(D_FALL)){
					String type = result.getString("TYPE");
					String id = result.getString("id");
					
					response.put("RESP",req+"_RE");
					response.put("R_C", "1");
				}else if(req.equals(D_LOW)){
					String type = result.getString("TYPE");
					String id = result.getString("id");
					
					response.put("RESP",req+"_RE");
					response.put("R_C", "1");
				}else if(req.equals(D_LOCATION)){
					boolean bool_location = false;
					LocationInfo info = new LocationInfo();      //定位数据保存	
					StringBuffer locationSb = new StringBuffer();
					String accesstype=result.getString("ACCESSTYPE");//1为GPS；2为基站定位
					String imei=result.getString("IMEI");//设备IMEI
					String belongProject=result.getString("b_g");//项目
					String battery=result.getString("BATTERY");//电池
					  
					   
					if(accesstype!=null&&!accesstype.equals("")){
						   if(accesstype.equals("1")){ //1为GPS
							   String lng=result.getString("LNG");//精度
							   String lat=result.getString("LAT");//纬度
							
							//   double[] data = CalculatorUtils.calculatorLonAndLat(location.getLon(), location.getLat());
								//先拿到经纬度的数组
						
							   //logger.info(TAG+",获取的经纬度分别为="+data[0]+","+data[1]);
							   logger.info("获取的经纬度分别为="+lng+","+lat);
								
								info.setBelongProject(belongProject);
								info.setSerieNo(imei);
								info.setPage(0);
								info.setRows(1);
								info.setSort("id");
								info.setOrder("DESC");
								List<LocationInfo> mLocationList = mLocationInfoService.find(info);
								//根据条件着信息
								int LocationId = 0;
								boolean bool_is_update = true;
								info.setBattery(Integer.parseInt(battery));
								info.setLongitude(lng);
								info.setLatitude(lat);
								info.setChangeLongitude(lng);
								info.setChangeLatitude(lat);
								info.setAccuracy(10);
								info.setLocationType("1");
								
								info.setUploadTime(new Date());
								info.setShowType("1"); // 都是显示的
								info.setEndTime(new Date());
								info.setFall("1");
								//String resultCodes = "{\"locations\":\"113.4582,23.1687\"}";    //调试
								String resultCodes = HttpRequest
											.sendGet(Constant.LOCATION_URL,
													"locations="
															+ lng
															+ ","
															+ lat
															+ "&coordsys=gps&output=json&key="+Constant.KEY);
								logger.info("resultCodes++" + resultCodes);
								if ("-1".equals(resultCodes)) {
									//result.setStatus("0");
									//resultCode = Constant.FAIL_CODE;
									response.put("RESP",req+"_RE");
									response.put("R_C", "0");
									info.setChangeLongitude("0");
									info.setChangeLatitude("0");
								}else{
									//result.setStatus("1");
									JSONObject object = JSONObject.parseObject(resultCodes);
								//	String locationJson = object.getString("locations");  //经纬度							
									
								//	String[] str = locationJson.split(",");
									if (lng!=null&&lat!=null&&!lng.equals("")&&!lat.equals("")) {
										locationSb.append(lng).append(":").append(lat);
										LocationId = mLocationList.get(0).getId();
                                       String a=LocationId+"";
										if(a==null&&a.equals("")){
											mLocationInfoService.insert(info);
											response.put("RESP",req+"_RE");
											response.put("R_C", "1");
										}
										double long1 = Double.parseDouble(lng);
										double lat1 = Double.parseDouble(lat);
										
										double long2 = Double.parseDouble(
												mLocationList.get(0).getLongitude());
										double lat2 = Double.parseDouble(
												mLocationList.get(0).getLatitude());
										
										info.setChangeLongitude(lng);
										info.setChangeLatitude(lat);
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
								//resultCode = Constant.SUCCESS_CODE;
								response.put("RESP",req+"_RE");
								response.put("R_C", "1");
								bool_location = true;
							
						   }else if(accesstype.equals("2")){//2为LBS
                                 String mcc=result.getString("MCC");//移动用户所属国家代码
                                 String mnc=result.getString("MNC");//移动网号中国移动0，联通1
                                 String lac=result.getString("LAC");//位置区域码
                                 String cellId=result.getString("CELLID");//基站小区编号
                                 String signal=result.getString("SIGNAL");//信号强度
                                 String smac=result.getString("SMAC");//信号强度
                                 
								StringBuffer lbs = new StringBuffer();
								lbs.append(mcc).append(",")
								.append(mnc).append(",")
								.append(lac).append(",")
								.append(cellId).append(",")
								.append(signal);
							logger.info("hahhahhhhhhhLBS"+lbs.toString());	
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("accesstype", "0");
								map.put("network", "GSM/EDGE");
								map.put("cdma", "0");  //非cdma卡
								map.put("imei", imei);  //手机imei号
								map.put("smac", smac);
								map.put("bts", lbs.toString());
								
								/*lbs.append("|");
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
								.append(location.getRssi3());*/
								
								map.put("nearbts", lbs.toString());
								map.put("key", Constant.KEYS);
								String jsonToString = HttpRequest.sendGetToGaoDe(Constant.LOCATION_LBS_URL,map);
								logger.info("jsonToString++" + jsonToString);
								if ("-1".equals(jsonToString)) {
									//resultCode = Constant.FAIL_CODE;
									response.put("RESP",req+"_RE");
									response.put("R_C", "0");
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
											info.setSerieNo(imei);  //手表id
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
											info.setSerieNo(imei);
											info.setBattery(Integer.parseInt(battery));
											info.setLongitude(str[0]);
											info.setLatitude(str[1]);
											info.setChangeLongitude(str[0]);
											info.setChangeLatitude(str[1]);
											info.setAccuracy(10);
											info.setUploadTime(new Date());
											info.setFall("1");
											
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
											//resultCode = Constant.SUCCESS_CODE;
											response.put("RESP",req+"_RE");
											response.put("R_C", "1");
										}
									}else{
										//result.setStatus("0");
									}
								}
								bool_location = true;
							
						   }else{
							   response.put("RESP",req+"_RE");
								response.put("R_C", "-2");//传参错误是accesstype不为1 或者2
						   }
						}else{
							response.put("RESP",req+"_RE");
							response.put("R_C", "-2");//传参错误是accesstype为空
						}
					}
								
			}catch(Exception nullE){
				logger.error("异常报错", nullE);
				response.put("R_C", "-2");
			}finally{
				session.write(response.toString());
			}
		}
	}

}
