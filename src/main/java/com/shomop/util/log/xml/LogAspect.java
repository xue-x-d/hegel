package com.shomop.util.log.xml;

import org.aspectj.lang.ProceedingJoinPoint;



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
    
    public Object processTx(ProceedingJoinPoint jp) throws java.lang.Throwable {  
        System.out.println("xml执行目标方法之前，模拟开始事物...");  
        // 执行目标方法，并保存目标方法执行后的返回值  
        Object rvt = jp.proceed(new String[]{"aop改变的参数"});
        System.out.println("xml执行目标方法之前，模拟结束事物...");  
        return rvt + "新增的内容";  
    }  
    
} 