package com.shomop.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;

@Component
public class GreetingIntroAdvice extends DelegatingIntroductionInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object[] argus = invocation.getArguments();
		System.out.println(method.getName()+" \t "+argus[0]);
		return method.getName()+" \t "+argus[0];
	}

}