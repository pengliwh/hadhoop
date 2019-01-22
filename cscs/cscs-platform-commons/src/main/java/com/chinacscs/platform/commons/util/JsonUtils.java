package com.chinacscs.platform.commons.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author: liusong
 * @date: 2018年12月13日
 * @email: 359852326@qq.com
 * @version:
 * @describe: //TODO
 */
public class JsonUtils {

	private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static String toJsonString(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException exception) {
			throw new RuntimeException(exception);
		}
	}

	public static <T> T toObject(String jsonString, Class<T> clz) {
		try {
			return OBJECT_MAPPER.readValue(jsonString, clz);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@SuppressWarnings("unchecked")
	public static Map<String,Object> jsonToMap(String jsonString){
		return toObject(jsonString, Map.class);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> objectToMap(Object ob){
		if(ob instanceof String) {
			return jsonToMap((String)ob);
		}else {
			return (Map<String,Object>)OBJECT_MAPPER.convertValue(ob, Map.class);
		}
	}
}

