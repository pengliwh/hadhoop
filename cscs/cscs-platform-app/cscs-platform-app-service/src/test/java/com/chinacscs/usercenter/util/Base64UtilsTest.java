package com.chinacscs.usercenter.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Base64Utils;

/**
*@author:MG01867
*@date:2018年11月15日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
public class Base64UtilsTest {

	public static void main(String[] args) {
		System.out.println("base64:"+Base64Utils.encodeToString("37dzsk9n5w".getBytes()));
		System.out.println("password:"+new BCryptPasswordEncoder().encode("37dzsk9n5w"));
	}
}
