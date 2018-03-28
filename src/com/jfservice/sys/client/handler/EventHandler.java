package com.jfservice.sys.client.handler;

import org.apache.mina.core.session.IoSession;

public interface EventHandler {

	public int getEventId();
	
	public void handler(Object message,IoSession session);
}
