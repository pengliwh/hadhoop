package com.chinacscs.platform.search.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.platform.search.BaseTest;
import com.chinacscs.platform.search.service.EsStressTestService;

/**
 * @author:  liusong
 * @date:    2019年1月14日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class EsStressTestServiceTest extends BaseTest{

	@Autowired
	EsStressTestService esStressTestService;
	
	@Test
	public synchronized void testStressTestReport() throws InterruptedException {
		esStressTestService.stressTestReport("SolrExportWorker",1);
		this.wait();
	}
}
