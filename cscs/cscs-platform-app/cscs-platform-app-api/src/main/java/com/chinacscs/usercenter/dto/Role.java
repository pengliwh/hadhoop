package com.chinacscs.usercenter.dto;

import com.chinacscs.platform.commons.dto.AuditBaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年11月6日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Role extends AuditBaseDTO {
	
	/** 资源名称 **/
	private String name;

	/** 层级深度 **/
	private int depth;

	/** 父资源id **/
	private String parentId;
}
