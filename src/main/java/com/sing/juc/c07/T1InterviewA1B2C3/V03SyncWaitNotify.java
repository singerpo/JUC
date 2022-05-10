package com.sing.juc.c07.T1InterviewA1B2C3;

import java.util.concurrent.CountDownLatch;

/**
 * 两个线程交替打印A1B2C3D4E5F6G7
 * 保证A先输出
 * @author songbo
 * @since 2022-05-10
 */
public class V03SyncWaitNotify {
    private static volatile boolean t1Started = false;
    // private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) {
        final Object obj = new Object();
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();

        new Thread(()->{
            synchronized (obj){
                for (char letter : letters) {
                    System.out.println(letter);
                    t1Started = true;
                    // latch.countDown();
                    try {
                        obj.notify();
                        // 让出锁
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                // 必须，否则无法停止线程
                obj.notify();
            }
        },"t1").start();

        new Thread(()->{
            synchronized (obj){
                // try {
                //     latch.await();
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
                while (!t1Started){
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (char number : numbers) {
                    System.out.println(number);
                    try {
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
               obj.notify();
            }
        },"t2").start();
    }
}
