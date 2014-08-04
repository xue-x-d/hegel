package com.shomop.crm.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.shomop.crm.common.cache.CacheException;
import com.shomop.crm.common.cache.RedisCacheImpl;
import com.shomop.crm.model.notify.DDNotifyTrade;

@Repository("notifyTradeRedisCache")
public class NotifyTradeRedisCacheImpl extends RedisCacheImpl<String,DDNotifyTrade>{
	
	/**
	 * 新增
	 * @param ntrade
	 * @return
	 */
	@Override
	public void put(final String key, final DDNotifyTrade ntrade) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] _key = getDefaultRedisSerializer().serialize(key);
				byte[] _value = getJsonRedisSerializer().serialize(ntrade);
				System.out.println("put value "+new String(_value));
				return connection.setNX(_key, _value);
			}
		});
	}
	
	 /**  
     * 通过key获取 
     * @param keyId 
     * @return 
	 * @throws CacheException 
     */  
    public DDNotifyTrade get(final String key) throws CacheException { 
		if (key == null) {
			return null;
		}
    	DDNotifyTrade ntrade = redisTemplate.execute(new RedisCallback<DDNotifyTrade>() {  
            public DDNotifyTrade doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getDefaultRedisSerializer();  
                byte[] value = connection.get(serializer.serialize(key));
                return getJsonRedisSerializer().deserialize(value);
            }  
        });
        return ntrade;
    }
    
    /**
	 * 批量新增
	 * @param list
	 * @return
	 */
    public boolean add(final List<DDNotifyTrade> list) {  
        Assert.notEmpty(list);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getDefaultRedisSerializer();
                for (DDNotifyTrade ntrade : list) {  
                	 byte[] key  = serializer.serialize(ntrade.getTid()+"");  
                     byte[] name = serializer.serialize(ntrade.getUserId()+"");   
                     connection.setNX(key, name);
                }  
                return true;  
            }  
        }, false, true);  
        return result;  
    }

	
}
