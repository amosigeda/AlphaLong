/**
 * @Description: 
 *
 * @Title: CharsetCodecFactory.java
 * @Package com.joyce.mina.code.factory
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-3-19 上午11:34:14
 * @version V2.0
 */
package com.jfservice.charset.weike;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;



/**
 * @Description: 字符编码、解码工厂类，编码过滤工厂
 *
 * @ClassName: CharsetCodecFactory
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-3-19 上午11:34:14
 * @version V2.0
 */
public class CharsetCodecFactoryII implements ProtocolCodecFactory {
	private CharsetEncoderII encoder;
	private CharsetDecoderII decoder;
	
	public CharsetCodecFactoryII(){
		this(Charset.forName("utf-8"));
	}
	public CharsetCodecFactoryII(Charset charset) {
		//LineDelimiter.CRLF.getValue()
		decoder = new CharsetDecoderII(charset,LineDelimiter.CRLF.getValue());
		encoder = new CharsetEncoderII(charset,LineDelimiter.CRLF.getValue());		
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
