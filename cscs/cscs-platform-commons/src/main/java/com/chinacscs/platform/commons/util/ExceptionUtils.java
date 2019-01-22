package com.chinacscs.platform.commons.util;

/**
 * @author: liusong
 * @date: 2018年12月20日
 * @email: 359852326@qq.com
 * @version:
 * @describe: 异常工具
 */
public class ExceptionUtils {

	public static String getExceptionMsg(Exception exception) {
		return getExceptionMsg(exception, true);
	}

	public static String getExceptionMsg(Exception exception, boolean getStackTrace) {
		StringBuilder msgSb = new StringBuilder();
		Throwable realThrowable = null;
		Throwable tempThrowable = exception;
		while (null != tempThrowable) {
			realThrowable = tempThrowable;
			tempThrowable = tempThrowable.getCause();
		}
		msgSb.append(realThrowable.getClass());
		msgSb.append(":");
		msgSb.append(realThrowable.getMessage());
		msgSb.append("\n");
		if (getStackTrace) {
			StackTraceElement[] stackTraceElements = realThrowable.getStackTrace();
			for (StackTraceElement stackTraceElement : stackTraceElements) {
				msgSb.append(stackTraceElement.getClassName());
				msgSb.append(".");
				msgSb.append(stackTraceElement.getMethodName());
				msgSb.append("(");
				msgSb.append(stackTraceElement.getFileName());
				msgSb.append(":");
				msgSb.append(stackTraceElement.getLineNumber());
				msgSb.append(")");
				msgSb.append("\n");
			}
		}
		return msgSb.toString();
	}
}
