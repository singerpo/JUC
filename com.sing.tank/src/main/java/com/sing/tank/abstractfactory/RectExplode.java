package com.sing.tank.abstractfactory;

import com.sing.tank.Audio;
import com.sing.tank.manager.ResourceManager;
import com.sing.tank.facade.GameModel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 *
 * @author songbo
 * @since 2022-07-08
 */
public class RectExplode extends BaseExplode {
    private int width = 71;
    private int height = 100;
    private int step = 0;
    private BaseTank tank;

    public RectExplode(BaseTank tank) {
        this.tank = tank;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics graphics) {
        BufferedImage bufferedImage = ResourceManager.rectExplodes[step++];
        this.setX(this.tank.getX() - (bufferedImage.getWidth() - this.tank.getWidth()) / 2);
        this.setY(this.tank.getY() - (bufferedImage.getHeight() - this.tank.getHeight()) / 2);
        graphics.drawImage(bufferedImage, this.getX(), this.getY(), null);
        if (step >= ResourceManager.rectExplodes.length) {
            step = 0;
            this.setLive(false);
        }
    }
}
