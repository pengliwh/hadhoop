package com.chinacscs.platform.commons.entity;

import java.util.Date;

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
public abstract class AuditBaseEntity extends BaseEntity{
	
	/**数据更新用户id:VARCHAR(36)**/
	private Long updateUserId;
	
	/**数据更新日期(每次写操作时赋值):TIMESTAMP**/
	private Date updateDate;
	
	/**数据创建用户id:VARCHAR(36)**/
	private Long createUserId;
	
	/**数据创建日期(只在创建时赋值):TIMESTAMP**/
	private Date createDate;
}
