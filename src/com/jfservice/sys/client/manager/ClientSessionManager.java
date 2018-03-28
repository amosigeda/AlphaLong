package com.jfservice.sys.client.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

public final class ClientSessionManager {
	/**
	 * 管理设备端session的id
	 */
	private final ConcurrentMap<String, IoSession> managerSession = new ConcurrentHashMap<String, IoSession>();
	
	public ConcurrentMap<String, IoSession> getManagerSession() {
		return managerSession;
	}
	
	public int getCurrentSessionIdCount() {
		return managerSession.size();
	}
	/**
	 * 用户id和IoSession关联
	 * @param key
	 * @param id
	 */
	public void addSessionId(String key, IoSession ioSession){
		managerSession.putIfAbsent(key, ioSession);
	}
	/**
	 * 移除key
	 * @param key
	 */
	public void removeSessionId(String key){
		managerSession.remove(key);
	}
	/**
	 * 通过key,获取到session通道
	 * @param key
	 */
	public IoSession getSessionId(String key){
		return managerSession.get(key);
	}
}
