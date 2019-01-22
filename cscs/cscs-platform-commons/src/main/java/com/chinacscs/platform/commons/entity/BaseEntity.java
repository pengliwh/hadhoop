package com.chinacscs.platform.commons.entity;

import lombok.Data;

/**
 * @author:MG01867
 * @date:2018年6月15日
 * @email:359852326@qq.com
 * @version:
 * @describe 基础实体类
 */
@Data
public abstract class BaseEntity {

	/**数据id业务无关性:VARCHAR(36)**/
	private Long id;
	
	/**数据版本:INT(11)**/
	private Integer version;
}
