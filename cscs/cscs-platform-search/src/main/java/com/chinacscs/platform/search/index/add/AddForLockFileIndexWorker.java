package com.chinacscs.platform.search.index.add;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacscs.platform.search.index.FileIndexWorker;
import com.chinacscs.platform.search.index.FileStatus;
import com.chinacscs.platform.search.index.FileType;

/**
 * @author: liusong
 * @date: 2019年1月21日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public class AddForLockFileIndexWorker extends FileIndexWorker {

	protected final static Logger log = LoggerFactory.getLogger(AddForLockFileIndexWorker.class);

	public AddForLockFileIndexWorker() {
		super(FileType.ADD, FileStatus.NEW);
	}

	@Override
	protected void doProcess(File file) throws Exception {
		String newFileName = generateFileName(FileType.ADD, FileStatus.LOCK);
		File newFile = new File(file.getParent(), newFileName);
		file.renameTo(newFile);
		doFile(newFile);
	}

	private void doFile(File file) throws Exception {
		List<String> dataJsons = FileUtils.readLines(file, "utf-8");
		log.info(dataJsons.toString());
	}
}
