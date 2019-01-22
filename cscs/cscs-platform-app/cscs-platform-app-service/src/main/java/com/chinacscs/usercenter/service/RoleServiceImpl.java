package com.chinacscs.usercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinacscs.usercenter.dto.Role;
import com.chinacscs.usercenter.repository.dao.RoleDao;
import com.chinacscs.usercenter.util.Convertor;

/**
*@author:MG01867
*@date:2018年11月15日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
@RestController
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public Role get(long id) {
		return Convertor.convert(roleDao.get(id),()->new Role());
	}
	
	@Override
	public List<Role> listByAppId(long appId) {
		return null;
	}
}
