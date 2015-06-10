package com.shomop.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class GreetingAroundAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		before();
		Object result = invocation.proceed();
		after();
		invocation.getArguments();
		return result;
	}

	private void before() {
		System.out.println("Before");
	}

	private void after() {
		System.out.println("After");
	}
}