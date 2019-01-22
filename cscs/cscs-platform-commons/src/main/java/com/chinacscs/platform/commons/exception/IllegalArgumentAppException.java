package com.chinacscs.platform.commons.exception;

/**
 * @author: liusong
 * @date: 2018年12月18日
 * @email: 359852326@qq.com
 * @version:
 * @describe: 非法参数异常
 */
public class IllegalArgumentAppException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 323236089791308891L;

	public IllegalArgumentAppException() {
		super();
	}

	public IllegalArgumentAppException(String message) {
		super(message);
	}

	public IllegalArgumentAppException(Throwable cause) {
		super(cause);
	}

	public IllegalArgumentAppException(String message, Throwable cause) {
		super(message, cause);
	}
}
