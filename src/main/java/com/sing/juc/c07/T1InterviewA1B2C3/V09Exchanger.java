package com.sing.juc.c07.T1InterviewA1B2C3;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Exchanger;

/**
 *
 * 两个线程交替打印A1B2C3D4E5F6G7
 *
 * @author songbo
 * @since 2022-05-10
 */
public class V09Exchanger {
    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) throws IOException {
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();

        new Thread(() -> {
            for (char letter : letters) {
                System.out.println(letter);
                try {
                    exchanger.exchange("letter");
                    //需要睡一下,来保证交替打印,不推荐使用
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (char number : numbers) {
                try {
                    exchanger.exchange("number");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(number);
            }
        }, "t2").start();
    }
}
