package com.shomop.crm.dao.impl;

import org.apache.commons.lang.SerializationException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author spencer.xue
 * @date 2014-8-1
 */
public class JsonRedisSeriaziler<T> {
	
	private static final String EMPTY_JSON_STR = "{}";
	private static final byte[] EMPTY_JSON_BYTE = new byte[0];

	private final ObjectMapper objectMapper = new ObjectMapper();

	public JsonRedisSeriaziler() {}

	/**
	 * to json-string
	 * 
	 * @param object
	 * @return
	 */
	public String serialize(T t) {
		if (t == null) {
			return EMPTY_JSON_STR;
		}
		try {
			return this.objectMapper.writeValueAsString(t);
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: "
					+ ex.getMessage(), ex);
		}
	}
	
	public byte[] serializeAsBytes(T t) {
		if (t == null) {
			return EMPTY_JSON_BYTE;
		}
		try {
			return this.objectMapper.writeValueAsBytes(t);
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: "
					+ ex.getMessage(), ex);
		}
	}

	/**
	 * to object
	 * 
	 * @param str
	 * @return
	 */
	public T deserialize(String src, Class<T> clazz) {
		if (src == null || clazz == null) {
			return null;
		}
		try {
			return this.objectMapper.readValue(src, clazz);
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: "
					+ ex.getMessage(), ex);
		}
	}
	
	public T deserialize(byte[] src, Class<T> clazz) {
		if (src == null || clazz == null) {
			return null;
		}
		try {
			return this.objectMapper.readValue(src, clazz);
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: "
					+ ex.getMessage(), ex);
		}
	}

}