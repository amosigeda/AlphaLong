package com.jfservice.common.lang;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CalculatorUtils {

	public static double[] calculatorLonAndLat(double lon,double lat){
		
		double[] gps = new double[2];
	    double temp_lat = Math.floor(lat / 100);
		double temp_lat_yu = (lat % 100) / 60;
		
		temp_lat = temp_lat + temp_lat_yu;
		BigDecimal bd = new BigDecimal(temp_lat);
		bd = bd.setScale(4, BigDecimal.ROUND_HALF_UP);
		gps[0] = bd.doubleValue();
		
		double temp_lon = Math.floor(lon / 100);
		double temp_lon_yu = (lon % 100) / 60;
		
		temp_lon = temp_lon + temp_lon_yu;
		BigDecimal bd1 = new BigDecimal(temp_lon);
		bd1 = bd1.setScale(4, BigDecimal.ROUND_HALF_UP);
		gps[1] = bd1.doubleValue();
		return gps;
	}
	
	public static boolean isSameDay(String date){
		boolean isSome = false;
		if(!date.equals("0")){
			date = date.substring(0,10);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			isSome = date.equals(df.format(new Date()));
		}
		return isSome;
	}
	public static String[] getSplitRegx(String input, String regx){
		if(input != null){
			Pattern mPattern = Pattern.compile(regx);
			return mPattern.split(input);
		}else{
			return new String[]{"0"};   //失败
		}
		
	}
	public static String getSubStr(String str, String regx, int num) {
		String result = "";
		  int i = 0;
		  while(i < num) {
		   int lastFirst = str.lastIndexOf(regx);
		   if(lastFirst == -1){
			   return result;
		   }
		   result = str.substring(lastFirst) + result;
		   str = str.substring(0, lastFirst);
		   i++;
		  }
		  return result.substring(1);
	}
	public static void main(String[] args){
		double[] temp = calculatorLonAndLat(2234.96355,11351.33223);
		//System.out.println("经度="+temp[0]+",纬度="+temp[1]);
	}
}
