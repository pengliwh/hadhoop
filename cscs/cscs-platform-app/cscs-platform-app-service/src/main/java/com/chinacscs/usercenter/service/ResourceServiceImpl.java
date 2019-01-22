package com.chinacscs.usercenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.chinacscs.usercenter.dto.Resource;
import com.chinacscs.usercenter.repository.dao.ResourceDao;
import com.chinacscs.usercenter.repository.entity.ResourceEntity;
import com.chinacscs.usercenter.util.Convertor;

/**
 * @author:MG01867
 * @date:2018年11月13日
 * @email:359852326@qq.com
 * @version:
 * @describe //TODO
 */
@RestController
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;

	@Override
	public long add(Resource resource) {
		ResourceEntity resourceEntity = Convertor.convert(resource, () -> new ResourceEntity());
		resourceDao.add(resourceEntity);
		return resourceEntity.getId();
	}

	@Override
	public List<Resource> listByAppId(String appId) {
		return Convertor.convert(resourceDao.listByAppId(appId), () -> new Resource());
	}

	@Override
	public List<Resource> listAll() {
		return Convertor.convert(resourceDao.listAll(), () -> new Resource());
	}

}
