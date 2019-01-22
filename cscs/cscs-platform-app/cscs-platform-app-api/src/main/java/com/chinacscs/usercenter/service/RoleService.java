package com.chinacscs.usercenter.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinacscs.usercenter.ServiceName;
import com.chinacscs.usercenter.dto.Role;

/**
 * @author:MG01867
 * @date:2018年11月5日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@FeignClient(ServiceName.SERVICE_NAME)
@Validated
@RequestMapping("/role")
public interface RoleService {

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	Role get(@RequestParam(name = "id") long id);

	@RequestMapping(value = "/listByAppId", method = RequestMethod.POST)
	List<Role> listByAppId(@RequestParam(name = "appId") long appId);
}
