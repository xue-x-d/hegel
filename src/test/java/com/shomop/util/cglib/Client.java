package com.shomop.util.cglib;

public class Client {

	//com.shomop.util.test.Foo$$EnhancerByCGLIB$$5d59ab47@5e13fb15
	public static void main(String[] args) {
		
		Foo foo = CGLibDynamicProxy.getInstance().getProxy(Foo.class);
		foo.sayHello("bar");
		
	}
}
