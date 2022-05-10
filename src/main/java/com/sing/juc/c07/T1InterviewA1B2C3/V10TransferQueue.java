package com.sing.juc.c07.T1InterviewA1B2C3;


import java.io.IOException;
import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 *
 * 两个线程交替打印A1B2C3D4E5F6G7
 *
 * @author songbo
 * @since 2022-05-10
 */
public class V10TransferQueue {

    public static void main(String[] args) throws IOException {
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();
        TransferQueue<Character> transferQueue = new LinkedTransferQueue<>();
        new Thread(() -> {
            for (char letter : letters) {
                try {
                    transferQueue.transfer(letter);
                    System.out.println(transferQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (char number : numbers) {
                try {
                    System.out.println(transferQueue.take());
                    transferQueue.transfer(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t2").start();
    }
}
