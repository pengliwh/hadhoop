package com.chinacscs.usercenter.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.dto.User;
import com.chinacscs.usercenter.service.UserService;

/**
 * @author:MG01867
 * @date:2018年11月5日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public class UserServiceTest extends BaseTest {

	@Autowired
	UserService userService;

	@Test
	public void testAdd() {
		User user = null;
		long result = userService.add(user);
		checkOk(result);
	}

	@Test
	public void testGet() {
		User result = userService.get(0);
		checkOk(result);
	}
}
