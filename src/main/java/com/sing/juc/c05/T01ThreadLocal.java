package com.sing.juc.c05;

import java.util.concurrent.TimeUnit;

/**
 * 1:02
 *
 * @author songbo
 * @since 2022-05-05
 */
public class T01ThreadLocal {
    static class Person {
        public String name = "zhangsan";
    }

    static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get().name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadLocal.set(new Person());
        }).start();
    }
}
