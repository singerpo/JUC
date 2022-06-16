package com.sing.design.d03singleton;

/**
 * 静态内部类式
 * JVM保证单例
 * 加载外部类时不会加载内部类，这样可以实现懒加载
 */
public class Mgr03 {
    private Mgr03() {
    }

    //静态内部类
    private static class Mgr03Holder {
        private static final Mgr03 INSTANCE = new Mgr03();
    }

    public static Mgr03 getInstance() {
        return Mgr03Holder.INSTANCE;
    }

}
