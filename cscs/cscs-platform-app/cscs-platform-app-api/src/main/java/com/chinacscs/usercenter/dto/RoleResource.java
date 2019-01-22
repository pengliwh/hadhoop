package com.chinacscs.usercenter.dto;

import com.chinacscs.platform.commons.dto.AuditBaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author: liusong
 * @date: 2018年12月13日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoleResource extends AuditBaseDTO {
	
	/**角色id**/
	private String roleId;
	
	/**资源id**/
	private String resourceId;
}
