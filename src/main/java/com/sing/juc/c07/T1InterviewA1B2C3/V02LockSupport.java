package com.sing.juc.c07.T1InterviewA1B2C3;

import java.util.concurrent.locks.LockSupport;

/**
 * 两个线程交替打印A1B2C3D4E5F6G7
 * 保证A先输出
 * @author songbo
 * @since 2022-05-10
 */
public class V02LockSupport {
    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();

        t1 = new Thread(()->{
                for (char letter : letters) {
                    System.out.println(letter);
                    // 叫醒t2
                    LockSupport.unpark(t2);
                    // 阻塞t1
                    LockSupport.park();
                }
        },"t1");

        t2 = new Thread(()->{
            for (char number : numbers) {
                // 阻塞t2
                LockSupport.park();
                System.out.println(number);
                // 叫醒t1
                LockSupport.unpark(t1);
            }
        },"t2");

        t1.start();
        t2.start();

    }
}
