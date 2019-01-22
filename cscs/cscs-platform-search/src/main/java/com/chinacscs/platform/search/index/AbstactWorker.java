package com.chinacscs.platform.search.index;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author: liusong
 * @date: 2019年1月12日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public abstract class AbstactWorker implements StressTestWorker {

	protected final static String END_FLAG ="END_FLAG";

	@Value("${app.home}")
	private String appHome;

	@Value("${export.table}")
	private String table;

	@Value("${es.url}")
	private String esUrl;
	
	private String dataDirPath;

	public AbstactWorker() {
		dataDirPath = appHome + File.separatorChar + "data";
	}

	protected String getDataPath() {
		String dataDir = dataDirPath + File.separatorChar + table;
		return dataDir;
	}

}
