package com.jfservice.common.lang;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Constant {

	// 经纬度接口
	public static final String LOCATION_URL = "http://restapi.amap.com/v3/assistant/coordinate/convert";
	public static final String KEY = "801df1e9132e2151cd9ad435ecc59858";

	public static final String WEATHER_URL = "https://api.thinkpage.cn/v3/weather/now.json";
	public static final String WEATHER_KEY = "IPSOQKNLD6";
	// lbs
	public static final String LOCATION_LBS_URL = "http://apilocate.amap.com/position";

	public static final String KEYS = "bcfbf9ebf25a4d0bf86fe9f416b62264";

	public static final int FAIL_CODE = 0; // 失败

	public final static int EXCEPTION_CODE = -1; // 异常

	public final static int SUCCESS_CODE = 1; // 成功

	// 忽略范围
	public final static double EFFERT_DATA = 100.0;

	// 地球半径
	public final static double EARTH_RADIUS = 6378.137;

	public static final String MEDIA_DOWNLOAD_PATH = "upload"
			+ System.getProperty("file.separator") + "media"
			+ System.getProperty("file.separator");

	public static final String MEDIA_PATH = System.getProperty("tranWeb.root")
			+ MEDIA_DOWNLOAD_PATH;
	public static final String SERVER = System.getProperty("file.separator")
			+ "AlphaLong";

	/*public static String getUniqueCode(String unique) {
		Calendar c = Calendar.getInstance();
		long now = c.getTime().getTime(); // 锟斤拷时锟斤拷锟絤s锟斤拷确锟斤拷唯一锟斤拷
		return unique + "_" + String.valueOf(now);
	}*/
	public static String getUniqueCode(String unique) {
		Calendar c = Calendar.getInstance();
		long now = c.getTime().getTime(); // 锟斤拷时锟斤拷锟絤s锟斤拷确锟斤拷唯一锟斤拷
		return unique + String.valueOf(now);
	}

	public static void createFileContent(String path, String fileName,
			byte[] content) throws IOException {
		createFile(path);
		FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(content);
		bos.flush();
		bos.close();
	}

	public static void createFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);

		long fileSize = file.length();

		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}

		FileInputStream fi = new FileInputStream(file);

		byte[] buffer = new byte[(int) fileSize];

		int offset = 0;

		int numRead = 0;

		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {

			offset += numRead;
		}

		// byte[] b = new byte[fi.available()];
		// offset = fi.read(b);

		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());

		}
		fi.close();
		return buffer;
	}

	public static String getDownloadPath(String url, String path) {
		String tmp = url + path;
		return tmp;
	}

	private static double getRadian(double degree) {
		return degree * Math.PI / 180;
	}

	/*
	 * 经纬度范围
	 */
	public static boolean getDistance(double lat1, double lng1, double lat2,
			double lng2, double range) {

		double radlat1 = getRadian(lat1);
		double radlat2 = getRadian(lat2);

		double lat_distance = radlat1 - radlat2; // 绾害宸�
		double lng_distance = getRadian(lng1) - getRadian(lng2); // 缁忓害宸�

		double distance = Math.asin(Math.sqrt(Math.pow(
				Math.sin(lat_distance / 2), 2)
				+ Math.cos(radlat1)
				* Math.cos(radlat2)
				* Math.pow(Math.sin(lng_distance / 2), 2)));

		distance = 2 * distance * EARTH_RADIUS * 1000;

		// distance = Math.round(distance);
		System.out.println("距离为" + distance);
		if (distance >= range) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		long s = System.currentTimeMillis()/1000;
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(s*1000);
		System.out.println(s);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(dateFormat.format(ca.getTime()));
		int ss = Integer.MAX_VALUE;
		if(ss < s){
			System.out.printf("%s", 2 + 2);
		}
		
	}
}
