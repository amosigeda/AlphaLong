package com.jfservice.sys.client.event;

import org.apache.mina.core.session.IoSession;
import org.springframework.context.ApplicationEvent;

public class ClientMessageEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7518520873317569418L;
	
	/**
	 * 业务处理类
	 */
	private Object message;
	
	/**
	 * 事件id
	 */
	private int eventId;
	
	/**
	 * session
	 */
	private IoSession ioSession;
	
	public ClientMessageEvent(){
		super("message");
	}
	public ClientMessageEvent(Object message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public IoSession getIoSession() {
		return ioSession;
	}
	public void setIoSession(IoSession ioSession) {
		this.ioSession = ioSession;
	}	
}
