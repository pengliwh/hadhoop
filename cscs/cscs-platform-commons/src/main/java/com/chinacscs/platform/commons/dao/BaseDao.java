package com.chinacscs.platform.commons.dao;

import com.chinacscs.platform.commons.entity.BaseEntity;

/**
 * @author:MG01867
 * @date:2018年10月31日
 * @email:359852326@qq.com
 * @version:
 * @describe 数据访问基础接口
 */
public interface BaseDao<T extends BaseEntity> {

	/**
	 * 根据id获取数据
	 * 
	 * @param id 数据id
	 * @return 返回匹配的数据实体
	 */
	T get(Long id);

	/**
	 * 新增数据实体
	 * 
	 * @param t 数据实体
	 * @return 返回新增影响行数
	 */
	int add(T t);

	/**
	 * 根据实体id删除数据
	 * 
	 * @param id           数据id
	 * @return 返回影响行数
	 */
	int delete(Long id);
}
