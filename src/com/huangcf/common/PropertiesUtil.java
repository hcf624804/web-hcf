package com.huangcf.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
private static Properties props = new Properties();
	
	static {
		InputStream inStream = PropertiesUtil.class
			.getClassLoader().getResourceAsStream(
				"opt.properties");
		try {
			props.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getOptmsg(String key){
		String msg = props.getProperty(key);
		return msg;
	}
}
