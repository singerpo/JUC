package com.sing.tank;

import com.sing.tank.abstractfactory.BaseExplode;
import com.sing.tank.abstractfactory.BaseTank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Explode extends BaseExplode {
    private int x, y;
    private boolean live = true;
    private TankFrame tankFrame;
    private int width = 71;
    private int height = 100;
    private int step = 0;
    private BaseTank tank;

    public Explode(BaseTank tank) {
        this.tank = tank;
        this.tankFrame = tank.getTankFrame();
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics graphics) {
        if (!live) {
            tankFrame.getExplodes().remove(this);
            return;
        }
        BufferedImage bufferedImage = ResourceManager.explodes[step++];
        this.x = this.tank.getX() - (bufferedImage.getWidth() - this.tank.getWidth()) / 2;
        this.y = this.tank.getY() - (bufferedImage.getHeight() - this.tank.getHeight()) / 2;
        graphics.drawImage(bufferedImage, x, y, null);
        if (step >= ResourceManager.explodes.length) {
            step = 0;
            this.live = false;
        }
    }
}
