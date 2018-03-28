   package jfservice;

import java.net.SocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.mina.core.filterchain.IoFilterChain;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.TransportMetadata;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.core.write.WriteRequestQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.jfservice.common.bean.weike.Common;
import com.jfservice.common.bean.weike.ResponseBean;
import com.jfservice.common.bean.weike.Result;
import com.jfservice.common.json.WeatherBean;
import com.jfservice.sys.client.core.ClientMessageNotification;
import com.jfservice.sys.client.event.ClientMessageEvent;
import com.jfservice.sys.client.handler.weike.DeviceHandler;
import com.jfservice.sys.client.handler.weike.UserHandler;
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
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserServiceImplTest{

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired 
	private DeviceHandler deviceHandler;
	
	@Autowired
	private DeviceActiveService mDeviceActiveService;
	
	@Autowired
	private LocationService mLocationInfoService;
	
	@Autowired
	private FamilyService mFamilyService;
	
	@Autowired
	private SettingService mSettingService;
	
	@Autowired
	private ClockService mClockService;
	
	/*@Test
	public void getFamilyList() {
		Family family = new Family();
		family.setUserId("3");
		List<Family> mList = mFamilyService.find(family);
		System.out.println("");
	}*/
	/*@Test
	public void getSettingList() {
		Setting setting = new Setting();
		setting.setBelongProject("1");
		setting.setSerieNo("111111111111119");
	    List<Setting> mList = mSettingService.find(setting);
		System.out.println("");
	}*/
	@Test
	public void getDeviceList() {
		Clock clock = new Clock();
		StringBuffer sb = new StringBuffer();
		
		clock.setDid(86);
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
		System.out.println(sb.toString());
	}
	/*
	@Test
	public void getLocation(){
		String location = "{\"status\":\"1\",\"info\":\"OK\",\"infocode\":\"1000\",\"result\":{\"type\":\"4\",\"location\":\"113.8373896,22.6247852\",\"radius\":\"550\",\"desc\":\"广东省 深圳市 宝安区 宝安大道 靠近万景创意大厦\",\"country\":\"中国\",\"province\":\"广东省\",\"city\":\"深圳市\",\"citycode\":\"0755\",\"adcode\":\"440306\",\"road\":\"宝安大道\",\"street\":\"草围村三巷\",\"poi\":\"万景创意大厦\"}}";
		JSONObject jsons = JSONObject.parseObject(location);
		
		String results = jsons.getString("result"); //结果级
		JSONObject jsonResult = JSONObject.parseObject(results);
		String locationJson = jsonResult.containsKey("location") ? jsonResult
				.getString("location") : null;   //经纬度
		if (locationJson != null) {
			String str[] = locationJson.split(",");
		}
	}*/
	
	public void getWeather(){
		String jsonToString = "{\"results\":[{\"location\":{\"id\":\"WS10730EM8EV\",\"name\":\"深圳\",\"country\":\"CN\",\"path\":\"深圳,深圳,广东,中国\",\"timezone\":\"Asia/Shanghai\",\"timezone_offset\":\"+08:00\"},\"now\":{\"text\":\"小雨\",\"code\":\"13\",\"temperature\":\"21\"},\"last_update\":\"2015-12-21T16:45:00+08:00\"}]}";
		WeatherBean json = JSON.parseObject(jsonToString,WeatherBean.class);
		//List<Weather> weaList = JSON.parseArray(jsonToString,Weather.class);
		System.out.println("天气情况="+json.getResults().get(0).getNow().getText());
		System.out.println("温度="+json.getResults().get(0).getNow().getTemperature());
	}
	public void getPermissionList() {
		ClientMessageEvent message = new ClientMessageEvent();
		
		ClientMessageNotification notification = (ClientMessageNotification)applicationContext.getBean("mainNotification");
		notification.setEventHandler(deviceHandler);	

		ResponseBean test = (ResponseBean)applicationContext.getBean("resp");
		Result result = (Result)applicationContext.getBean("result");
		Common commom = (Common)applicationContext.getBean("common");
		result.setStatus("1");
		
		commom.setResult(result);
		test.setCommon(commom);
		XStream xstream = new XStream(new DomDriver());
		xstream.processAnnotations(ResponseBean.class);
		xstream.useAttributeFor(Result.class, "status");
		StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		
		sb.append("<page><common><function id=\"PowerOn\" watch_id=\"000000000000001\" version=\"1\" Verification=\"sdsd\"/></common><body><Battery Content=\"90\"/></body></page>");
		//sb.append("<page><common><function id=\"SubmitWIFI\" watch_id=\"000000000000001\" version=\"1\" Verification=\"19CA1\"/></common><body><Location Time=\"-2147483648\"  adorn=\"0\" Mac1=\"B0:D5:9D:44:8E:59#64:D9:54:6A:07:0C#BC:0F:2B:81:3D:B3#22:F0:2F:76:AF:CB#\" Rssi1=\"-33#-67#-89#-94#\" ssid=\"西部硅谷#baidu21712#midea_ac_0896#这不是你的wifi#\" Battery=\"100\"/> </body></page>");
		String tests = sb.toString();
		message.setMessage(tests);
		message.setIoSession(new IoSession() {
			
			@Override
			public WriteFuture write(Object message, SocketAddress destination) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public WriteFuture write(Object message) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void updateThroughput(long currentTime, boolean force) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void suspendWrite() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void suspendRead() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setCurrentWriteRequest(WriteRequest currentWriteRequest) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object setAttributeIfAbsent(Object key, Object value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object setAttributeIfAbsent(Object key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object setAttribute(Object key, Object value) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object setAttribute(Object key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object setAttachment(Object attachment) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void resumeWrite() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void resumeRead() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean replaceAttribute(Object key, Object oldValue, Object newValue) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean removeAttribute(Object key, Object value) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object removeAttribute(Object key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ReadFuture read() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isWriterIdle() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isWriteSuspended() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isSecured() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isReaderIdle() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isReadSuspended() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isIdle(IdleStatus status) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isConnected() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isClosing() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isBothIdle() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public double getWrittenMessagesThroughput() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getWrittenMessages() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getWrittenBytesThroughput() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getWrittenBytes() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getWriterIdleCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public WriteRequestQueue getWriteRequestQueue() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public TransportMetadata getTransportMetadata() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public SocketAddress getServiceAddress() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IoService getService() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getScheduledWriteMessages() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getScheduledWriteBytes() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public SocketAddress getRemoteAddress() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getReaderIdleCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getReadMessagesThroughput() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getReadMessages() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getReadBytesThroughput() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getReadBytes() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public SocketAddress getLocalAddress() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getLastWriterIdleTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getLastWriteTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getLastReaderIdleTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getLastReadTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getLastIoTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getLastIdleTime(IdleStatus status) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getLastBothIdleTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getIdleCount(IdleStatus status) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public long getId() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public IoHandler getHandler() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IoFilterChain getFilterChain() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public WriteRequest getCurrentWriteRequest() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getCurrentWriteMessage() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getCreationTime() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public IoSessionConfig getConfig() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public CloseFuture getCloseFuture() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getBothIdleCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Set<Object> getAttributeKeys() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(Object key, Object defaultValue) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(Object key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttachment() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean containsAttribute(Object key) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public CloseFuture close(boolean immediately) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public CloseFuture close() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		applicationContext.publishEvent(message);
		ResponseBean ss = (ResponseBean)xstream.fromXML(tests);
		System.out.println("");
		
		/*DeviceActive mList = mDeviceActiveService.findById(6);
		LocationInfo info = new LocationInfo();
		info.setBelongProject("1");
		info.setSerieNo("000000000000001");
		info.setPage(0);
		info.setRows(1);
		info.setSort("id");
		info.setOrder("DESC");
		List<LocationInfo> mList = mLocationInfoService.find(info);
		PageInfo<LocationInfo> pageInfo = new PageInfo<LocationInfo>(mList);
		List<LocationInfo> temp = pageInfo.getList();
		DeviceActive deviceActive = new DeviceActive();
		deviceActive.setPage(0);
		deviceActive.setRows(1);
		deviceActive.setSort("id");
		deviceActive.setOrder("DESC");
		deviceActive.setDeviceImei("000000000000001");
		deviceActive.setBelongProject("1");*/
		//List<DeviceActive> mList = mDeviceActiveService.find(deviceActive);
		//System.out.println("dfdf="+mList.get(0).getLatitude());		
	}
}
