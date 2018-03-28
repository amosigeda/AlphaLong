package com.jfservice.sys.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.FileSystem;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.VoiceStatus;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.SimpleBufferAllocator;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.integration.beans.SetEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.ServletContextScope;

import com.alibaba.fastjson.JSONObject;
import com.jfservice.common.bean.weike.Common;
import com.jfservice.common.bean.weike.ResponseBean;
import com.jfservice.common.bean.weike.Result;
import com.jfservice.common.lang.Constant;
import com.jfservice.common.lang.XmlUtils;
import com.jfservice.sys.client.core.ClientMessageNotification;
import com.jfservice.sys.client.event.ClientMessageEvent;
import com.jfservice.sys.client.handler.Jlt.JltHandler;
import com.jfservice.sys.client.handler.Jlt.JltUserHandler;
import com.jfservice.sys.client.handler.alpha.AlpUserHandler;
import com.jfservice.sys.client.handler.alpha.AlphaHandler;
import com.jfservice.sys.client.handler.alpha.DataBean;
import com.jfservice.sys.client.handler.weike.DeviceHandler;
import com.jfservice.sys.client.handler.weike.ImmediationHandler;
import com.jfservice.sys.client.handler.weike.UserHandler;
import com.jfservice.sys.client.manager.ClientSessionManager;
import com.jfservice.sys.deviceactiveinfo.model.DeviceActive;
import com.jfservice.sys.deviceactiveinfo.service.DeviceActiveService;
import com.jfservice.sys.media.model.Media;
import com.jfservice.sys.media.service.MediaService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Controller
@Scope("prototype")
public class ImmediationSerHandlerJlt extends IoHandlerAdapter {
	
	private final static String TAG = ImmediationSerHandlerJlt.class.getSimpleName();
	private Logger logger = Logger.getLogger(ImmediationSerHandlerJlt.class);
	/**
	 * 设备端和客户端
	 */
	private final static String DEVICE = "D_";
	private final static String USER = "U_";
	/**
	 * 客户协议
	 */
	private final static String WEIKE = "watch_id";    //威科
	
	private final static String ALPHA = "MG";          //阿尔法
	
	private final static String ALPHA_U = "AL_U";          //阿尔法用户
	
	private final static String JLT="4A 54";//     吉久通
	private final static String JL="JT";//     吉久通app
	@Autowired
	private ApplicationContext applicationContext;   //接收到客户端的消息进行处理
	
	@Autowired 
	private DeviceHandler deviceHandler;
	
	@Autowired 
	private JltUserHandler JltUserHandler;
//	private UserHandler userHanlder;
	
	@Autowired 
	private ImmediationHandler immediationHandler;
	
	@Autowired
	private AlphaHandler alphaHandler;
	
	@Autowired
	private AlpUserHandler alphaUserHandler;
	
	@Autowired
	private JltHandler jltHandler;
	
	@Autowired
	private ClientSessionManager mClientSessionManager;  //客户端session的保存
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("sessionCreated"+session.getId());
	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception{
		super.sessionClosed(session);
		logger.info("sessionClosed"+session.getId());
		if(mClientSessionManager.getCurrentSessionIdCount() != 0){   //说明有值
			String key = (String)session.getAttribute("id");   //先把保存的属性值拿出
			logger.info("sessionClosed====关闭sessionId="+session.getId()+"=key="+key);
			removeKey(key);
		}
	}
	
