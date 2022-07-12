package com.sing.tank;

/**
 * @author songbo
 * @since 2022-07-07
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        new Thread(() -> new Audio("audio/war1.wav").loop()).start();
        while (true) {
            Thread.sleep(TankFrame.PAINT_DIFF);
            tankFrame.repaint();
        }

    }
}
