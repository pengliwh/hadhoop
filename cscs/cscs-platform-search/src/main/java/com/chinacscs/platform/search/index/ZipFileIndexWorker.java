package com.chinacscs.platform.search.index;

import java.io.File;

/**
 * @author:  liusong
 * @date:    2019年1月21日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class ZipFileIndexWorker extends FileIndexWorker{

	public ZipFileIndexWorker() {
		super(FileType.ZIP,FileStatus.NEW);
	}

	@Override
	protected void doProcess(File file) {
		
	}
}
