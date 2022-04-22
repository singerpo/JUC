package com.sing.juc.c01;

/**
 * synchronized关键字(可重入)
 * 对某个对象加锁
 * 1.锁升级的概念
 * 偏向锁（markword 记录这个线程ID)
 * 自旋锁（当其他线程来争用时升级为自旋锁,占用CPU）
 * 重量级锁（自旋锁10次以后升级为重量级锁-OS）
 * 建议：执行时间短、线程数量少用自旋锁，执行时间长、线程数量比较多用系统锁
 *
 * 2.synchronized(Object)不能用String常量、Integer、Long等基本类型
 * @author songbo
 * @since 2022-04-20
 */
public class T04Synchronized {
    private int count = 10;
    private final Object obj = new Object();

    public void m1() {
        // 任何线程要执行下面的代码，必须先拿到obj的锁
        synchronized (obj) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }

    public void m2() {
        // 任何线程要执行下面的代码，必须先拿到this的锁
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }

    public synchronized void m3() {
        // 任何线程要执行下面的代码，必须先拿到this的锁
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }

    public synchronized static void m4() {//这里等同与synchronized(T04_Synchronized.class)
        System.out.println(Thread.currentThread().getName());
    }

    public void m5() {
        synchronized (T04Synchronized.class) {
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }

    public static void m6() {
        synchronized (T04Synchronized.class) {
            System.out.println(Thread.currentThread().getName());
        }
    }

}
