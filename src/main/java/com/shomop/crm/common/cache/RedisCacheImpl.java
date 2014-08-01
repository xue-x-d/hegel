package com.shomop.crm.common.cache;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.shomop.crm.dao.impl.JsonRedisSeriaziler;
/**
 * redisCach
 * @author spencer.xue
 * @date 2014-7-30
 */
@SuppressWarnings("unchecked")
public abstract class RedisCacheImpl<K, V extends Serializable> implements Cache<K,V>{

	protected final static Log logger = LogFactory.getLog(RedisCacheImpl.class);
	
	@Autowired  
    protected RedisTemplate<K,V> redisTemplate;
	protected JsonRedisSeriaziler<V> jsonRedisSeriaziler;
	protected JdkSerializationRedisSerializer jdkRedisSeriaziler;
	private Class<V> type; // convenient avoid TypeReference
	
	public RedisCacheImpl() {
		jsonRedisSeriaziler = new JsonRedisSeriaziler<V>();
		jdkRedisSeriaziler = new JdkSerializationRedisSerializer();
		type = (Class<V>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public Class<V> getType() {
		return type;
	}
 
	/**
	 * 设置redisTemplate
	 * 
	 * @param redisTemplate the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
      
	/**
	 * StringSerializer
	 * 
	 * @return
	 */
	protected RedisSerializer<String> getDefaultRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * josnSerializer
	 * 
	 * @return
	 */
	protected JsonRedisSeriaziler<V> getJsonRedisSerializer() {
		return jsonRedisSeriaziler;
	}

	/**
	 * jdkSerializer
	 * 
	 * @return
	 */
	protected JdkSerializationRedisSerializer getJdkRedisSeriaziler() {
		return jdkRedisSeriaziler;
	}
	
	@Override
	public V get(K key) throws CacheException {
		 
		return redisTemplate.opsForValue().get(key);
	}
	
	@Override
	public void put(K key, V value) throws CacheException {
		if (key == null) {  
            throw new CacheException("key must not be null");  
        }
		redisTemplate.opsForValue().set(key,value);
	}
	
	@Override
	public void put(K key, V value, int TTL) throws CacheException {
		if (key == null) {  
            throw new CacheException("key must not be null");  
        }
		redisTemplate.opsForValue().set(key,value,TTL,TimeUnit.MILLISECONDS); 
	}
	
	@Override
	public void remove(K key) throws CacheException {
		if (key == null) {  
            throw new CacheException("key must not be null");  
        }
		redisTemplate.opsForValue().getOperations().delete(key);
	}
	
	@Override
	public void update(K key, V value) throws CacheException {
		if (key == null) {  
            throw new CacheException("key must not be null");  
        }
		redisTemplate.opsForValue().set(key,value);
	}
	
	
}