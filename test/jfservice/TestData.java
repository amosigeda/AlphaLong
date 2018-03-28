package jfservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.jfservice.common.bean.alpha.subcri.SubcriJsonData;
import com.jfservice.common.lang.CalculatorUtils;

public class TestData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*String temp = "";
		String[] s = temp.split("#");
		System.out.println(s[0]);
		
		Pattern mPattern = Pattern.compile("#");
		String[] ss = mPattern.split(temp);
		for(String st : ss){
			System.out.println(st);
		}*/
		byte[] testBuf = new byte[20];
		String ss = new String(testBuf,0,8);
		String tenl = "\"ff\"";
		System.out.println(tenl.length());
		String tempSplite = "MG|310|264|0|KW02|20|20150101001627-216|reqConnect||{\"cmd\":\"reqConnect\",\"devId\":\"123456798952368\",\"sCode\":\"CC6DA7\",\"hbRate\":60,\"devInfo\":{\"IMEI\":\"123456798952368\",\"IMSI\":\"9460020228862591\",\"productId\":\"KW02\",\"fwVer\":\"A2510_ALPHA_01_V01_160106C\",\"fwBuild\":1,\"mcuBuild\":1,\"btName\":\"buga2\",\"btMac\":\"00:00:00:00:00:00\"}}|";
		System.out.println(tempSplite.length());
		String s = CalculatorUtils.getSubStr(tempSplite, "|", 2).replace("|", "");
	    System.out.println(s);
	    
	    String cfg = "{\"cmd\": \"devCfg\",\"devCfg\": {\"isStepOn\" : 1,\"isAutoLctOn\" : 1,\"sosNum\" : [\"13978388888\",\"13978386666\",\"18778388888\"],\"cmdConnCfg\" : {\"port\" : 9995,\"host\" : \"svr.buga.cn\"},\"dataConnCfg\" : {\"port\" : 8080,\"host\" : \"svr.buga.cn\"}}}";
	    SubcriJsonData tempSub = JSON.parseObject(cfg, SubcriJsonData.class);
	    System.out.println(cfg);
	}

}
