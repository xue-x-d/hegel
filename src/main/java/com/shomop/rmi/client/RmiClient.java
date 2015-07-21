package com.shomop.rmi.client;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shomop.rmi.service.AccountService;
  
public class RmiClient {  
  
    private static final Logger LOG = Logger.getLogger(RmiClient.class);  
      
    public static void main(String[] args) {  
        ApplicationContext ctx = new ClassPathXmlApplicationContext(  
                "rmi.xml");  
        AccountService accountService = (AccountService) ctx  
                .getBean("mobileAccountService");  
        String result = accountService.shoopingPayment("13800138000", (byte) 5);  
        LOG.info(result);  
    }  
  
} 