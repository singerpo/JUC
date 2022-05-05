package com.sing.juc.c05;

/**
 * 0:44
 * @author songbo
 * @since 2022-05-05
 */
public class T01ThreadLocal {
    static class Person {
        public String name;
    }
    volatile static Person person = new Person();

    public static void main(String[] args) {
        new Thread(()->{

        }).start();
    }
}
