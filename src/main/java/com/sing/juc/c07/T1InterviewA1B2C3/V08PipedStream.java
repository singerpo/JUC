package com.sing.juc.c07.T1InterviewA1B2C3;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 两个线程交替打印A1B2C3D4E5F6G7
 *
 * @author songbo
 * @since 2022-05-10
 */
public class V08PipedStream {
    public static void main(String[] args) throws IOException {
        char[] letters = "ABCDEFG".toCharArray();
        char[] numbers = "1234567".toCharArray();

        PipedInputStream inputStream1 = new PipedInputStream();
        PipedInputStream inputStream2 = new PipedInputStream();
        PipedOutputStream outputStream1 = new PipedOutputStream();
        PipedOutputStream outputStream2 = new PipedOutputStream();

        inputStream1.connect(outputStream2);
        inputStream2.connect(outputStream1);
        String msg = "your turn";
        new Thread(() -> {
            for (char letter : letters) {
                byte[] buffer = new byte[9];
                try {
                    inputStream1.read(buffer);
                    if(new String(buffer).equals(msg)){
                        System.out.println(letter);
                    };
                    outputStream1.write(msg.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (char number : numbers) {
                byte[] buffer = new byte[9];
                try {
                    System.out.println(number);
                    outputStream2.write(msg.getBytes());
                    inputStream2.read(buffer);
                    if(new String(buffer).equals(msg)){
                       continue;
                    };
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(number);
            }
        }, "t2").start();
    }
}
