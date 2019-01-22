package com.chinacscs.platform.commons.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.chinacscs.platform.commons.util.DateTimeFormatPatternUtils;
import com.chinacscs.platform.commons.validation.AddValidationGroup;
import com.chinacscs.platform.commons.validation.UpdateValidationGroup;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年6月15日
 * @email:359852326@qq.com
 * @version:
 * @describe 基础审计实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AuditBaseDTO extends BaseDTO{

	/** 数据更新用户 **/
	@NotNull(message = "The updateUserId must be not null", groups = { UpdateValidationGroup.class })
	private Long updateUserId;

	/** 数据更新日期 **/
	@JsonFormat(pattern = DateTimeFormatPatternUtils.DATE_TIME_FOR_COMMON)
	@DateTimeFormat(pattern = DateTimeFormatPatternUtils.DATE_TIME_FOR_COMMON)
	private Date updateDate;

	/** 数据创建用户 **/
	@NotNull(message = "The createUserId must be not null", groups = { AddValidationGroup.class })
	private Long createUserId;

	/** 数据创建日期 **/
	@JsonFormat(pattern = DateTimeFormatPatternUtils.DATE_TIME_FOR_COMMON)
	@DateTimeFormat(pattern = DateTimeFormatPatternUtils.DATE_TIME_FOR_COMMON)
	private Date createDate;
}
