package com.chinacscs.platform.commons.exception;


/**
 * @author:  liusong
 * @date:    2018年12月19日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class UnsupportedOperationAppException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7721539412970808502L;

	public UnsupportedOperationAppException() {
		super();
	}

	public UnsupportedOperationAppException(String message) {
		super(message);
	}

	public UnsupportedOperationAppException(Throwable cause) {
		super(cause);
	}

	public UnsupportedOperationAppException(String message, Throwable cause) {
		super(message, cause);
	}
}