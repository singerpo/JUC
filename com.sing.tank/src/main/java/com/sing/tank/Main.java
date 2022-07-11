package com.sing.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author songbo
 * @since 2022-07-07
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        while (true){
            Thread.sleep(TankFrame.PAINT_DIFF);
            tankFrame.repaint();
        }

    }
}
