package com.sing.tank;

import org.apache.commons.math3.analysis.function.Tan;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author songbo
 * @since 2022-07-07
 */
public class TankFrame extends Frame {
    int x = 200, y = 200;

    public TankFrame() throws HeadlessException {
        setSize(800, 600);
        setResizable(false);
        setTitle("坦克大战");
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                x += 30;
               repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                y += 30;
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.fillRect(x, y, 50, 50);
        x += 10;
        y += 10;
    }

    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        while (true){
            Thread.sleep(100);
            tankFrame.repaint();
        }
    }
}
