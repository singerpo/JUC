package com.sing.juc.c03;

import java.util.concurrent.Semaphore;

/**
 * 信号
 * 限流28
 */
public class T06Semaphore {

    public static void main(String[] args) {
        // 允许一个线程同时执行
        Semaphore semaphore = new Semaphore(1);
        new Thread(() -> {
            try {
                // 获取信号（获取到则信号减1，否则阻塞）
                semaphore.acquire();
                System.out.println("T1 running...");
                Thread.sleep(200);
                System.out.println("T1 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();

        new Thread(() -> {
            try {
                // 获取信号（获取到则信号减1，否则阻塞）
                semaphore.acquire();
                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();
    }
}
