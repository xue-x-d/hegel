package com.shomop.crm.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.shomop.common.cache.CacheException;
import com.shomop.common.cache.RedisCacheImpl;
import com.shomop.crm.dao.NotifyTradeRedisCache;
import com.shomop.crm.model.notify.DDNotifyTrade;
import com.shomop.util.DateUtils;

@Repository("notifyTradeRedisCache")
public class NotifyTradeRedisCacheImpl extends RedisCacheImpl<String,DDNotifyTrade> implements NotifyTradeRedisCache{
	
	/**  
     * Retrieves all elements of list, but does not remove.
     * or returns <tt>null</tt> if this list is empty.
     * @param keyId 
     * @return 
     */  
	public List<DDNotifyTrade> getAll(final String key) {
		return redisTemplate.execute(new RedisCallback<List<DDNotifyTrade>>() {
			@Override
			public List<DDNotifyTrade> doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] _key = getStringRedisSerializer().serialize(key);
				List<byte[]> _tList = connection.lRange(_key, 0L, -1L);
				List<DDNotifyTrade> result = new ArrayList<DDNotifyTrade>(_tList.size());
				for (byte[] bs : _tList) {
					result.add(getJsonRedisSerializer().deserialize(bs));
				}
				return result;
			}
		});
	}
	
    /**
     * Retrieves and removes the head of this list,
     * or returns <tt>null</tt> if this list is empty
     * @param key
     * @return
     */
    @Override
    public DDNotifyTrade get(final String key) {
    	return redisTemplate.execute(new RedisCallback<DDNotifyTrade>() {
			@Override
			public DDNotifyTrade doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] _value = connection.lPop(getStringRedisSerializer().serialize(key));
				return getJsonRedisSerializer().deserialize(_value);
			}
		});
    }
	
	/**
	 * 插入新元素
	 * 默认放在队尾
	 * @param ntrade
	 * @return
	 */
	@Override
	public void put(final String key, final DDNotifyTrade ntrade) {
		long size = redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] _key = getStringRedisSerializer().serialize(key);
				byte[] _value = getJsonRedisSerializer().serialize(ntrade);
				return connection.rPush(_key, _value);
			}
		});
		if (logger.isDebugEnabled()) {
			logger.debug("<<< after insert value :"+ntrade.getTid()+" to key: "+key+",list size: "+size);
		}
	}
	
    /**
	 * 批量新增
	 * @param key
	 * @param list
	 * @return 当前key的链表中元素的数量  
	 * 		   -1 插入异常
	 */
    public long putList(final String key,final List<DDNotifyTrade> list) {
        Assert.notEmpty(list);
        /*ListOperations<String, DDNotifyTrade>  opsForList = redisTemplate.opsForList();
        long len = 0L;
		for (DDNotifyTrade ntrade : list) {
			if(len == LIST_MAX){
				return -1L;
			}
			len = opsForList.rightPush(key, ntrade);
		}
		return len;*/
        long len = 0L;
		try {
			redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) {
					byte[] _key = getStringRedisSerializer().serialize(key);
					for (DDNotifyTrade ntrade : list) {
						byte[] _value = getJsonRedisSerializer().serialize(ntrade);
						connection.rPush(_key, _value);
					}
					return true;
				}
			}, false, true);
			len = size(key);
		} catch (Exception e) {
			len = -1L;
		}
		return len;
    }
    
    /**
     * 链表大小
     */
    @Override
	public long size(final String key) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.lLen(getStringRedisSerializer()
						.serialize(key));
			}
		});
	}
	
    @Override
    public void remove(final String key) throws CacheException {
    	redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] _key = getStringRedisSerializer().serialize(key);
				connection.lTrim(_key, 1L, 0L); 
				return true;
			}
		}); 
    }

	@Override
	public boolean putNTrade(DDNotifyTrade trade) {
		if (trade != null) {
			try {
				final String key = "user:" + trade.getUserId();
				boolean flag = exists(key);
				put(key,trade);
				if (!flag) {
					expireAt(key,DateUtils.getEndTime(new Date()).getTime() / 1000);
				}
			} catch (Exception e) {
				logger.error(">>> cache notify trade error.tid:" + trade.getTid(),e);
				return false;
			}
		}
		return true;
	}
	
	private boolean exists(final String key) throws CacheException {
    	return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] _key = getStringRedisSerializer().serialize(key);
				return connection.exists(_key);
			}
		}); 
    }
	
	private boolean expireAt(final String key,final long endTime) throws CacheException {
    	return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] _key = getStringRedisSerializer().serialize(key);
				return connection.expireAt(_key,endTime);
			}
		}); 
    }
    
}
