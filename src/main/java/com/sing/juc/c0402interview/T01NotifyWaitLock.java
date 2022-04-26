package com.sing.juc.c0402interview;

import java.util.ArrayList;
import java.util.List;

/**
 * 1:35
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class T01NotifyWaitLock {

    List lists = new ArrayList();

    public void put(Object o) {
        lists.add(o);
    }

    public Object get() {
        Object obj = lists.get(0);
        lists.remove(0);
        return obj;
    }

    public int getCount() {
        return lists.size();
    }

    public static void main(String[] args) {
        T01NotifyWaitLock t01WithoutVolatile = new T01NotifyWaitLock();

        new Thread(() -> {
            while (true) {
                if (t01WithoutVolatile.getCount() <= 10) {
                    t01WithoutVolatile.put(new Object());
                    System.out.println(Thread.currentThread().getName() + "--" + t01WithoutVolatile.getCount());
                }
            }
        }, "p1").start();

        new Thread(() -> {
            while (true) {
                if (t01WithoutVolatile.getCount() <= 10) {
                    t01WithoutVolatile.put(new Object());
                    System.out.println(Thread.currentThread().getName() + "--" + t01WithoutVolatile.getCount());
                }
            }
        }, "p2").start();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    if (t01WithoutVolatile.getCount() > 0) {
                        Object obj = t01WithoutVolatile.get();
                        System.out.println(Thread.currentThread().getName() + "--" + t01WithoutVolatile.getCount());
                    }
                }
            }, "c" + i).start();
        }

    }
}
