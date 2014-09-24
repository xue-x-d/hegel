package com.shomop.util.log.xml;



public class LogAspect {  
        
 
    public void authorith(){  
        System.out.println("xml模拟进行权限检查。");  
    } 
	
   
    public void log(Object rvt) {  
        System.out.println("xml模拟目标方法返回值：" + rvt);  
        System.out.println("xml模拟记录日志功能...");  
    }
   
    public void doRecoverActions(Throwable ex) {  
        System.out.println("xml目标方法中抛出的异常：" + ex);  
        System.out.println("xml模拟抛出异常后的增强处理...");  
    }  
    
 
    public void release() {  
        System.out.println("xml模拟方法结束后的释放资源...");  
    } 
    
    
    
} 