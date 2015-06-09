package com.yongjia.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




public class CodeUtil {
	private static final Log log = LogFactory.getLog(CodeUtil.class);

	/**
	 * 规则：年月日时分秒+手机号后四位+随机号4位
	 * 
	 * @return
	 */
	public static String createCode(String mobile) {
		// TODO Auto-generated method stub

		StringBuffer sb = new StringBuffer();
		sb.append(DateUtil.convertLong2String(System.currentTimeMillis(), "yyyyMMddHHmmss"));
		sb.append(mobile.subSequence(7, 11));
		sb.append(DataUtils.getFixLenthString(4));		
		log.info(sb.toString());
		return sb.toString();
	}
	
	
	public static void main(String[] args) {
	String content=	CodeUtil.createCode("13466789042");
	log.info(content.length());
	
	}

	
}
