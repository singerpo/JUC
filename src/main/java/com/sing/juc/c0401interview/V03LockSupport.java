package com.sing.juc.c0401interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * 实现一个容器，提供两个方法 add size
 * 写两个线程线程1提供10个元素到容器中，线程2实现监控元素的个数，当个数为5时，线程2给出提示并结束
 */
public class V03LockSupport {
    List lists = new ArrayList();
    static Thread t1,t2;

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) throws InterruptedException {
        V03LockSupport t03LockSupport = new V03LockSupport();

        t1 = new Thread(() -> {
            System.out.println(" t1 启动");
            for (int i = 0; i < 10; i++) {
                t03LockSupport.add(new Object());
                System.out.println("add " + i);

                if (t03LockSupport.size() == 5) {
                    // 让t2能够执行
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
            }
        }, "t1");

        t2 = new Thread(() -> {
            System.out.println(" t2 启动");
            LockSupport.park();
            System.out.println(" t2 结束");
            LockSupport.unpark(t1);


        }, "t2");

        t1.start();
        t2.start();



    }
}
