package com.chinacscs.platform.commons.exception;

/**
 * @author: liusong
 * @date: 2018年12月14日
 * @email: 359852326@qq.com
 * @version:
 * @describe: 应用异常
 */
public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9008864538631403842L;

	public AppException() {
		super();
	}

	public AppException(String message) {
		super(message);
	}

	public AppException(Throwable cause) {
		super(cause);
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

}
