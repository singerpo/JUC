package com.sing.juc.c07.T1InterviewA1B2C3;

/**
 * 两个线程交替打印A1B2C3D4E5F6G7
 * (无锁、自旋锁）
 * @author songbo
 * @since 2022-05-10
 */
public class V06Cas {
    static volatile int t = 1;

    public static void main(String[] args) {
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();

        new Thread(() -> {
            for (char letter : letters) {
                while (t != 1) {
                }
                System.out.println(letter);
                t = 2;
            }
        }, "t1").start();

        new Thread(() -> {
            for (char number : numbers) {
                while (t != 2) {
                }
                System.out.println(number);
                t = 1;
            }
        }, "t2").start();
    }
}
