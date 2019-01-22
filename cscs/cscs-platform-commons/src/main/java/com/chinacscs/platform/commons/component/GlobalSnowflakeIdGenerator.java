package com.chinacscs.platform.commons.component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

/**
 * @author: liusong
 * @date: 2018年12月14日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public class GlobalSnowflakeIdGenerator extends LocalSnowflakeIdGenerator {

	static final Logger log = LoggerFactory.getLogger(GlobalSnowflakeIdGenerator.class);

	private final static String ROOT_PATH = "/snowflake_global_id";

	private final static String SEQ_PATH = ROOT_PATH + "/sequence";

	private String workerName;

	private String appName;

	private String zkConnectString;

	private CuratorFramework curatorFramework;

	private String workerForeverPath;

	private String workerLastTimestampPath;

	private Thread refreshTimestampThread;

	public GlobalSnowflakeIdGenerator(String appName, String zkConnectString) {
		this.appName = appName;
		this.zkConnectString = zkConnectString;
		this.refreshTimestampThread = new Thread(this::loopRefreshTimestamp);
		this.refreshTimestampThread.setDaemon(true);
		this.refreshTimestampThread.setName("refresh-timestamp-thread");
		this.refreshTimestampThread.start();
	}

	@PostConstruct
	public void init() {
		initCuratorFramework();
		initZkPath();
		long workerId = initWorkerId();
		setWorkerId(workerId);
	}

	private void initCuratorFramework() {
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		curatorFramework = CuratorFrameworkFactory.newClient(zkConnectString, retryPolicy);
		curatorFramework.start();
	}

	private void initZkPath() {
		this.workerName = StringUtils.replace(appName, "-", "_");
		this.workerForeverPath = ROOT_PATH + "/forever/" + workerName;
		this.workerLastTimestampPath = ROOT_PATH + "/last_timestamp/" + workerName;
	}

	private long initWorkerId() {
		Stat existStat = null;
		try {
			existStat = curatorFramework.checkExists().forPath(workerForeverPath);
		} catch (Exception exception) {
			throw new RuntimeException("check worker path error", exception);
		}
		String sequenceValue = null;
		if (null == existStat) {
			try {
				sequenceValue = curatorFramework.create().creatingParentContainersIfNeeded()
						.withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(SEQ_PATH);
				curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
						.forPath(workerForeverPath, sequenceValue.getBytes());
				curatorFramework.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
						.forPath(workerLastTimestampPath, String.valueOf(getLastTimestamp()).getBytes());
			} catch (Exception exception) {
				throw new RuntimeException("create worker path error", exception);
			}
		} else {
			try {
				byte[] data = curatorFramework.getData().forPath(workerLastTimestampPath);
				String lastTimestampStr = new String(data);
				long lastTimestamp = Long.valueOf(lastTimestampStr);
				if (lastTimestamp > getLastTimestamp()) {
					throw new RuntimeException(String.format(
							"Clock moved backwards,Refusing to generate id for milliseconds[%s]", getLastTimestamp()));
				}
			} catch (Exception exception) {
				throw new RuntimeException("check worker path error", exception);
			}

			try {
				byte[] data = curatorFramework.getData().forPath(workerForeverPath);
				sequenceValue = new String(data);
			} catch (Exception exception) {
				throw new RuntimeException("check worker path error", exception);
			}
		}
		String numStr = StringUtils.replace(sequenceValue, SEQ_PATH, "");
		return NumberUtils.parseNumber(numStr, Long.class);
	}

	private void loopRefreshTimestamp() {
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// ignore
			}
			try {
				curatorFramework.setData().forPath(workerLastTimestampPath,
						String.valueOf(getLastTimestamp()).getBytes());
			} catch (Exception exception) {
				log.warn("refresh timestamp error", exception);
			}
		}
	}

	@PreDestroy
	public void destroy() {
		if (null != curatorFramework) {
			curatorFramework.close();
		}
	}
}
