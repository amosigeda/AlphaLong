package com.jfservice.sys.keep;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.jfservice.sys.client.handler.weike.ImmediationHandler;

public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{
	
	private Logger logger = Logger.getLogger(KeepAliveMessageFactoryImpl.class);	
	//心跳包内容
    private static final String HEARTBEATREQUEST = "heartbeat";
    private static final String HEARTBEATRESPONSE = "heartBeatResponse";
    
	/**
     * @see 返回给客户端的心跳包数据 return 返回结果才是客户端收到的心跳包数据
     * @author Herman.Xiong
     */
    public Object getRequest(IoSession session) {
        return HEARTBEATREQUEST;
    }

    /**
     * @see 接受到的客户端数据包
     * @author Herman.Xiong
     */
    public Object getResponse(IoSession session, Object request) {
        return request;
    }

    /**
     * @see 判断是否是客户端发送来的的心跳包此判断影响 KeepAliveRequestTimeoutHandler实现类判断是否心跳包接收超时(来自客户端的请求)
     * @author Herman.Xiong
     */
    public boolean isRequest(IoSession session, Object message) {
        if(message.equals(HEARTBEATRESPONSE)){
        	logger.info("接收到客户端心数据包引发心跳事件                 心跳数据包是》》" + message);
	        return true;
	    }
        return false;
    }

    /**
     * @see  判断发送信息是否是心跳数据包此判断影响 KeepAliveRequestTimeoutHandler实现类 判断是否心跳包发送超时
     * @author Herman.Xiong
     */
    public boolean isResponse(IoSession session, Object message) {
        if(message.equals(HEARTBEATREQUEST)){
            logger.info("服务器发送数据包中引发心跳事件: " + message);
            return true;
        }
        return false;
    }

}
