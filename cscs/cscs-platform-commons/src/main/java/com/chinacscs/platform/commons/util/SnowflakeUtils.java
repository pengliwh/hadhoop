package com.chinacscs.platform.commons.util;

/**
 * @author: liusong
 * @date: 2018年12月27日
 * @email: 359852326@qq.com
 * @version:
 * @describe: 雪花算法工作
 */
public final class SnowflakeUtils {

	private final static long TWEPOCH = 1288834974657L;

	/** 序列在id中占的位数 */
	public final static long SEQ_BITS = 12L;

	/** 机器id所占的位数 */
	public final static long WID_BITS = 10L;

	/** 机器ID向左移12位 */
	public final static long WID_SHIFT = SEQ_BITS;

	/** 时间截向左移22位(5+5+12) */
	public final static long TIMESTAMP_LEFT_SHIFT = SEQ_BITS + WID_BITS;

	/**
	 * 生成雪花算法id
	 * 
	 * @param workerId
	 * @param timestamp
	 * @param sequence
	 * @return
	 */
	public static long generateId(long workerId, long timestamp,
			long sequence) {
		long newId = (timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT;
		newId |= workerId << WID_SHIFT;
		newId |= sequence;
		return newId;
	}

	/**
	 * 从雪花算法id中抽取时间戳
	 * 
	 * @param snowflakeId
	 * @return
	 */
	public static long extractTimestamp(long snowflakeId) {
		return (snowflakeId >> TIMESTAMP_LEFT_SHIFT) + TWEPOCH;
	}
}
