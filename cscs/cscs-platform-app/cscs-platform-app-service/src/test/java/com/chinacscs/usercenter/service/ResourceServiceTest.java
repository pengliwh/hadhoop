package com.chinacscs.usercenter.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.dto.Resource;

/**
 * @author:  liusong
 * @date:    2018年12月13日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class ResourceServiceTest extends BaseTest{

	@Autowired
	ResourceService resourceService;

	@Test
	public void testAdd() {
		List<Resource> result=resourceService.listAll();
		checkOk(result);
	}
}
