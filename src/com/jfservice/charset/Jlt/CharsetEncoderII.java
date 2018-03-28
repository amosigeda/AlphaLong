/**
 * @Description: 
 *
 * @Title: CharsetEncoder.java
 * @Package com.joyce.mina.code
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-3-19 上午11:48:53
 * @version V2.0
 */
package com.jfservice.charset.Jlt;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;

import com.jfservice.sys.client.handler.alpha.DataBean;

/**
 * @Description: 字符编码
 *
 * @ClassName: CharsetEncoder
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-wtc
 * @date 2016-5-12 上午12:15:53
 * @version V2.0
 */
public class CharsetEncoderII implements ProtocolEncoder {
	private static Logger logger = Logger.getLogger(CharsetEncoderII.class);
	
	private Charset charset; // 编码格式

	private String delimiter; // 文本分隔符

	public CharsetEncoderII(Charset charset, String delimiter) {
		this.charset = charset;
		this.delimiter = delimiter;
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		if (delimiter == null || "".equals(delimiter)) { // 如果文本换行符未指定，使用默认值
			delimiter = LineDelimiter.CRLF.getValue();
		}
		if (charset == null) {
			charset = Charset.forName("utf-8");
		}
System.out.println("CharsetEncoderII--I----------"+message);
		//DataBean mDataBean = (DataBean)message;
		String value = (String)message;
		logger.info("in CharsetEncoderII encode value is ========================"+value);
		IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);	
		logger.info(message);
		buf.putString(value, charset.newEncoder()); // 真实数据
	//	buf.putString(value, Charset.forName("utf-8").newEncoder()); // 真实数据
		/*if(mDataBean.getVoice() != null && mDataBean.getVoice().length > 0){
			logger.info("下发的数据为=");
			buf.put(mDataBean.getVoice()); 
		}*/
		buf.putString(delimiter, charset.newEncoder()); // 文本换行符
		buf.flip();
		out.write(buf);
	}

	public void dispose(IoSession session) throws Exception {
	}

}
