package com.chinacscs.platform.commons.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.quartz.CronExpression;
import org.springframework.util.StringUtils;

/**
 * @author:MG01867
 * @date:2018年11月5日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CronScriptValidation.Checker.class)
@Documented
public @interface CronScriptValidation {

	String message() default "The current user is illegal";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String value() default "";

	boolean inclusive() default true;

	public class Checker implements ConstraintValidator<CronScriptValidation, String> {

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			if (StringUtils.isEmpty(value) ||!CronExpression.isValidExpression(value)) {
				return false;
			}
			return true;
		}
	}
}