package com.chinacscs.usercenter.service;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinacscs.usercenter.dto.User;
import com.chinacscs.usercenter.repository.dao.UserDao;
import com.chinacscs.usercenter.repository.entity.UserEntity;

import com.chinacscs.usercenter.util.Convertor;

/**
 * @author:MG01867
 * @date:2018年11月5日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@RestController
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public long add(User user) {
		UserEntity stockUser = userDao.getByName(user.getName());
		if (null != stockUser) {
			throw new ValidationException(String.format("The user[name:%s] already exists", user.getName()));
		}
		UserEntity userEntity = Convertor.convert(user, () -> new UserEntity());
		userEntity.setUpdateUserId(user.getCreateUserId());
		userDao.add(userEntity);
		return userEntity.getId();
	}

	@Override
	public User get(long userId) {
		return Convertor.convert(userDao.get(userId), () -> new User());
	}

	@Override
	public User getByName(String name) {
		return Convertor.convert(userDao.getByName(name), () -> new User());
	}
}
