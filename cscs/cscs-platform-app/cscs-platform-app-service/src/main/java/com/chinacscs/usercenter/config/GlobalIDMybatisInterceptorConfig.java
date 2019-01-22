package com.chinacscs.usercenter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.chinacscs.platform.commons.component.GlobalSnowflakeIdGenerator;
import com.chinacscs.platform.commons.component.IdGenerator;
import com.chinacscs.platform.commons.dao.interceptor.IdGeneratorMybatisInterceptor;

/**
 * @author:  liusong
 * @date:    2018年12月14日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
@Configuration
public class GlobalIDMybatisInterceptorConfig{

	@Autowired
	private IdGenerator globalIdGenerator;
	
	@Value("${spring.application.name}")
	private String appName;

	@Value("${spring.cloud.client.ip-address}")
	private String ip;

	@Value("${server.port}")
	private int port;

	@Value("${app.zkConnectString}")
	private String zkConnectString;

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public IdGenerator initGlobalIdGenerator() {
		return new GlobalSnowflakeIdGenerator(appName,zkConnectString);
	}
	
	@Bean
	public IdGeneratorMybatisInterceptor initGlobalIDMybatisInterceptor() {
		return new IdGeneratorMybatisInterceptor(globalIdGenerator);
	}
}
