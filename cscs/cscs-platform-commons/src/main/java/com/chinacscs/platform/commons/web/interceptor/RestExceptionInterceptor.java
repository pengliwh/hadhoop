package com.chinacscs.platform.commons.web.interceptor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinacscs.platform.commons.exception.AppException;
import com.chinacscs.platform.commons.exception.IllegalOperationAppException;

import lombok.extern.slf4j.Slf4j;

import com.chinacscs.platform.commons.exception.IllegalArgumentAppException;

/**
 * @author:MG01867
 * @date:2018年8月22日
 * @email:359852326@qq.com
 * @version:
 * @describe 异常信息统一拦截
 */
@Slf4j
@ControllerAdvice
public class RestExceptionInterceptor {

	final static String SYSTEM_ERROR_MSG = "system error";

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public String handle(HttpServletResponse resp, Exception exception) {
		if (exception instanceof ValidationException
				|| exception instanceof MethodArgumentNotValidException
				|| exception instanceof MissingServletRequestParameterException
				|| exception instanceof NullPointerException
				|| exception instanceof IllegalArgumentException
				|| exception instanceof IllegalStateException
				|| exception instanceof UnsupportedOperationException
				|| exception instanceof IllegalArgumentAppException
				|| exception instanceof IllegalOperationAppException) {
			log.warn(exception.getMessage(), exception);
			resp.setStatus(HttpStatus.BAD_REQUEST.value());
			return exception.getMessage();
		} else if (exception instanceof RuntimeException
				|| exception instanceof AppException) {
			return doSystemError(resp, exception);
		} else {
			return doSystemError(resp, exception);
		}
	}

	private static String doSystemError(HttpServletResponse resp,
			Exception exception) {
		log.error(exception.getMessage(), exception);
		resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return SYSTEM_ERROR_MSG;
	}
}
