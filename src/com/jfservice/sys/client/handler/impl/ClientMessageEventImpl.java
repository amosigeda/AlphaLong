package com.jfservice.sys.client.handler.impl;

import com.jfservice.sys.client.handler.EventHandler;

public abstract class ClientMessageEventImpl implements EventHandler {

	private int eventId;
			
	public void setEventId(int eventId){
		this.eventId = eventId;
	}
	@Override
	public int getEventId() {
		// TODO Auto-generated method stub
		return eventId;
	}
}
