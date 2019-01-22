package com.chinacscs.platform.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:MG01867
 * @date:2018年6月15日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 程序启动入口类
 */
@SpringBootApplication
@RestController
public class StartUp implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(StartUp.class);
	}

	/**
	 * 拦截器配置
	 */
	public void addInterceptors(InterceptorRegistry registry) {

	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		System.out.println("index");
		return null;
	}
	
	@RequestMapping(value = "/cars/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable("id") Long id) {
		System.out.println("get by id:"+id);
		return null;
	}
	
	@RequestMapping(value = "/cars", method = RequestMethod.GET)
	@ResponseBody
	public String get(@RequestParam("colour") String colour,@RequestParam("name") String name) {
		System.out.println("colour:"+colour);
		return null;
	}
}