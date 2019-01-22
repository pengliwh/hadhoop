package com.chinacscs.platform.commons.component;

/**
 * @author: liusong
 * @date: 2018年12月14日
 * @email: 359852326@qq.com
 * @version:
 * @describe: 全局id生成器
 */
public interface IdGenerator {

	void init();
	
	long nextId();
	
	void close();
}
