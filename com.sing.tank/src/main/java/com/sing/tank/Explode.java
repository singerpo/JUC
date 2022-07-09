package com.sing.tank;

import java.awt.*;

/**
 * 爆炸
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Explode {
    private int x, y;
    private boolean live = true;
    private TankFrame tankFrame;
    private int width = 12;
    private int height = 12;
    private int step = 0;

    public Explode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics graphics) {
        if (!live) {
            tankFrame.explodes.remove(this);
            return;
        }
        graphics.drawImage(ResourceManager.explodes[step++], x, y, null);
        if (step >= ResourceManager.explodes.length) {
            step = 0;
            this.live = false;
        }
    }
}
