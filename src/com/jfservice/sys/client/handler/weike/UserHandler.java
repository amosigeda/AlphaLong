package com.jfservice.sys.client.handler.weike;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.jfservice.common.lang.CalculatorUtils;
import com.jfservice.common.lang.Constant;
import com.jfservice.sys.client.handler.alpha.DataBean;
import com.jfservice.sys.client.handler.impl.ClientMessageEventImpl;
import com.jfservice.sys.client.manager.ClientSessionManager;
import com.jfservice.sys.clock.model.Clock;
import com.jfservice.sys.clock.service.ClockService;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;
import com.jfservice.sys.deviceactiveinfo.service.DeviceActiveService;
import com.jfservice.sys.family.model.Family;
import com.jfservice.sys.family.service.FamilyService;
import com.jfservice.sys.media.model.Media;
import com.jfservice.sys.media.service.MediaService;
import com.jfservice.sys.setting.model.Setting;
import com.jfservice.sys.setting.service.SettingService;

public class UserHandler extends ClientMessageEventImpl{

	private final static String TAG = UserHandler.class.getSimpleName();
	private Logger logger = Logger.getLogger(UserHandler.class);
	
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
	
	private ClientSessionManager mClientSessionManager;  //客户端session的保存
	/**
	 * 客户协议
	 */
	private final static String U_LK = "U_LK";
	private final static String U_VOICE = "U_VOICE";
	private final static String U_LOGIN = "U_LOGIN";
	private final static String U_FN = "U_FN";
	private final static String U_S = "U_S";
	private final static String U_CL = "U_CL";
	@Override
	public void handler(Object message, IoSession session) {
		// TODO Auto-generated method stub
		mClientSessionManager = (ClientSessionManager)mApplicationContext.getBean("clientSessionManager");
		
		if(message != null && session != null){
			final JSONObject response = new JSONObject();
			String req = "";
			try{
				DataBean dataBean = (DataBean) message;
				JSONObject result = JSONObject.parseObject(dataBean.getContent());
				req = result.getString("REQ");
				String uid = result.containsKey("U_I")?result.getString("U_I"):"0";
				String did = result.containsKey("D_I")?result.getString("D_I"):"0";
				String b_g = result.getString("b_g");
				logger.info("请求接口="+req+",uid="+uid+",did="+did+",b_g="+b_g);
				response.put("RESP",req+"_RE");
				if(req.equals(U_LK)){    //链路保持
					uid = "u_"+uid;
					String tempUid = (String)session.setAttributeIfAbsent("id", uid);  //格式:u_+用户id,若有值,则获取,否则set进去
			        logger.info("链路接口的sessionId="+session.getId());
			        logger.info("保存的="+tempUid);
			        if(mClientSessionManager.getSessionId(uid) != null){
						mClientSessionManager.removeSessionId(uid);
					}
					mClientSessionManager.addSessionId(uid, session);
					response.put("R_C", "1");
				}else if(req.equals(U_VOICE)){  //语音接口
					String msg = result.getString("MSG");
					String[] arr = CalculatorUtils.getSplitRegx(msg, "#");
					int id = Integer.valueOf(arr[0]);
					int sid = Integer.valueOf(arr[1]);
					String voiceContent = arr[2];
					String length = arr[3];
					String type = arr[4];      //预留字段,0表示单聊,1表示群聊
					if(id != 0){
						DeviceActive deviceActive = mDeviceActiveService.findById(id);
						if(deviceActive != null){
							String path = Constant.MEDIA_PATH+sid+System.getProperty("file.separator");
							String fileName = Constant.getUniqueCode(String.valueOf(sid)) + ".amr";
							Constant.createFileContent(path, fileName, Base64.decodeBase64(voiceContent));
							
							final Media media = new Media();
							media.setFromId(String.valueOf(sid)); //来自哪个用户
							media.setToId(String.valueOf(id));   //发给哪个设备
							media.setMsgContent(path + fileName);
							media.setSendType("0");   //未发送
							media.setSendTime(new Date());
							media.setTimeLength(length);
							media.setBelongProject(b_g);
							
							//要给设备发送语音
							IoSession tempSession = mClientSessionManager.getSessionId("d_"+id);
							JSONObject deviceSub = new JSONObject();
							
							deviceSub.put("SUB", "D_VOICE");
							deviceSub.put("MSG", msg);
							if(tempSession != null){
								WriteFuture writeFuture = tempSession.write(deviceSub.toString());
								writeFuture.addListener(new IoFutureListener<IoFuture>() {

									@Override
									public void operationComplete(IoFuture future) {
										// TODO Auto-generated method stub
										if(((WriteFuture)future).isWritten()){   //发送成功
											logger.info("U_VOICE发送成功=");
											media.setSendType("1");
											response.put("R_C", "1");
										}else{
											logger.info("U_VOICE通道关闭=");
											media.setSendType("0");
											response.put("R_C", "-2");
										}
									}
								});
							}else{
								media.setSendType("0");
								response.put("R_C", "-2");
							}
							mMediaService.insert(media);
						}else{
							response.put("R_C", "0");
						}
					}else{
						response.put("R_C", "-3");
					}
						
				}else if(req.equals(U_FN)){
					Family tempFamily = new Family();
					String msg = result.getString("MSG");   //亲情号码消息
					
					tempFamily.setUserId(uid);
					tempFamily.setBelongProject(b_g);
					mFamilyService.delete(tempFamily);     //全部删除
					
					DeviceActive deviceActive = mDeviceActiveService.findById(Integer.valueOf(did));
					String deviceImei = "";
					if(deviceActive != null){
						deviceImei = deviceActive.getDeviceImei();
					}
					if(!msg.equals("")){
						String type[] = CalculatorUtils.getSplitRegx(msg, "\\*");
						String msgContent[] = CalculatorUtils.getSplitRegx(type[1], "#");
						int length = msgContent.length;
						for(int i=0;i<length;i++){
							Family family = new Family();
							String tempContent[] = CalculatorUtils.getSplitRegx(msgContent[i], "@");
							family.setSerieNo(deviceImei);
							family.setPhoneNumber(tempContent[1]);
							family.setNickName(tempContent[2]);
							family.setRelativeType(tempContent[3]);
							family.setStatus("1");
							family.setAddTime(new Date());
							family.setUserId(uid);
							family.setBelongProject(b_g);
							
							mFamilyService.insert(family);
						}
						msg = msg + "#";
					}
					
					//要给设备发送语音
					IoSession tempSession = mClientSessionManager.getSessionId("d_"+did);
					JSONObject deviceSub = new JSONObject();
					deviceSub.put("id", did);
					deviceSub.put("b_g", b_g);
					deviceSub.put("MSG", msg);
					deviceSub.put("SUB", "D_FN");
					if(tempSession != null){
						logger.info("sessionId为="+tempSession.getId());
						WriteFuture writeFuture = tempSession.write(deviceSub.toString());
						writeFuture.addListener(new IoFutureListener<IoFuture>() {

							@Override
							public void operationComplete(IoFuture future) {
								// TODO Auto-generated method stub
								if(((WriteFuture)future).isWritten()){   //发送成功
									logger.info("U_FN发送成功=");
									response.put("R_C", "1");
								}else{
									logger.info("U_FN通道关闭=");
									response.put("R_C", "-2");
								}
							}
						});
					}else{
						response.put("R_C", "-2");
					}
				}else if(req.equals(U_LOGIN)){
					String po = result.getString("P_O");
					String pv = result.getString("P_V");
					String av = result.getString("A_V");
					
					Clock clock = new Clock();
					StringBuffer sb = new StringBuffer();
					
					clock.setDid(Integer.valueOf(did));
					List<Clock> mTempClockList = mClockService.find(clock);
					int length = mTempClockList.size();
					for(int i=0;i<length;i++){
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
					response.put("R_C", "1");
					response.put("MSG", sb.toString());
				}else if(req.equals(U_S)){
					String type = result.getString("type");
					String d_im = result.getString("D_IM");
					
					Setting setting = new Setting();
					setting.setBelongProject(b_g);
					setting.setSerieNo(d_im);
					
					String responseType = "-1";
					if(type.equals("0")){
						setting.setShutdown("1");
						responseType = "0";
					}else if(type.equals("1")){
						setting.setRepellent("1");
						responseType = "1";
					}else if(type.contains("2")){
						setting.setHeart(type.split("@")[1]);
						responseType = "2@3";
					}else if(type.equals("3")){
						setting.setSeac("1");
						responseType = "3";
					}else if(type.equals("4")){
						setting.setListen("1");
						responseType = "4";
					}else if(type.equals("5")){
						setting.setRest("1");
						responseType = "5";
					}else if(type.contains("6")){
						String p_l = type.split("@")[1];
						setting.setLight(p_l);
						if(p_l.equals("0")){
							setting.setGps_on("0");
						}else{
							setting.setGps_on("1");
						}
						responseType = "6@10";
					}else if(type.equals("7")){
						setting.setVeri("1");
						responseType = "7";
					}else if(type.equals("8")){
						setting.setRes("1");
						responseType = "8";
					}
					mSettingService.update(setting);
					//要给设备发送语音
					IoSession tempSession = mClientSessionManager.getSessionId("d_"+did);
					JSONObject deviceSub = new JSONObject();
					deviceSub.put("type", type);
					deviceSub.put("SUB", "D_S");
					if(tempSession != null){
						WriteFuture writeFuture = tempSession.write(deviceSub.toString());
						writeFuture.addListener(new IoFutureListener<IoFuture>() {

							@Override
							public void operationComplete(IoFuture future) {
								// TODO Auto-generated method stub
								if(((WriteFuture)future).isWritten()){   //发送成功
									logger.info("U_S发送成功=");
									response.put("R_C", "1");
								}else{
									logger.info("U_S通道关闭=");
									response.put("R_C", "-2");
								}
							}
						});
					}else{
						response.put("R_C", "-2");
					}
					
				}else if(req.equals(U_CL)){
					String msg = result.getString("MSG");
					//要给设备发送闹钟
					Clock clock = new Clock();
					clock.setDid(Integer.valueOf(did));
					mClockService.delete(clock);    //删除这个设备De闹钟
					
					if(!msg.equals("")){
						String type[] = CalculatorUtils.getSplitRegx(msg, "\\*");
						String msgContent[] = CalculatorUtils.getSplitRegx(type[1], "#");
						int length = msgContent.length;
						for(int i=0;i<length;i++){
							Clock tempClock = new Clock();
							String tempContent[] = CalculatorUtils.getSplitRegx(msgContent[i], "@");
							tempClock.setDid(Integer.valueOf(did));
							tempClock.setStatu(tempContent[1]);
							tempClock.setClock(tempContent[2]);
							tempClock.setType(Integer.valueOf(tempContent[3]));
							tempClock.setRemainType(tempContent[4]);
							
							mClockService.insert(tempClock);
						}
						msg = msg + "#";
					}
					IoSession tempSession = mClientSessionManager.getSessionId("d_"+did);
					JSONObject deviceSub = new JSONObject();
					deviceSub.put("id", did);
					deviceSub.put("b_g", b_g);
					deviceSub.put("MSG", msg);
					deviceSub.put("SUB", "D_CL");	
					if(tempSession != null){
						WriteFuture writeFuture = tempSession.write(deviceSub.toString());
						writeFuture.addListener(new IoFutureListener<IoFuture>() {

							@Override
							public void operationComplete(IoFuture future) {
								// TODO Auto-generated method stub
								if(((WriteFuture)future).isWritten()){   //发送成功
									logger.info("U_CL发送成功=");
									response.put("R_C", "1");
								}else{
									logger.info("U_CL通道关闭=");
									response.put("R_C", "-2");
								}
							}
						});
					}else{
						response.put("R_C", "-2");
					}
				}
			}catch(Exception nullE){
				logger.error("异常报错", nullE);
				response.put("R_C", "-3");
			}finally{
				session.write(response.toString());
			}
		}
	}
}
