package com.chinacscs.datamarket.commons.util;

import java.util.HashMap;
import java.util.Map;

import com.chinacscs.platform.commons.util.JsonUtils;

/**
 * @author:  liusong
 * @date:    2018年12月13日
 * @email:   359852326@qq.com
 * @version: 
 * @describe: //TODO
 */
public class JsonUtilsTest {

	public static void main(String[] args) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("name", "刘松");
		map.put("age", 18);
		System.out.println(map);
		String jsonString=JsonUtils.toJsonString(map);
		System.out.println(jsonString);
		map=JsonUtils.jsonToMap(jsonString);
		System.out.println(map);
		Person person=JsonUtils.toObject(jsonString,Person.class);
		System.out.println(person);
	}
	
	public static class Person{
		private String name;
		private int age;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
	}
}
