package com.chinacscs.usercenter.repository.entity;


import com.chinacscs.platform.commons.entity.AuditBaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author:MG01867
 * @date:2018年11月10日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppEntity extends AuditBaseEntity {

	/**应用编码varchar(40)**/
	private String code;

	/**应用名称varchar(20)**/
	private String name;

	/**token过期时间**/
	private long tokenExpiredTime;

	/**刷新token过期时间**/
	private int refreshTokenExpiredTime;
	
	/**描述**/
	private String describe;
}
