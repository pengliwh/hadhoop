package com.chinacscs.platform.search.index;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author:  liusong
 * @date:    2019年1月12日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public interface StressTestWorker extends InitializingBean{

	void run(String job,String group);
}
