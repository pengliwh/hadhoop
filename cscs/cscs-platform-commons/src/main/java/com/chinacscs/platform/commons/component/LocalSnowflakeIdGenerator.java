package com.chinacscs.platform.commons.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinacscs.platform.commons.util.SnowflakeUtils;

/**
 * @author: liusong
 * @date: 2018年12月14日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public class LocalSnowflakeIdGenerator implements IdGenerator {

	static final Logger log = LoggerFactory.getLogger(LocalSnowflakeIdGenerator.class);

	/** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
	private final static long sequenceMask = -1L ^ (-1L << SnowflakeUtils.SEQ_BITS);

	private long workerId;

	private volatile long lastTimestamp = 0l;

	private volatile long sequence = 0l;

	@Override
	public synchronized final long nextId() {
		long timestamp = System.currentTimeMillis();
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
					"Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		} else if (timestamp == lastTimestamp) {
			sequence = ++sequence & sequenceMask;
			if (0 == sequence) {
				while (timestamp <= lastTimestamp) {
					timestamp = System.currentTimeMillis();
				}
			}
		} else {
			sequence = 0l;
		}
		long newId = SnowflakeUtils.generateId(workerId, timestamp, sequence);
		this.lastTimestamp = timestamp;
		return newId;
	}

	protected void setWorkerId(long workerId) {
		this.workerId = workerId;
	}

	protected long getLastTimestamp() {
		return lastTimestamp;
	}

	@Override
	public void init() {

	}

	@Override
	public void close() {

	}
}
