package com.chinacscs.platform.search.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chinacscs.platform.search.entity.StressTestReport;
import com.chinacscs.platform.search.index.StressTestWorker;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: liusong
 * @date: 2019年1月12日
 * @email: 359852326@qq.com
 * @version:
 * @describe: es压力测试
 */
@RestController
@RequestMapping("es-test")
@Slf4j
public class EsStressTestService {

	private Map<String, StressTestReport> stressTestReportStore = new HashMap<>();

	private AtomicInteger threadIndex = new AtomicInteger(0);

	private ExecutorService threadPool;

	@Autowired
	private ConfigurableApplicationContext applicationContext;

	@Autowired
	private AutowireCapableBeanFactory capableBeanFactory;
	
	@PostConstruct
	private void init() {
		threadPool = Executors.newFixedThreadPool(5000, this::threadFactory);
	}

	private Thread threadFactory(Runnable run) {
		Thread thread = new Thread(run, "es-stressTest-thread-" + threadIndex.getAndIncrement());
		thread.setDaemon(false);
		return thread;
	}

	@RequestMapping(value = "/stressTestReport", method = RequestMethod.POST)
	@ResponseBody
	public String stressTestReport(@RequestParam("job") String job,
			@RequestParam("threads") int threads) {
		StressTestWorker worker = applicationContext.getBean(job, StressTestWorker.class);
		if (null == worker) {
			throw new UnsupportedOperationException();
		}
		String uuid = UUID.randomUUID().toString();
		log.info(String.format("start run job[%s] group[%s]", job,uuid));
		doStressTestReport(worker,threads,job,uuid);
		return uuid;
	}

	@SuppressWarnings("deprecation")
	private StressTestWorker copyAndNewWorker(StressTestWorker worker) {
		StressTestWorker newWorker;
		try {
			newWorker = worker.getClass().newInstance();
			capableBeanFactory.autowireBean(newWorker);
			newWorker.afterPropertiesSet();
			return newWorker;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private void doStressTestReport(StressTestWorker worker,int threads,String job,String group){
		for (int i = 0; i < threads; i++) {
			StressTestWorker newWorker=copyAndNewWorker(worker);
			threadPool.execute(() -> {
				try {
					newWorker.run(job,group);
				} catch (Exception exception) {
					log.error(String.format("stressTest work error for group[%s]",group), exception);
				}
			});
		}
	}

	@RequestMapping(value = "/stressTestReport/{key}", method = RequestMethod.GET)
	@ResponseBody
	public StressTestReport getStressTestReport(@PathVariable("key") String key) {
		return stressTestReportStore.get(key);
	}

	@PreDestroy
	private void shutdown() {
		threadPool.shutdown();
	}
}
