package com.sing.design.s3proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB动态代理
 * @author songbo
 * @since 2022-06-22
 */
public class CglibDynamicProxy {
    public static void main(String[] args) {
        // 指定 CGLIB 将动态生成的代理类保存至指定的磁盘路径下
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "G:\\cglib");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Boy.class);
        enhancer.setCallback(new LogMethodInterceptor());
       // GiveGift boyProxy = (GiveGift) Enhancer.create(Boy.class,new LogMethodInterceptor());
        GiveGift boyProxy = (GiveGift) enhancer.create();
        boyProxy.giveFlowers();
    }
}

class LogMethodInterceptor implements MethodInterceptor {

    public void before() {
        System.out.println("method start");
    }

    public void after() {
        System.out.println("method end");
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }
}
