package com.chinacscs.usercenter.repository.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.repository.dao.RoleResourceDao;
import com.chinacscs.usercenter.repository.entity.RoleResourceEntity;

/**
 * @author:MG01867
 * @date:2018年11月6日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
public class RoleResourceDaoTest extends BaseTest {

	@Autowired
	RoleResourceDao roleResourceDao;

	@Test
	public void testAdd() {
		RoleResourceEntity roleResource = new RoleResourceEntity();
		roleResource.setRoleId("ca60a67be17511e89e01005056986f0b");
		roleResource.setResourceId("b85e7251e18a11e89e01005056986f0b");
		roleResource.setCreateUserId(1l);
		roleResource.setUpdateUserId(1l);
		int count = roleResourceDao.add(roleResource);
		checkOk(count);
	}
	
	@Test
	public void testGet() {
		RoleResourceEntity roleResource =roleResourceDao.get(0l);
		checkOk(roleResource);
	}

}
