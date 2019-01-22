package com.chinacscs.usercenter.repository.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.constant.UserStatus;
import com.chinacscs.usercenter.repository.dao.UserDao;
import com.chinacscs.usercenter.repository.entity.UserEntity;

/**
*@author:MG01867
*@date:2018年6月28日
*@E-mail:359852326@qq.com
*@version:
*@describe //TODO
*/
public class UserDaoTest extends BaseTest{

	@Autowired
	UserDao userDao;
	
	@Test
	public void testAdd() {
		UserEntity user=new UserEntity();
		user.setName("sixliu5945");
		user.setStatus(UserStatus.UNCERTIFIED);
		user.setPassword("37dzsk9n5w");
		user.setNickname("sixliu");
		user.setCreateUserId(1l);
		user.setUpdateUserId(1l);
		int count=userDao.add(user);
		checkOk(count);
	}
	
	@Test
	public void testGet() {
		UserEntity user=userDao.get(0l);
		checkOk(user);
	}
}
