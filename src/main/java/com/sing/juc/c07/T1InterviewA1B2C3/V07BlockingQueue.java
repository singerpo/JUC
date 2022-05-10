package com.sing.juc.c07.T1InterviewA1B2C3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 两个线程交替打印A1B2C3D4E5F6G7
 *
 * @author songbo
 * @since 2022-05-10
 */
public class V07BlockingQueue {
    public static void main(String[] args) {
        BlockingQueue<Character> queue1 = new ArrayBlockingQueue<>(1);
        BlockingQueue<Character> queue2 = new ArrayBlockingQueue<>(1);
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();

        new Thread(() -> {
            for (char letter : letters) {
                try {
                    queue1.put(letter);
                    System.out.println(queue2.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (char number : numbers) {
                try {
                    System.out.println(queue1.take());
                    queue2.put(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
