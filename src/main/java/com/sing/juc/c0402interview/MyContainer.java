package com.sing.juc.c0402interview;

import java.util.ArrayList;
import java.util.List;

/**
 * 1:52
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class MyContainer<T> {

    final private List<T> lists = new ArrayList();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        while (lists.size() == MAX){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lists.add(t);
        ++count;
        // 通知消费者线程进行消费
        this.notifyAll();
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.get(0);
        lists.remove(0);
        count--;
        this.notifyAll();//通知生产者进行生产
        return t;
    }

    public int getCount() {
        return lists.size();
    }

    public static void main(String[] args) {
        MyContainer t01WithoutVolatile = new MyContainer();

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
