package com.sing.design.c3singleton;

/**
 * 饿汉式
 * 类加载到内存后，就实例化一个单例（JVM保证线程安全)
 * 简单实用，推荐使用
 * 唯一缺点：不管用到与否，类装载时就完成实例化（加载、验证、准备、解析、初始化)
 */
public class Mgr05 {
    private static final Mgr05 INSTANCE;

    // 在初始化阶段完成实例化
    static {
        INSTANCE = new Mgr05();
    }

    private Mgr05() {
    }

    public static Mgr05 getInstance() {
        return INSTANCE;
    }

}
