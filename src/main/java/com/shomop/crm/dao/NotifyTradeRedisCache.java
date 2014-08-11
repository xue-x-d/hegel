package com.shomop.crm.dao;

import java.util.List;

import com.shomop.crm.common.cache.Cache;
import com.shomop.crm.model.notify.DDNotifyTrade;
/**
 * 
 * @author spencer.xue
 * @date 2014-8-5
 */
public interface NotifyTradeRedisCache extends Cache<String,DDNotifyTrade> {

	List<DDNotifyTrade> getAll(final String key);
	
	long putList(final String key,final List<DDNotifyTrade> list);
	
	boolean putNTrade(DDNotifyTrade trade);
}
