package com.sing.juc.c0402interview;

import java.util.LinkedList;

/**
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class MyContainer2<T> {

    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        // 为什么用while而不是用if
        // 因为当执行this.wait时阻塞，唤醒之后会继续往下执行，不会再判断条件，所以要用while唤醒之后重新判断条件
        while (lists.size() == MAX) {
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
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        //通知生产者进行生产
        this.notifyAll();
        return t;
    }

    public int getCount() {
        return this.count;
    }

    public static void main(String[] args) {
        MyContainer2<String> myContainer = new MyContainer2();

        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(myContainer.get());
                }
            }, "c" + i).start();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    myContainer.put(Thread.currentThread().getName() + " " + j);
                }
            }, "p" + i).start();
        }


    }
}
