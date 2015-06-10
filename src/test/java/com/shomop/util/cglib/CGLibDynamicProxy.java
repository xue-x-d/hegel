package com.shomop.util.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * http://isuifengfei.iteye.com/blog/1682606
 * @author spencer.xue
 * @date 2015-4-29
 */
public class CGLibDynamicProxy implements MethodInterceptor {
 
    private static CGLibDynamicProxy instance = new CGLibDynamicProxy();
 
    private CGLibDynamicProxy() {
    }
 
    public static CGLibDynamicProxy getInstance() {
        return instance;
    }
 
    @SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> cls) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(getClass());
		enhancer.setCallbacks(new Callback[] { instance, instance });
		enhancer.setCallbackFilter(new CallbackFilter() {
			public int accept(Method method) {
				String methodName = method.getName();
				if ("fun1".equals(methodName)) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		enhancer.create();
		return (T) Enhancer.create(cls, instance);
	}
 
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        //Object result = method.invoke(target,args);
        Object result = proxy.invokeSuper(target, args);
        after();
        return result;
    }
 
    private void before() {
        System.out.println("Before");
    }
 
    private void after() {
        System.out.println("After");
    }
    
	public static void main(String[] args) {
		 //BeanCopier可以实现Bean之间的属性同名属性拷贝
		 BeanCopier beanCopier = BeanCopier.create(Foo.class,Foo.class ,false );
		 beanCopier.copy(null,null,null);
	}
}