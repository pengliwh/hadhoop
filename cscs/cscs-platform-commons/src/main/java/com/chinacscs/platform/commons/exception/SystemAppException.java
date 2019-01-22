package com.chinacscs.platform.commons.exception;


/**
 * @author:  liusong
 * @date:    2018年12月18日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class SystemAppException extends AppException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2009977545087099336L;

	public SystemAppException() {
		super();
	}

	public SystemAppException(String message) {
		super(message);
	}

	public SystemAppException(Throwable cause) {
		super(cause);
	}

	public SystemAppException(String message, Throwable cause) {
		super(message, cause);
	}
}
