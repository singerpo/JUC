package com.sing.juc.c03;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**2.55
 * 可重入锁
 * ReentrantLock用于替代synchronized
 * 由于m1锁定this,只有m1执行完毕的时候，m2才能执行
 * <p>
 * 使用ReetrantLock可以进行lock
 * 使用ReetrantLock可以进行tryLock
 * 使用ReetrantLock可以进行lockInterruptibly(调用线程.interrupt方法打断该线程的等待）
 * 参数为true则表示为公平锁
 *
 * 底层原理：AQS(AbstractQueuedSynchronizer)
 * 底层是CAS+volatile
 *
 */
public class T01ReentrantLock {
    Lock lock = new ReentrantLock();

    void m1() {
        lock = new ReentrantLock();
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("m1--" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 使用tryLock进行尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据tryLock的返回值来判断是否锁定
     * 也可以指定tyLock的时间，由于tryLock(time)抛出异常
     */
    void m2() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2..." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    void m3() {
        boolean isLock = true;
        try {
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                System.out.println("线程被中断了");
                isLock = false;
                // e.printStackTrace();
            }
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("m3--" + i);
            }
        } finally {
            if (isLock) {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        T01ReentrantLock t01ReentrantLock = new T01ReentrantLock();
        new Thread(t01ReentrantLock::m1).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t01ReentrantLock::m2).start();

        Thread t3 = new Thread(t01ReentrantLock::m3);
        t3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.interrupt();


    }
}
