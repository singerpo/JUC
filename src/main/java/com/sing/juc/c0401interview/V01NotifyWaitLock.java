package com.sing.juc.c0401interview;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现一个容器，提供两个方法 add size
 * 写两个线程线程1提供10个元素到容器中，线程2实现监控元素的个数，当个数为5时，线程2给出提示并结束
 */
public class V01NotifyWaitLock {

    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        V01NotifyWaitLock t01WithoutVolatile = new V01NotifyWaitLock();
        final Object obj = new Object();
        new Thread(() -> {
            synchronized (obj) {
                System.out.println(" t2 启动");
                if (t01WithoutVolatile.size() != 5) {
                    try {
                        // 不等于5，阻塞释放锁
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(" t2 结束");
                // 通知t1继续执行
                obj.notify();
            }
        }, "t2").start();

        // try {
        //     TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        new Thread(() -> {
            synchronized (obj) {
                for (int i = 0; i < 10; i++) {
                    t01WithoutVolatile.add(new Object());
                    System.out.println("add " + i);

                    if (t01WithoutVolatile.size() == 5) {
                        // 等于5时通知t2,释放锁，让t2能够执行
                        obj.notify();
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "t1").start();

    }
}
