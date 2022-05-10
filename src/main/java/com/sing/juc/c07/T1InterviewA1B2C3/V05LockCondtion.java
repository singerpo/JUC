package com.sing.juc.c07.T1InterviewA1B2C3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印A1B2C3D4E5F6G7
 * @author songbo
 * @since 2022-05-10
 */
public class V05LockCondtion {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();

        new Thread(()->{
            try {
                lock.lock();
                for (char letter : letters) {
                    System.out.println(letter);
                    try {
                        condition2.signal();
                        // 让出锁
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 必须，否则无法停止线程
                condition2.signal();
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            try {
                lock.lock();
                for (char number : numbers) {
                    System.out.println(number);
                    try {
                        condition1.signal();
                        // 让出锁
                        condition2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 必须，否则无法停止线程
                condition1.signal();
            }finally {
                lock.unlock();
            }
        },"t2").start();


    }
}
