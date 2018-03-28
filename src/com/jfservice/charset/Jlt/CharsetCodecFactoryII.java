package com.jfservice.charset.Jlt;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;

import com.jfservice.charset.Jlt.CharsetDecoderII;
import com.jfservice.charset.Jlt.CharsetEncoderII;

public class CharsetCodecFactoryII implements ProtocolCodecFactory {
	private CharsetEncoderII encoder;
	private CharsetDecoderII decoder;
	
	public CharsetCodecFactoryII(){
		this(Charset.forName("utf-8"));
	}
	public CharsetCodecFactoryII(Charset charset) {
		//LineDelimiter.CRLF.getValue()
		decoder = new CharsetDecoderII(charset,LineDelimiter.CRLF.getValue());
		System.out.println("decoder----------"+decoder);
		encoder = new CharsetEncoderII(charset,LineDelimiter.CRLF.getValue());		
	   System.out.println("encoder----------"+encoder);
	}

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}

}
