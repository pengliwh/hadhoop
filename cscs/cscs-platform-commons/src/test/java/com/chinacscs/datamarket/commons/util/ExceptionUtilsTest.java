package com.chinacscs.datamarket.commons.util;

import com.chinacscs.platform.commons.util.ExceptionUtils;

/**
 * @author:  liusong
 * @date:    2018年12月20日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class ExceptionUtilsTest {

	public static void main(String[] args) {
		try {
			doing1();
		} catch (Exception exception) {
			String msg= ExceptionUtils.getExceptionMsg(exception);
			System.out.println(msg);
		}
	}
	
	private static void doing1() throws Exception {
		try {
			doing2();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private static void doing2() throws Exception {
		throw new Exception("test exception");
	}
}
