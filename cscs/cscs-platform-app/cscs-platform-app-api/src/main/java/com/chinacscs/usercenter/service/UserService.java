package com.chinacscs.usercenter.service;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinacscs.usercenter.ServiceName;
import com.chinacscs.usercenter.dto.User;
import com.chinacscs.usercenter.validation.AddValidationGroup;

/**
 * @author:MG01867
 * @date:2018年8月13日
 * @E-mail:359852326@qq.com
 * @version:
 * @describe 用户管理客户端接口
 */
@FeignClient(ServiceName.SERVICE_NAME)
@Validated
@RequestMapping("/user")
public interface UserService {

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	long add(@Validated(value = { AddValidationGroup.class }) @RequestBody User user);

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	User get(@NotBlank(message = "The user's id must be not blank") @RequestParam(name = "id") long id);

	@RequestMapping(value = "/getByName", method = RequestMethod.GET)
	User getByName(
			@NotBlank(message = "The user's name must be not blank") @RequestParam(name = "name") String name);
}
