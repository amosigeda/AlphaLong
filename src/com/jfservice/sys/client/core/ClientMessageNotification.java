package com.jfservice.sys.client.core;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.jfservice.sys.client.event.ClientMessageEvent;
import com.jfservice.sys.client.handler.EventHandler;

public class ClientMessageNotification {

	private EventHandler eventHandler;
	
	private int notificationId;

	@Async
	@EventListener
	public void processClientMessageEvent(ClientMessageEvent event){
		eventHandler.handler(event.getMessage(),event.getIoSession());
	}
	
	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public EventHandler getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}
}
