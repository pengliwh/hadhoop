package com.chinacscs.platform.commons.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.chinacscs.platform.commons.validation.UpdateValidationGroup;

import lombok.Data;

/**
 * @author:MG01867
 * @date:2018年6月15日
 * @email:359852326@qq.com
 * @version:
 * @describe 基础实体类
 */
@Data
public abstract class BaseDTO {

	/** 数据id业务无关性:VARCHAR(36) **/
	@NotBlank(message = "The id must be not blank", groups = { UpdateValidationGroup.class })
	private Long id;

	/** 数据版本:INT(11) **/
	@NotNull(message = "The version must be not null", groups = { UpdateValidationGroup.class })
	@Min(value = 0, message = "The version must be >=0", groups = { UpdateValidationGroup.class })
	private Integer version;
}
