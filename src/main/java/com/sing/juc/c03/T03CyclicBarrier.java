package com.sing.juc.c03;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环障碍
 * @author songbo
 * @since 2022-04-24
 */
public class T03CyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满人，发车"));
        // CyclicBarrier barrier = new CyclicBarrier(20);
        // CyclicBarrier barrier1 = new CyclicBarrier(20, new Runnable() {
        //     @Override
        //     public void run() {
        //         System.out.println("满人，发车")
        //     }
        // });

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(()->{
                try {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName()+"-----");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },"t"+finalI).start();
        }
    }
}
