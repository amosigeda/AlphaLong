package com.jfservice.common.lang;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlUtils {

	public static void testXStream(){
		/*XStream xstream=new XStream(new DomDriver());
		xstream.alias("PERSON", Person.class);
		xstream.alias("ADDRESS",Address.class);
		xstream.alias("PHONENUMBER", PhoneNumber.class);
		xstream.alias("HOUSE", House.class);
		Person person=(Person)xstream.fromXML(XmlUtils.getXml());
		System.out.println(person.toString());*/
	}
	
	public static String getXml(){
		StringBuffer sb=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");//
		sb.append("<PERSON firstName=\"Herman\">");
		sb.append("<lastName>Xiong</lastName>  ");
		sb.append("<phonex>");
		sb.append("<code>0</code>");
		sb.append("<number>1234567</number>");
		sb.append("</phonex>");
		sb.append("<fax>");
		sb.append("<code>0</code>");
		sb.append("<number>7654321</number>");
		sb.append("</fax>");
		sb.append("<addList>");
		sb.append("<ADDRESS>");
		sb.append("<add>湖北省十堰市</add>");
		sb.append("<zipCode>123456</zipCode>");
		sb.append("</ADDRESS>");
		sb.append("</addList>");
		sb.append("<house>");
		sb.append("<HOUSE>");
		sb.append("<size>300万</size>");
		sb.append("<price>120平方米</price>");
		sb.append("</HOUSE>");
		sb.append("<HOUSE>");
		sb.append("<size>500万</size>");
		sb.append("<price>130平方米</price>");
		sb.append("</HOUSE>");
		sb.append("<HOUSE>");
		sb.append("<size>160万</size>");
		sb.append("<price>61.5平方米</price>");
		sb.append("</HOUSE>");
		sb.append("</house>");
		sb.append("</PERSON>\n");
		return sb.toString();
	}
}
