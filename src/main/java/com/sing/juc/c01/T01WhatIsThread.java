package com.sing.juc.c01;

import java.util.concurrent.TimeUnit;

/**
 * 什么是线程
 * 1.线程的5种状态：新建（New)、就绪（Runnable)、运行（Running)、阻塞（blocked)、死亡(Dead)
 * （1）新建状态（NEW）：新建一个线程对象。
 * （2）就绪状态（Runnable）：线程对象创建后，其他线程调用了该对象的start方法。该状态的线程位于可运行线程池中，变得可运行，等待获取CPU的使用权。
 * （3）运行状态（Running）：就绪状态的线程获取了CPU，执行程序代码。
 * （4）阻塞状态（Blocked）：阻塞状态是线程因为某种原因放弃CPU
 * 使用权，暂时停止运行。直到线程进入就绪状态，才有机会转到运行状态。
 * （5）死亡状态（Dead）：线程执行完了或者因异常退出了run方法，该线程结束生命周期。
 *
 * 2.阻塞的情况又分为三种：
 * （1）等待阻塞：运行的线程执行wait方法，该线程会释放占用的所有资源，JVM会把该线程放入“等待池”中。进入这个状态后，是不能被自动唤醒的，必须依靠其他线程调用notify或notifyAll方法才能被唤醒，wait是object类的方法。
 * （2）同步阻塞：运行的线程在获取对象的同步锁时，若该同步锁被别的线程占用，则JVM会把该线程放入“锁池”中。
 * （3）其他阻塞：运行的线程执行sleep或join方法，或者发出了I/O请求时，JVM会把该线程置为阻塞状态。当sleep状态超时、join等待线程终止或超时、或者I/O处理完毕时，线程重新转入就绪状态。sleep是Thread类的方法。
 *
 * @author songbo
 * @since 2022-04-20
 */
public class T01WhatIsThread {
    private static class T1 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.MICROSECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("T1");
            }
        }
    }

    public static void main(String[] args) {
        new T1().start();
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.MICROSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main");
        }
    }
}
