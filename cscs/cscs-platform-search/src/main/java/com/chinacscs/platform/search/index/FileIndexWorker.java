package com.chinacscs.platform.search.index;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: liusong
 * @date: 2019年1月21日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public abstract class FileIndexWorker implements IndexWorker {

	protected final static Logger log = LoggerFactory.getLogger(FileIndexWorker.class);

	private final static String LISTEN_THREAD_NAME_TEMPLATE = "FileIndexWorker-listen-%s-%s";

	private final static String WORK_THREAD_NAME_TEMPLATE = "FileIndexWorker-work-%s-%s";

	private String name;

	private String baseDirPath;

	private File baseDir;

	private FileType fileType;

	private FileStatus fileStatus;

	private Thread listenThread;

	private ExecutorService executorService;

	private long listenInterval;

	private Set<String> fileNameSet = new HashSet<>();

	public FileIndexWorker(FileType fileType, FileStatus fileStatus) {
		Objects.requireNonNull(fileType);
		Objects.requireNonNull(fileStatus);
		this.fileType = fileType;
		this.fileStatus = fileStatus;
		String listenThreadName = String.format(LISTEN_THREAD_NAME_TEMPLATE, fileType.getValue(),
				fileStatus.getValue());
		this.listenThread = new Thread(this::listenFiles, listenThreadName);
		this.listenThread.setDaemon(true);
		this.executorService = Executors.newSingleThreadExecutor(run -> {
			String workThreadName = String.format(WORK_THREAD_NAME_TEMPLATE, fileType.getValue(),
					fileStatus.getValue());
			Thread thread = new Thread(run, workThreadName);
			thread.setDaemon(false);
			return thread;
		});
	}

	@Override
	public final void setListenInterval(long listenInterval) {
		this.listenInterval = listenInterval;
	}

	@Override
	public final void start() {
		this.listenThread.start();
	}

	@Override
	public final void end() {

	}

	private void listenFiles() {
		while (true) {
			File[] files = baseDir.listFiles((dir, name) -> {
				return name.endsWith(getFileType().getValue());
			});
			if (null != files && files.length > 0) {
				sortForFile(files);
				for (File file : files) {
					process(file);
				}
			}
			try {
				Thread.sleep(listenInterval);
			} catch (InterruptedException e) {
				// 异常忽略
			}
		}
	}

	private void process(File file) {
		if (!fileNameSet.contains(file.getName())) {
			fileNameSet.add(file.getName());
			executorService.execute(() -> {
				try {
					process(file);
				} catch (Exception exception) {
					log.error(String.format("The FileIndexWorker[%s] process file[%s] error", name,
							file.getName()), exception);
				} finally {
					fileNameSet.remove(file.getName());
				}
			});
		}
	}

	protected abstract void doProcess(File file)throws Exception;

	protected String generateFileName(FileType type, FileStatus status) {
		return null;
	}

	private File[] sortForFile(File[] files) {
		return null;
	}

	public String getBaseDirPath() {
		return baseDirPath;
	}

	public File getBaseDir() {
		return baseDir;
	}

	public FileType getFileType() {
		return fileType;
	}

	public FileStatus getFileStatus() {
		return fileStatus;
	}
}
