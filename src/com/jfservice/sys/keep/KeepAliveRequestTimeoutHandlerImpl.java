package com.jfservice.sys.keep;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class KeepAliveRequestTimeoutHandlerImpl implements KeepAliveRequestTimeoutHandler {
	private Logger logger = Logger.getLogger(KeepAliveRequestTimeoutHandlerImpl.class);	
	/**
     * @see org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler心跳超时处理
     * @author Herman.Xiong
     */
	@Override
    public void keepAliveRequestTimedOut(KeepAliveFilter filter,
            IoSession session) throws Exception {
    	logger.info("服务器端心跳包发送超时处理(即长时间没有发送（接受）心跳包)---关闭当前长连接");
        CloseFuture closeFuture = session.close(true);
        closeFuture.addListener(new IoFutureListener<IoFuture>() {
            public void operationComplete(IoFuture future) {
                if (future instanceof CloseFuture) {
                    ((CloseFuture) future).setClosed();
                    logger.info("sessionClosed CloseFuture setClosed-->"+ future.getSession().getId());
                }
            }
        });
    }

}
