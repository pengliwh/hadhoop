package com.chinacscs.platform.commons.exception;

/**
 * @author: liusong
 * @date: 2018年12月18日
 * @email: 359852326@qq.com
 * @version:
 * @describe: 非法操作异常
 */
public class IllegalOperationAppException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 323236089791308891L;

	public IllegalOperationAppException() {
		super();
	}

	public IllegalOperationAppException(String message) {
		super(message);
	}

	public IllegalOperationAppException(Throwable cause) {
		super(cause);
	}

	public IllegalOperationAppException(String message, Throwable cause) {
		super(message, cause);
	}
}
