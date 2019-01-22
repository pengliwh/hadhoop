package com.chinacscs.datamarket.commons.component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.chinacscs.platform.commons.component.LocalSnowflakeIdGenerator;
/**
 * @author:  liusong
 * @date:    2018年12月17日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class LocalSnowflakeGeneratorTest {

	AtomicInteger index=new AtomicInteger();
	
	private Thread newThread(Runnable run) {
		Thread thread=new Thread(run);
		thread.setName("test-thread-"+index.incrementAndGet());
		return thread;
	}
	
	@Test
	public void test() throws InterruptedException {
		ExecutorService executorService=Executors.newFixedThreadPool(100,this::newThread);
		LocalSnowflakeIdGenerator idGenerator1=new LocalSnowflakeIdGenerator();
		int count=50000;
		AtomicLong total1=new AtomicLong();
		CountDownLatch cdl1=new CountDownLatch(count);
		for(int i=0;i<count;i++) {
			executorService.execute(()->{
				long start=System.currentTimeMillis();
				long id=idGenerator1.nextId();
				System.out.println(id);
				long end=System.currentTimeMillis();
				total1.addAndGet(end-start);
				cdl1.countDown();
			});
		}
		cdl1.await();
		System.out.println("算法时间:"+total1.longValue());
		executorService.shutdown();
	}
}
