package com.sing.tank;

import com.sing.tank.facade.GameModel;
import com.sing.tank.net.Client;

import javax.swing.*;

/**
 * @author songbo
 * @since 2022-07-07
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        TankFrame tankFrame = new TankFrame();
        // new Thread(() -> new Audio("audio/war1.wav").loop()).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(TankFrame.PAINT_DIFF);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!GameModel.getInstance().getPause()) {
                    tankFrame.mainPanel.repaint();
                }
            }
        }).start();
        Client client = new Client();
        client.connect();


    }
}
