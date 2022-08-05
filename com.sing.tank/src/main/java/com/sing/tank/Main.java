package com.sing.tank;

import com.sing.tank.facade.GameModel;

import javax.swing.*;

/**
 * @author songbo
 * @since 2022-07-07
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        TankFrame tankFrame = new TankFrame();
        // new Thread(() -> new Audio("audio/war1.wav").loop()).start();
        while (true) {
            Thread.sleep(TankFrame.PAINT_DIFF);
            if (!GameModel.getInstance().getPause()) {
                tankFrame.mainPanel.repaint();
            }
        }
    }
}
