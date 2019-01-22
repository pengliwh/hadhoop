package com.chinacscs.usercenter.repository.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.constant.ResourceType;
import com.chinacscs.usercenter.repository.dao.ResourceDao;
import com.chinacscs.usercenter.repository.entity.ResourceEntity;

/**
*@author:MG01867
*@date:2018年11月6日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class ResourceDaoTest extends BaseTest{

	@Autowired
	ResourceDao resourceDao;
	
	@Test
	public void testAdd() {
		ResourceEntity resource=new ResourceEntity();
		resource.setName("获取用户接口");
		resource.setAppId("dafe8b23fdec11e8b738005056b62500");
		resource.setType(ResourceType.FUNCTION);
		resource.setPath("/user/get");
		resource.setParentId(null);
		resource.setCreateUserId(1l);
		resource.setUpdateUserId(1l);
		int count=resourceDao.add(resource);
		checkOk(count);
	}
	
	@Test
	public void testGet() {
		ResourceEntity resource=resourceDao.get(0l);
		checkOk(resource);
	}
	
	@Test
	public void testListByAppId() {
		List<ResourceEntity> resources=resourceDao.listByAppId("18ded2a1e65311e89e01005056986f0b");
		checkOk(resources);
	}

}
