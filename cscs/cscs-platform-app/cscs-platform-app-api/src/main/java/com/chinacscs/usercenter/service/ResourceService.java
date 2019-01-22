package com.chinacscs.usercenter.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chinacscs.usercenter.ServiceName;
import com.chinacscs.usercenter.dto.Resource;
import com.chinacscs.usercenter.validation.AddValidationGroup;

/**
*@author:MG01867
*@date:2018年11月6日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
@FeignClient(ServiceName.SERVICE_NAME)
@Validated
@RequestMapping("/resource")
public interface ResourceService {

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	long add(@Validated(value = { AddValidationGroup.class }) @RequestBody Resource resourceDTO);
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	List<Resource> listAll();
	
	@RequestMapping(value = "/listByAppId", method = RequestMethod.POST)
	List<Resource> listByAppId(@RequestParam(name="appId") String appId);
}
