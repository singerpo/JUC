package com.sing.tank.abstractfactory;

import com.sing.tank.Audio;
import com.sing.tank.ResourceManager;
import com.sing.tank.TankFrame;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 *
 * @author songbo
 * @since 2022-07-08
 */
public class RectExplode extends BaseExplode {
    private int x, y;
    private boolean live = true;
    private TankFrame tankFrame;
    private int width = 71;
    private int height = 100;
    private int step = 0;
    private BaseTank tank;

    public RectExplode(BaseTank tank) {
        this.tank = tank;
        this.tankFrame = tank.getTankFrame();
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics graphics) {
        if (!live) {
            tankFrame.getExplodes().remove(this);
            return;
        }
        BufferedImage bufferedImage = ResourceManager.rectExplodes[step++];
        this.x = this.tank.getX() - (bufferedImage.getWidth() - this.tank.getWidth()) / 2;
        this.y = this.tank.getY() - (bufferedImage.getHeight() - this.tank.getHeight()) / 2;
        graphics.drawImage(bufferedImage, x, y, null);
        if (step >= ResourceManager.rectExplodes.length) {
            step = 0;
            this.live = false;
        }
    }
}
