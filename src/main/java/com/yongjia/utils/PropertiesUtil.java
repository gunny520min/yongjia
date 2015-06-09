package com.yongjia.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * PropertiesUtil.java
 * 
 * @desc properties 资源文件解析工具
 * @author magenm
 * @datatime 2013-12-2
 * 
 */
public class PropertiesUtil {

	private static Properties props;
	static {
		props = new Properties();
		InputStream fis = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("cfg/config.properties");
		try {
			props.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取某个属性
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

}