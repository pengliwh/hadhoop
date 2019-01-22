package com.chinacscs.platform.commons.util;

import com.chinacscs.platform.commons.exception.IllegalArgumentAppException;
import com.chinacscs.platform.commons.exception.IllegalOperationAppException;

/**
 * @author: liusong
 * @date: 2018年12月27日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public class ValidateUitls {

	public static void IllegalOperation(String msg) {
		throw new IllegalOperationAppException(msg);
	}

	public static <T> T nonnull(T value, String name) {
		if (null == value) {
			throw new IllegalArgumentAppException(String.format("The parameter[%s] is illegal", name));
		}
		return value;
	}

	public static <T> T nonnull(T value) {
		if (null == value) {
			throw new IllegalArgumentAppException();
		}
		return value;
	}
}
