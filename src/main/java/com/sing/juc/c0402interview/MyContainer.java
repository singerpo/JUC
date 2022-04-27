package com.sing.juc.c0402interview;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1:52
 * 写一个固定容量同步容器，拥有put和get方法，以及getCount方法
 * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 */
public class MyContainer<T> {

    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    private Lock lock = new ReentrantLock();
    // Condition本质就是不同的等待队列
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t) {
        try {
            lock.lock();
            // 为什么用while而不是用if
            // 因为当执行this.wait时阻塞，唤醒之后会继续往下执行，不会再判断条件，所以要用while唤醒之后重新判断条件
            while (lists.size() == MAX) {
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lists.add(t);
            ++count;
            // 通知消费者线程进行消费
            consumer.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                try {
                   consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            t = lists.removeFirst();
            count--;
            //通知生产者进行生产
            producer.signalAll();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public int getCount() {
        return this.count;
    }

    public static void main(String[] args) {
        MyContainer<String> myContainer = new MyContainer();

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
