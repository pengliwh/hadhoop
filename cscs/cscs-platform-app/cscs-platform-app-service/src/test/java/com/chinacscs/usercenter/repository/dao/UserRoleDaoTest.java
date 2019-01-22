package com.chinacscs.usercenter.repository.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.repository.dao.UserRoleDao;
import com.chinacscs.usercenter.repository.entity.UserRoleEntity;

/**
*@author:MG01867
*@date:2018年11月6日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class UserRoleDaoTest extends BaseTest{

	@Autowired
	UserRoleDao userRoleDao;
	
	@Test
	public void testAdd() {
		UserRoleEntity userRole=new UserRoleEntity();
		userRole.setUserId("02460d0de0e811e89e01005056986f0b");
		userRole.setRoleId("ca60a67be17511e89e01005056986f0b");
		userRole.setCreateUserId(1l);
		userRole.setUpdateUserId(1l);
		int count=userRoleDao.add(userRole);
		checkOk(count);
	}
	
	@Test
	public void testGet() {
		UserRoleEntity userRole=userRoleDao.get(0l);
		checkOk(userRole);
	}
	
	@Test
	public void testListByRoleId() {
		List<UserRoleEntity> userRoles=userRoleDao.listByRoleId("ca60a67be17511e89e01005056986f0b");
		checkOk(userRoles);
	}
}
