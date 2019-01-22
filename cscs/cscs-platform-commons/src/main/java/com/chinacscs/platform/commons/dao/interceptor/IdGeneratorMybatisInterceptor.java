package com.chinacscs.platform.commons.dao.interceptor;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import com.chinacscs.platform.commons.component.IdGenerator;
import com.chinacscs.platform.commons.entity.BaseEntity;

/**
 * @author: liusong
 * @date: 2018年12月14日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class IdGeneratorMybatisInterceptor implements Interceptor {

	private IdGenerator globalIdGenerator;

	public IdGeneratorMybatisInterceptor(IdGenerator globalIdGenerator) {
		this.globalIdGenerator = globalIdGenerator;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		SqlCommandType sqlCommandType = ms.getSqlCommandType();
		if (SqlCommandType.INSERT == sqlCommandType) {
			Object parameter =invocation.getArgs()[1];
			if(parameter instanceof BaseEntity) {
				BaseEntity baseEntity=(BaseEntity)parameter;
				setId(baseEntity);
			}if(parameter instanceof DefaultSqlSession.StrictMap) {
				DefaultSqlSession.StrictMap map=(DefaultSqlSession.StrictMap)parameter;
				List<BaseEntity> baseEntitys=(List<BaseEntity>)map.get("list");
				for(BaseEntity baseEntity:baseEntitys) {
					setId(baseEntity);
				}
			}
		}
		return invocation.proceed();
	}
	
	private void setId(BaseEntity baseEntity) {
		long newID = globalIdGenerator.nextId();
		baseEntity.setId(newID);
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}
}