	@Override
	public void sessionIdle(final IoSession session,final IdleStatus status) throws Exception{
        super.sessionIdle(session, status);
        //session.close(true);
        if(status == IdleStatus.BOTH_IDLE){
        	/*WriteFuture writeFuture = session.write("heartbeat");
        	writeFuture.addListener(new IoFutureListener<IoFuture>() {

				@Override
				public void operationComplete(IoFuture future) {
					// TODO Auto-generated method stub
					if(((WriteFuture)future).isWritten()){   //发送成功
						String key = (String)session.getAttribute("id");
						logger.info("发送成功====sessionId="+session.getId()+"=key="+key);
					}else{
						//空闲,通讯不上,剔除session
						String key = (String)session.getAttribute("id");
						logger.info("sessionIdle====关闭sessionId="+session.getId()+"=key="+key);
						removeKey(key);
						session.close(true);
					}
				}
			});*/
        }else{
        	String key = (String)session.getAttribute("id");
			logger.info("sessionIdle====关闭isConnected="+session.getId()+"=key="+key);
			removeKey(key);
        }
        
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		StringBuffer sb = new StringBuffer();
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		cause.printStackTrace(printWriter);
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);		
		logger.error("异常编码" + sb.toString());
		String key = (String)session.getAttribute("id");
		logger.error("exceptionCaught异常sessionId"+session.getId()+",保存的key="+key);
		//removeKey(key);
		session.close(true);   //报异常时,删除客户端的登陆
		super.exceptionCaught(session, cause);
	}
		
	@Override
	public void messageReceived(IoSession session,Object message) throws Exception{		
	System.out.println("message---------------"+message);
//		DataBean temp = (DataBean)message;
		String temp =(String)message;
		ClientMessageEvent event = new ClientMessageEvent();
		System.out.println("吉久通messageReceived");
		ClientMessageNotification notification = (ClientMessageNotification)applicationContext.getBean("mainNotification");
		//logger.info("调试信息0="+applicationContext.getEnvironment().getProperty("log1"));
		//logger.info("调试信息1="+System.getProperty("tranWeb.root"));
		//logger.info("session的messageReceived收到的消息="+temp.getContent());
		//System.out.println("吉久通session的messageReceived"+temp.getContent());
		if(temp.contains(JLT)){  //设备端(我们自己拟定的)
			System.out.println("1234");
			notification.setEventHandler(jltHandler);
		}
		if(temp.contains(JL)){   //app端   temp中包含JL（即JT）
			System.out.println("APP---------");
			notification.setEventHandler(JltUserHandler);
		}
		event.setMessage(temp);
		event.setIoSession(session);
		
		applicationContext.publishEvent(event);
		
		//logger.info("messageReceived="+(String)message);
		
		//JSONObject object = JSONObject.parseObject((String)message);
		/*String reqeust = object.getString("request");
		if(reqeust.contains("DEVICE_YY")){
			String device_imei =  object.getString("device_imei");
			String msg_content =  object.getString("msg_content");
			String time_length =  object.getString("time_length");
			String b_g =  object.getString("b_g");
			StringBuffer sb = new StringBuffer()
			.append(device_imei).append(",")
			.append(msg_content).append(",")
			.append(time_length).append(",")
			.append(b_g);
			logger.info(sb.toString());
			
			DeviceActive deviceActive = new DeviceActive();
			deviceActive.setBelongProject(b_g);
			deviceActive.setDeviceImei(device_imei);
			List<DeviceActive> mList = deviceActiveService.find(deviceActive);
			if(mList.size() > 0){				
				String userId = mList.get(0).getUserId();	
				String path = Constant.MEDIA_PATH+userId;
				String fileName = Constant.getUniqueCode(String.valueOf(userId)) + ".amr";
				byte[] content = Base64.decodeBase64(msg_content);
				Constant.createFileContent(path, fileName, content);
				
				String[] server = getServerName().split(",");
				String url = "http://" +server[0] +":"+server[1];
				String downloadPath = Constant.SERVER+System.getProperty("file.separator")+Constant.MEDIA_DOWNLOAD_PATH
						              +userId
						              +System.getProperty("file.separator")+fileName;
				msg_content = Constant.getDownloadPath(url, downloadPath);
				
				Media media = new Media();
				media.setFromId(device_imei);
				media.setToId(userId);
				media.setMsgContent(msg_content);
				media.setSendType("0");
				media.setSendTime(new Date());
				media.setTimeLength(time_length);
				media.setBelongProject(b_g);
				
				mediaService.insert(media);
				json.put("resultCode",1);								
			}else{
				json.put("resultCode",0);
			}			
		}else if(reqeust.contains("DEVICE_LOGIN")){
			//String device_imei =  object.getString("device_imei");
			json.put("resultCode",1);
		}
		json.put("request","/DEVICE_SEND_MEDIA.do");
		session.write(json);*/
	}
	/**
	 * 发送成功回调
	 */
	@Override
	public void messageSent(IoSession session, Object message) {
		logger.info("发送成功="+session.getId()+"发送吉久通内容="+(String)message);
	}
	@Override
	public void inputClosed(IoSession session) throws Exception{
		super.inputClosed(session);
	}
	public String getServerName() throws Exception {
		StringBuffer serverName = new StringBuffer();
		Properties pros = new Properties();
		pros.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
		serverName.append(pros.getProperty("server"))
		          .append(",").append(pros.getProperty("port"))
		          .append(",").append(pros.getProperty("serverName"));
		return serverName.toString();
	}
	
	private void removeKey(String key){
		if(key != null){
			mClientSessionManager.removeSessionId(key);
		}
	}
}
