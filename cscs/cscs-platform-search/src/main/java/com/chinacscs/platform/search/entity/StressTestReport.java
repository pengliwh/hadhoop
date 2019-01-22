package com.chinacscs.platform.search.entity;

import lombok.Data;
/**
 * @author: liusong
 * @date: 2019年1月12日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
@Data
public class StressTestReport {

	private String job;
	
	private String group;

	private boolean succeed;
	
	private long startTime;
	
	private long endTime;

	private long consumeTime;
	
	private String track;
}
