/**
 * @Description: 
 *
 * @Title: CharsetDecoder.java
 * @Package com.joyce.mina.code
 * @Copyright: Copyright (c) 2014
 *
 * @author Comsys-LZP
 * @date 2014-3-19 上午11:35:49
 * @version V2.0
 */
package com.jfservice.charset.alpha;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.jfservice.sys.client.handler.alpha.DataBean;

/**
 * @Description: 字符解码
 * 
 * @ClassName: CharsetDecoder
 * @Copyright: Copyright (c) 2014
 * 
 * @author Comsys-LZP
 * @date 2014-9-19 上午11:35:49
 * @version V2.0
 */
public class CharsetDecoderII extends CumulativeProtocolDecoder {

	private static Logger logger = Logger.getLogger(CharsetDecoderII.class);

	private Charset charset; // 编码格式

	private String delimiter; // 文本分隔符

	private IoBuffer delimBuf; // 文本分割符匹配的变量
	
	// 定义常量值，作为每个IoSession中保存解码任务的key值
	// private static String CONTEXT = CharsetDecoderII.class.getName()
	// + ".context";

	private final AttributeKey CONTEXT = new AttributeKey(this.getClass(),
			"context");

	// 构造函数，必须指定Charset和文本分隔符
	public CharsetDecoderII(Charset charset, String delimiter) {
		this.charset = charset;
		this.delimiter = delimiter;
	}

	/*
	 * public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput
	 * out) throws Exception { Context ctx = getContext(session); if (delimiter
	 * == null || "".equals(delimiter)) { // 如果文本换行符未指定，使用默认值 delimiter =
	 * "\r\n"; } if (charset == null) { charset = Charset.forName("utf-8"); }
	 * decodeNormal(ctx, in, out); }
	 * 
	 * // 解码 private void decodeNormal(Context ctx, IoBuffer in,
	 * ProtocolDecoderOutput out) throws CharacterCodingException { //
	 * 取出未完成任务中已经匹配的文本换行符的个数 int matchCount = ctx.getMatchCount();
	 * 
	 * // 设置匹配文本换行符的IoBuffer变量 if (delimBuf == null) { IoBuffer tmp =
	 * IoBuffer.allocate(2).setAutoExpand(true); tmp.putString(delimiter,
	 * charset.newEncoder()); tmp.flip(); delimBuf = tmp; }
	 * 
	 * int oldPos = in.position(); // 解码的IoBuffer中数据的原始信息 int oldLimit =
	 * in.limit(); while (in.hasRemaining()) { // 变量解码的IoBuffer byte b =
	 * in.get(); if (delimBuf.get(matchCount) == b) { // 匹配第matchCount位换行符成功
	 * matchCount++; if (matchCount == delimBuf.limit()) { //
	 * 当前匹配到字节个数与文本换行符字节个数相同，匹配结束 int pos = in.position(); //
	 * 获得当前匹配到的position（position前所有数据有效） in.limit(pos); in.position(oldPos); //
	 * position回到原始位置 ctx.append(in); // 追加到Context对象未完成数据后面 in.limit(oldLimit);
	 * // in中匹配结束后剩余数据 in.position(pos); IoBuffer buf = ctx.getBuf();
	 * buf.flip(); buf.limit(buf.limit() - matchCount);// 去掉匹配数据中的文本换行符 try {
	 * out.write(buf.getString(ctx.getDecoder())); // 输出解码内容 } finally {
	 * buf.clear(); // 释放缓存空间 } oldPos = pos; matchCount = 0; } } else { //
	 * 如果matchCount==0，则继续匹配 // 如果matchCount>0，说明没有匹配到文本换行符的中的前一个匹配成功字节的下一个字节，
	 * // 跳转到匹配失败字符处，并置matchCount=0，继续匹配 in.position(in.position()-matchCount);
	 * matchCount = 0; // 匹配成功后，matchCount置空 } }
	 * 
	 * // 把in中未解码内容放回buf中 in.position(oldPos); ctx.append(in);
	 * 
	 * ctx.setMatchCount(matchCount); }
	 */
	// 从IoSession中获取Context对象
	private Context getContext(IoSession session) {
		Context ctx;
		ctx = (Context) session.getAttribute(CONTEXT);
		if (ctx == null) {
			ctx = new Context();
			session.setAttribute(CONTEXT, ctx);
		}
		return ctx;
	}

	public void dispose(IoSession session) throws Exception {

	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
	}

	// 内部类，保存IoSession解码时未完成的任务
	private class Context {
		private java.nio.charset.CharsetDecoder decoder;
		private IoBuffer buf; // 保存真实解码内容
		private int matchCount = 0; // 匹配到的文本换行符个数
		private int splitPos = 0;
		private int splitCount = 0;

		private Context() {
			decoder = charset.newDecoder();
			buf = IoBuffer.allocate(80).setAutoExpand(true);
		}

		// 重置
		public void reset() {
			matchCount = 0;
			splitPos = 0;
			splitCount = 0;
			decoder.reset();
		}

		// 追加数据
		public void append(IoBuffer in) {
			getBuf().put(in);
		}

		// ======get/set方法=====================
		public CharsetDecoder getDecoder() {
			return decoder;
		}

		public IoBuffer getBuf() {
			return buf;
		}

		public int getMatchCount() {
			return matchCount;
		}
        
		public int getSplitPos() {
			return splitPos;
		}
		
		public int getSplitCount() {
			return splitCount;
		}
		
		public void setMatchCount(int matchCount) {
			this.matchCount = matchCount;
		}
		
		public void setSplitPos(int splitPos) {
			this.splitPos = splitPos;
		}
		
