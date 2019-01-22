package com.chinacscs.platform.search.index;


/**
 * @author:  liusong
 * @date:    2019年1月21日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public enum FileType {
	ZIP(".zip"), ADD(".add"), UPDATE(".update"), DEL(".del");

	private String value;

	FileType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
