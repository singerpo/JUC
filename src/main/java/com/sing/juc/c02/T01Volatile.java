package com.sing.juc.c02;

import java.util.concurrent.TimeUnit;

/**
 * volatile
 * 1.保证线程可见性（通过缓存一致性协议实现,不能保证操作原子性所以不能替代锁）
 * 2.禁止指令重排序（CPU)（通过内存屏障实现）
 */
public class T01Volatile {
    volatile boolean running = true;

    void m(){
        System.out.println("m start");
        while (running){

        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        T01Volatile t01Volatile = new T01Volatile();

        new Thread(t01Volatile::m,"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 如果不加volatile，该变量值就不能及时被线程t1读取
        t01Volatile.running = false;

    }
}