		public void setSplitCount(int splitCount) {
			this.splitCount = splitCount;
		}
	} // end class Context;

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		logger.info("dodecode session is ==========================="
				+ session);
		int inRemain = in.remaining();//流中的chang'd
		logger.info("inRemain is =================="
				+ inRemain);
		if (inRemain < 4)
			return false;
		// 取出未完成任务中已经匹配的文本换行符的个数
		Context ctx = getContext(session);
		System.out.println(ctx);
		int matchCount = ctx.getMatchCount();
		int count = ctx.getSplitCount();
		int splitPos = ctx.getSplitPos();
		// 设置匹配文本换行符的IoBuffer变量
		if (delimBuf == null) {
			IoBuffer tmp = IoBuffer.allocate(2).setAutoExpand(true);
			tmp.putString(delimiter, charset.newEncoder());
			tmp.flip();
			delimBuf = tmp;
		}

		int oldPos = in.position(); // 解码的IoBuffer中数据的原始信息
		int oldLimit = in.limit();
		while (in.hasRemaining()) { // 变量解码的IoBuffer
			byte b = in.get();//当前的匹配符
			System.out.println("b------------"+b);
			if(b == 124) {
				count++;
				ctx.setSplitCount(count);
			}
			if(splitPos == 0) {
			if(count == 10) {
				splitPos = in.position();//取出最后一个竖线的位置
				logger.info("splitPos is ==========================="
						+ splitPos);
				ctx.setSplitPos(splitPos);
			}
			}
			byte test = delimBuf.get(matchCount);
			//byte test=10;
			System.out.println(test);
			System.out.println("1");
			if (test == b) { // 匹配第matchCount位换行符成功
				matchCount++;
				//logger.info("数据流中含有的字节表示="+b+",匹配第一个字符成功====="+ test +",对应的通道="+session);
				System.out.println("matchCount---------"+matchCount);
				System.out.println("delimBuf.limit()-------"+delimBuf.limit());
				if (matchCount == delimBuf.limit()) { // 当前匹配到字节个数与文本换行符字节个数相同，匹配结束
					//logger.info("匹配成功====="+ session);
					int pos = in.position(); // 获得当前匹配到的position（position前所有数据有效）
					logger.info("pos is ==========================="
							+ pos);
					in.limit(pos);
					in.position(oldPos); // position回到原始位置
					logger.info("oldPos is ==========================="
							+ oldPos);
					ctx.append(in); // 追加到Context对象未完成数据后面,把传上服务器的数据读到要追加的IoBuffer中
					in.limit(oldLimit); // in中匹配结束后剩余数据
					logger.info("oldLimit is ==========================="
							+ oldLimit);
					in.position(pos);
					IoBuffer buf = ctx.getBuf();   //存之前的数据
					buf.flip();  //相等于刷新缓冲区，把数据存入了
					buf.limit(buf.limit() - matchCount);// 去掉匹配数据中的文本换行符,把指针位置移动到匹配符之前
					int length = buf.limit();
					logger.info("length is ==========================="
							+ length);
					buf.limit(buf.position());   //读到限制的地方
					logger.info("limit is ==========================="
							+ buf.limit());
//					buf.limit(length);
					buf.position(0);
					try {
						DataBean data = new DataBean();
						buf.flip();
						buf.position(buf.limit());
						logger.info("splitPos2 is ==========================="
								+ splitPos);
						buf.limit(splitPos);
						//判断|符号
						CharsetDecoder tempCharsetDecoder = ctx.getDecoder();
						logger.info("tempCHarsetDecoder------"+tempCharsetDecoder);					
						String dString = buf.getString(tempCharsetDecoder);
						System.out.println("dString----------"+dString);
						logger.info("dodecode string is ==========================="
								+ dString+",对应通道="+session);
						buf.flip();
						buf.position(buf.limit());
						buf.limit(length);
						int p = buf.position();
						logger.info("p is ==========================="
								+ p);
						int l = buf.limit();
						logger.info("l is ==========================="
								+ l);
						byte[] voiceData = new byte[l-p];
						buf.get(voiceData);
						ctx.setSplitCount(0);
						ctx.setSplitPos(0);
						
//						dString = new String(voiceData);
						data.setContent(dString);
						logger.info("dString是-------"+dString);
						System.out.println("阿尔法alphaCharsetDecoderIIAAAAAAAAAAAAAAAA");
						data.setVoice(voiceData);
						String voice = new String(voiceData);
						logger.info("voiceData是--------"+voiceData);
						logger.info("voice是--------"+voice);
						out.write(data); // 输出解码内容，传输结束
						
						
					}catch(Exception e){
						logger.error("解码错误", e);
					}finally {
						buf.clear(); // 释放缓存空间
					}
					oldPos = pos;
					matchCount = 0;
				}
			} else {
				// 如果matchCount==0，则继续匹配
				// 如果matchCount>0，说明没有匹配到文本换行符的中的前一个匹配成功字节的下一个字节，
				// 跳转到匹配失败字符处，并置matchCount=0，继续匹配
				in.position(in.position() - matchCount);
				matchCount = 0; // 匹配成功后，matchCount置空
			}
		}
		// 把in中未解码内容放回buf中
		in.position(oldPos);
		ctx.append(in);
		ctx.setMatchCount(matchCount);
		return true;
	}

	public static byte[] ioBufferToByte(Object message) {
		if (!(message instanceof IoBuffer)) {
			return null;
		}
		IoBuffer ioBuffer = (IoBuffer) message;
		ioBuffer.flip();
		byte[] readByte = new byte[ioBuffer.limit()];
		try {
			ioBuffer.get(readByte);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return readByte;
	}
}
