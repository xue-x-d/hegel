package crm.shomop.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.shomop.crm.dao.impl.JsonRedisSeriaziler;
import com.shomop.crm.dao.impl.NotifyTradeRedisDaoImpl;
import com.shomop.crm.model.notify.DDNotifyTrade;

@ContextConfiguration(locations = {"classpath:/application.xml"})  
public class NotifyTradeRedisDaoImplTest  extends AbstractJUnit4SpringContextTests {
	
	@Autowired  
	private NotifyTradeRedisDaoImpl notifyTradeRedisDao;
	
	/** 
     * 批量新增
     */  
    //@Test  
    public void testAddUsers1() { 
    	System.out.println(notifyTradeRedisDao);
        List<DDNotifyTrade> list = new ArrayList<DDNotifyTrade>(50000);  
        for (int i = 10; i < 15; i++) {  
        	DDNotifyTrade trade = new DDNotifyTrade();
        	trade.setTid(Long.valueOf(10+""+i));
        	trade.setUserId(Long.valueOf(20+""+i));
        	list.add(trade);
        } 
        int index = 1;
        long begin = System.currentTimeMillis();  
        for (DDNotifyTrade trade : list) {  
//        	notifyTradeRedisDao.add("user_add:"+index,trade);
        	index ++;
        }
        //耗时： 110034
        System.out.println("耗时： "+(System.currentTimeMillis() -  begin));
        begin = System.currentTimeMillis();
        index = 50000;
        for (DDNotifyTrade trade : list) {  
			notifyTradeRedisDao.put("user_add:"+index,trade);
        	index ++;
        }
        //耗时： 103573
        System.out.println("耗时： "+(System.currentTimeMillis() -  begin));  
    } 
    
    @Test
    public void testGet(){
    	long begin = System.currentTimeMillis();  
    	for (int i = 1; i < 50000; i++) {  
    		notifyTradeRedisDao.get("user_put:"+i);;
    	}
    	System.out.println("耗时： "+(System.currentTimeMillis() -  begin));
    	
    	String ob = "{\"status\":null,\"insertTime\":null,\"tid\":1010,\"userId\":2010}";
    	JsonRedisSeriaziler<DDNotifyTrade> jr = new JsonRedisSeriaziler<DDNotifyTrade>();
    	System.out.println(jr.deserialize(ob.getBytes(),DDNotifyTrade.class));
    }
	

}
