package com.chinacscs.platform.search.index;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author:  liusong
 * @date:    2019年1月12日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
@Component
public class WorkerManager {

	@Value("${work.cmd:#}")
	private String workCmd;
	
	@Autowired
	private ConfigurableApplicationContext applicationContext;

	private Thread thread;
	
	@PostConstruct
	private void init() {
		thread = new Thread(this::dispatch);
		thread.setDaemon(false);
		thread.start();
	}
	
	private void dispatch() {
		if(!"#".equals(workCmd)) {
			AbstactWorker abstactWorker=applicationContext.getBean(workCmd, AbstactWorker.class);
			abstactWorker.run(workCmd,UUID.randomUUID().toString());
			System.exit(1);
		}
	}
}
