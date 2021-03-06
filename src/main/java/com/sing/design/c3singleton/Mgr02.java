package com.sing.design.c3singleton;

/**
 * 懒汉式
 * synchronized双重检查锁
 * volatile禁止指令重排
 */
public class Mgr02 {
    private volatile static Mgr02 instance;

    private Mgr02() {
    }

    public static Mgr02 getInstance() {
        if (instance == null) {
            // 通过减小同步代码块和双重检查来提高效率
            synchronized (Mgr02.class) {
                if (instance == null) {
                    instance = new Mgr02();
                }
            }
        }
        return instance;
    }

}
