package com.chinacscs.usercenter.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.chinacscs.platform.commons.dto.AuditBaseDTO;
import com.chinacscs.usercenter.constant.ResourceType;
import com.chinacscs.usercenter.validation.AddValidationGroup;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
*@author:MG01867
*@date:2018年11月6日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Resource extends AuditBaseDTO{

	/**资源名称**/
	@NotBlank(message = "The appId must be not blank",groups= {AddValidationGroup.class})
	private String appId;
	
	/**资源名称**/
	@NotBlank(message = "The name must be not blank",groups= {AddValidationGroup.class})
	private String name;
	
	/**资源类型**/
	@NotBlank(message = "The type must be not blank",groups= {AddValidationGroup.class})
	private ResourceType type;
	
	/**资源路径**/
	@NotBlank(message = "The path must be not blank",groups= {AddValidationGroup.class})
	private String path;
	
	/** 层级深度 **/
	private int depth;
	
	/**资源是否公开的**/
	@NotNull(message = "The open must be not null",groups= {AddValidationGroup.class})
	private boolean open;
}
