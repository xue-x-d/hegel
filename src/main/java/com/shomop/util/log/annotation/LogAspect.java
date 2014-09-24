package com.shomop.util.log.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {  
        
	// 使用@Pointcut Annotation 时指定切入点表达式  
	@Pointcut("execution(* com.shomop.crm.controller.*.*(..))")  
	// 使用一个返回值为void，方法体为空的方法来命名切入点  
	private void anyControllerMethod(){}  
	
	// 匹配 com.shomop.crm.controller.* 包下所有类的所有方法作为切入点  
    @Before("execution(* com.shomop.crm.controller.*.*(..))")  
    public void authorith(){  
        System.out.println("注解模拟进行权限检查。");  
    } 
	
    // 匹配 com.shomop.crm.controller.* 包下所有类的所有方法作为切入点  
    @AfterReturning(returning="rvt", pointcut="anyControllerMethod()")  
    public void log(Object rvt) {  
        System.out.println("注解模拟目标方法返回值：" + rvt);  
        System.out.println("注解模拟记录日志功能...");  
    }
    
    @AfterThrowing(throwing="ex", pointcut="anyControllerMethod()")  
    public void doRecoverActions(Throwable ex) {  
        System.out.println("注解目标方法中抛出的异常：" + ex);  
        System.out.println("注解模拟抛出异常后的增强处理...");  
    }  
    
    @After("execution(* com.shomop.crm.controller.*.*(..))")  
    public void release() {  
        System.out.println("注解模拟方法结束后的释放资源...");  
    } 
    
    // 匹配 com.wicresoft.app.service.impl 包下所有类的所有方法作为切入点  
    @Around("execution(* com.shomop.crm.controller.*.*(..))")  
    public Object processTx(ProceedingJoinPoint jp) throws java.lang.Throwable {  
        System.out.println("注解执行目标方法之前，模拟开始事物...");  
        // 执行目标方法，并保存目标方法执行后的返回值  
        Object rvt = jp.proceed(new String[]{"被改变的参数"});
        System.out.println("注解执行目标方法之前，模拟结束事物...");  
        return rvt + "新增的内容";  
    }  
    
    
} 