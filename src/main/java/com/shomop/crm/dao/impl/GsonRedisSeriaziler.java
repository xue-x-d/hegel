package com.shomop.crm.dao.impl;

import java.nio.charset.Charset;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import com.google.gson.Gson;

/**
 * gson
 * 速度优于 JacksonJsonRedisSerializer 1\3
 * @author spencer.xue
 * @date 2014-8-1
 */
public class GsonRedisSeriaziler<T> implements RedisSerializer<T> {
	
	private static final byte[] EMPTY_JSON_BYTE = new byte[0];
	private final Gson gson = new Gson();
	private final Class<T> type;
	private final Charset charset;
	
	public GsonRedisSeriaziler(Class<T> type) {
		this(type,Charset.forName("UTF8"));
	}
	
	public GsonRedisSeriaziler(Class<T> type,Charset charset) {
		Assert.notNull(charset);
		this.type = type;
		this.charset = Charset.forName("UTF8");
	}

	@Override
	public byte[] serialize(T t) throws org.springframework.data.redis.serializer.SerializationException {
		if(t == null){
			return EMPTY_JSON_BYTE;
		}
		String _result = gson.toJson(t,type);
		return _result == null? null:_result.getBytes(charset);
	}


	@Override
	public T deserialize(byte[] bytes) throws org.springframework.data.redis.serializer.SerializationException {
		if(bytes == null) {
			return null;	
		}
		return gson.fromJson(new String(bytes, charset),type);
	}
 

}