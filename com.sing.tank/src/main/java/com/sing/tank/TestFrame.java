package com.sing.tank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author songbo
 * @since 2022-07-07
 */
public class TestFrame {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setSize(800,600);
        frame.setTitle("坦克大战");
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               System.exit(0);
            }
        });

    }
}
