package com.sing.design.s3proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理
 * JDK反射生成代理必须面向接口，这是由Proxy的内部实现决定的（反射之后利用ASM生成的代理类继承了Proxy，所以只能实现接口）
 *
 * @author songbo
 * @since 2022-06-21
 */
public class JdkDynamicProxy {

    public static void main(String[] args) {
        // 保存动态代理生成的代理类文件
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        GiveGift boy = new Boy();
        GiveGift boyProxy = (GiveGift) Proxy.newProxyInstance(Boy.class.getClassLoader(), new Class[]{GiveGift.class},
                new LogInvocationHandler(boy)
        );
        boyProxy.giveFlowers();
    }

}

class LogInvocationHandler implements InvocationHandler {
    GiveGift giveGift;

    public LogInvocationHandler(GiveGift giveGift) {
        this.giveGift = giveGift;
    }

    public void before(){
        System.out.println("method start");
    }

    public void after(){
        System.out.println("method end");
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(giveGift, args);
        after();
        return result;
    }


}



