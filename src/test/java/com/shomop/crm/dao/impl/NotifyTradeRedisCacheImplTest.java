package com.shomop.crm.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.shomop.common.cache.CacheException;
import com.shomop.crm.model.notify.DDNotifyTrade;
import com.shomop.util.DateUtils;

@ContextConfiguration(locations = {"classpath:application.xml","classpath:spring-hibernate.xml"})
public class NotifyTradeRedisCacheImplTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired  
	private NotifyTradeRedisCacheImpl notifyTradeRedisCache;
	
	/** 
     * 批量新增
     */  
    //@Test  
    public void testAddUsers1() { 
    	System.out.println(notifyTradeRedisCache);
        List<DDNotifyTrade> list = new ArrayList<DDNotifyTrade>(20);  
        for (int i = 10; i < 20; i++) {  
        	DDNotifyTrade trade = new DDNotifyTrade();
        	trade.setTid(Long.valueOf(10+""+i));
        	trade.setUserId(Long.valueOf(20+""+i));
        	list.add(trade);
        }
        long begin = System.currentTimeMillis();
        System.out.println(notifyTradeRedisCache.putList("user_110",list));
        //耗时： 103573
        System.out.println("耗时： "+(System.currentTimeMillis() -  begin));  
    } 
    
    //@Test
    public void testGet() throws CacheException{
    	long begin = System.currentTimeMillis();
    	List<DDNotifyTrade> trades = notifyTradeRedisCache.getAll("user_110");
    	for (DDNotifyTrade ddNotifyTrade : trades) {
			System.out.println(ddNotifyTrade.getTid());
		}
    	System.out.println("pop: "+notifyTradeRedisCache.get("user_110").getTid());
    	List<DDNotifyTrade> trades2 = notifyTradeRedisCache.getAll("user_110");
    	for (DDNotifyTrade ddNotifyTrade : trades2) {
			System.out.println(ddNotifyTrade.getTid());
		}
    	System.out.println("耗时： "+(System.currentTimeMillis() -  begin));
//    	String ob = "{\"status\":null,\"insertTime\":null,\"tid\":1010,\"userId\":2010}";
//    	System.out.println(ob);
//    	GsonRedisSeriaziler<DDNotifyTrade> jr = new GsonRedisSeriaziler<DDNotifyTrade>();
//    	System.out.println(jr.deserialize(ob.getBytes(),DDNotifyTrade.class));
    }
    
    //@Test
    public void testJsonserialize(){
    	DDNotifyTrade trade = new DDNotifyTrade();
    	trade.setTid(Long.valueOf(10+""+10));
    	trade.setUserId(Long.valueOf(20+""+10));
    	JacksonJsonRedisSerializer<DDNotifyTrade> js = new JacksonJsonRedisSerializer<DDNotifyTrade>(DDNotifyTrade.class);
    	byte[] jt = js.serialize(trade);
    	long start = System.currentTimeMillis();
    	for (int i = 0; i < 50000; i++) {
//    		 System.out.println(js.serialize(trade).toString()); // js: 367 ms
    		 js.deserialize(jt); // js: 364 ms
		}
    	System.out.println("js: "+(System.currentTimeMillis() - start)+" ms");
    	GsonRedisSeriaziler<DDNotifyTrade> gs = new GsonRedisSeriaziler<DDNotifyTrade>(DDNotifyTrade.class);
    	byte[] gt = gs.serialize(trade);
    	start = System.currentTimeMillis();
    	for (int i = 0; i < 50000; i++) {
//    		 System.out.println(gs.serialize(trade).toString()); // gs: 279 ms
    		 gs.deserialize(gt); // gs: 298 ms
		}
    	System.out.println("gs: "+(System.currentTimeMillis() - start)+" ms");
    }
    
    @Test  
    public void testAddUsers() { 
    	long begin = System.currentTimeMillis();
        for (int i = 10; i < 20; i++) {  
        	DDNotifyTrade trade = new DDNotifyTrade();
        	trade.setTid(Long.valueOf(10+""+i));
        	trade.setUserId(Long.valueOf(20));
        	notifyTradeRedisCache.putNTrade(trade);
        }
        //耗时： 137
        System.out.println("耗时： "+(System.currentTimeMillis() -  begin));  
    }
    public static void main(String[] args) {
		
    	System.out.println(DateUtils.getEndTime(new Date()).getTime()-new Date().getTime());
	}

}
