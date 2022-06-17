package com.sing.design.c3singleton;

/**
 * 饿汉式
 * 类加载到内存后，就实例化一个单例（JVM保证线程安全)
 * 简单实用，推荐使用
 * 唯一缺点：不管用到与否，类装载时就完成实例化（加载、验证、准备、初始化、解析)
 */
public class Mgr01 {
    // 在准备阶段完成实例化
    private static final Mgr01 INSTANCE = new Mgr01();

    private Mgr01() {
    }

    public static Mgr01 getInstance() {
        return INSTANCE;
    }

}
