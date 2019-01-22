package com.chinacscs.usercenter.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinacscs.usercenter.ServiceName;
import com.chinacscs.usercenter.dto.RoleResource;
import com.chinacscs.usercenter.validation.AddValidationGroup;

/**
 * @author: liusong
 * @date: 2018年12月13日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
@FeignClient(ServiceName.SERVICE_NAME)
@Validated
@RequestMapping("/roleResource")
public interface RoleResourceService {

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	String add(@Validated(value = { AddValidationGroup.class }) @RequestBody RoleResource roleResource);
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	List<RoleResource> listAll();
}
