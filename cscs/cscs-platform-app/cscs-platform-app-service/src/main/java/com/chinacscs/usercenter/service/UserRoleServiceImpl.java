package com.chinacscs.usercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinacscs.usercenter.dto.UserRole;
import com.chinacscs.usercenter.repository.dao.UserRoleDao;

/**
*@author:MG01867
*@date:2018年11月5日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
@RestController
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public List<UserRole> listByRoleId(String roleId) {
		userRoleDao.listByRoleId(roleId);
		return null;
	}

	@Override
	public List<UserRole> listByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole getByUserIdAndRoleId(String userId, String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

}
