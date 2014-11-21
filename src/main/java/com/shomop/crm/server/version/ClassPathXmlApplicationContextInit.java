package com.shomop.crm.server.version;

import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * server初始化
 * @author shomop
 *
 */
public class ClassPathXmlApplicationContextInit {
    public static ClassPathXmlApplicationContext getClassPathXmlApplicationContext(Config config){
//        String appDir = "conf"+File.separator+config.version.getFile()+File.separator +"app.xml";
//        String jdbcDir = "conf"+File.separator+config.version.getFile()+File.separator +config.env.getFile()+File.separator+"jdbc.xml";
        return new ClassPathXmlApplicationContext("application.xml");
    }
}
