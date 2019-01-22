package com.chinacscs.platform.search.index;


/**
 * @author:  liusong
 * @date:    2019年1月21日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public enum FileStatus {
	NEW("new_"), LOCK("lock_"), ERROR("error_"), FINISH("finish_");

	private String value;

	FileStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
