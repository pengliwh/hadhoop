package com.chinacscs.usercenter.repository.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.chinacscs.usercenter.BaseTest;
import com.chinacscs.usercenter.repository.dao.AppDao;
import com.chinacscs.usercenter.repository.entity.AppEntity;

/**
*@author:MG01867
*@date:2018年11月10日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class AppDaoTest extends BaseTest{

	@Autowired
	AppDao appDao;
	
	@Test
	public void testAdd() {
		AppEntity app=new AppEntity();
		app.setCode("user-center");
		app.setName("用户中心");
		app.setTokenExpiredTime(3000);
		app.setRefreshTokenExpiredTime(18000);
		app.setDescribe("管理用户");
		app.setCreateUserId(1l);
		app.setUpdateUserId(1l);
		int count=appDao.add(app);
		checkOk(count);
	}

}
