package com.chinacscs.usercenter.repository.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.repository.dao.RoleDao;
import com.chinacscs.usercenter.repository.entity.RoleEntity;

/**
*@author:MG01867
*@date:2018年11月6日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class RoleDaoTest extends BaseTest{

	@Autowired
	RoleDao roleDao;
	
	@Test
	public void testAdd() {
		RoleEntity role=new RoleEntity();
		role.setName("初审岗位");
		role.setParentId(null);
		role.setCreateUserId(1l);
		role.setUpdateUserId(1l);
		int count=roleDao.add(role);
		checkOk(count);
	}
	
	@Test
	public void testGet() {
		RoleEntity role=roleDao.get(0l);
		checkOk(role);
	}

}
