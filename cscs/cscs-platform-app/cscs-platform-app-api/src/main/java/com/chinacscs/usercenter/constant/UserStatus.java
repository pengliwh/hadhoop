package com.chinacscs.usercenter.constant;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author:MG01867
 * @date:2018年11月13日
 * @email:359852326@qq.com
 * @version:
 * @describe 用户状态
 */
public enum UserStatus {

	/** 未认证 **/
	UNCERTIFIED,

	/** 正常 **/
	NORMAL,

	/** 锁住 **/
	LOCKED,

	/** 过期 **/
	EXPIRED,

	/** 废弃 **/
	DISCARDED;

	private final static Set<String> names;

	static {
		names = new HashSet<>(UserStatus.values().length);
		for (UserStatus item : UserStatus.values()) {
			names.add(item.name());
		}
	}

	public static boolean validate(String value) {
		return names.contains(value);
	}

	@MappedTypes(value = UserStatus.class)
	@MappedJdbcTypes(value = { JdbcType.VARCHAR, JdbcType.CHAR })
	public static class Handler extends BaseTypeHandler<UserStatus> {

		@Override
		public void setNonNullParameter(PreparedStatement ps, int i, UserStatus parameter, JdbcType jdbcType)
				throws SQLException {
			ps.setString(i, parameter.name());
		}

		@Override
		public UserStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
			String value = rs.getString(columnName);
			return UserStatus.valueOf(value);
		}

		@Override
		public UserStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
			String value = rs.getString(columnIndex);
			return UserStatus.valueOf(value);
		}

		@Override
		public UserStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
			String value = cs.getString(columnIndex);
			return UserStatus.valueOf(value);
		}

	}
}
