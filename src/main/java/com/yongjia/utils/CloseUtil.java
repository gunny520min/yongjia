package com.yongjia.utils;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 资源关闭辅助类
 * @author woodle
 *
 */
public class CloseUtil {
	
	private static final Log log = LogFactory.getLog(CloseUtil.class);
	
	public static void closeResouce(Closeable closeable) throws IOException {
		if (closeable == null) {
			return;
		}
		try {
			closeable.close();
			closeable = null;
		} catch (IOException e) {
			log.error( "IOException thrown while closing Closeable."+ e);			
		}
	}
	
	public static void closeResouces(Closeable... clos) throws IOException {
		for (Closeable closeable : clos) {
			closeResouce(closeable);
		}
	}
}
