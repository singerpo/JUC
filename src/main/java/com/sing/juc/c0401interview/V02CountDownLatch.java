package com.sing.juc.c0401interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *
 * 实现一个容器，提供两个方法 add size
 * 写两个线程线程1提供10个元素到容器中，线程2实现监控元素的个数，当个数为5时，线程2给出提示并结束
 */
public class V02CountDownLatch {
    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        V02CountDownLatch t02CountDownLatch = new V02CountDownLatch();
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println(" t1 启动");
            for (int i = 0; i < 10; i++) {
                t02CountDownLatch.add(new Object());
                System.out.println("add " + i);

                if (t02CountDownLatch.size() == 5) {
                    // 打开闩锁，让t2能够执行
                    latch.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();

        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" t2 结束");
            latch2.countDown();
        }, "t2").start();


    }
}
