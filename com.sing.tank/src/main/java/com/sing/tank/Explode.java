package com.sing.tank;

import com.sing.tank.abstractfactory.BaseExplode;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 爆炸
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Explode extends BaseExplode {
    private int width = 71;
    private int height = 100;
    private int step = 0;
    private BaseTank tank;

    public Explode(BaseTank tank) {
        this.tank = tank;
        new Thread(() -> new Audio("audio/explode.wav").play()).start();
        GameModel.getInstance().add(this);
    }

    public void paint(Graphics graphics) {
        if (!this.getLive()) {
            GameModel.getInstance().remove(this);
            return;
        }
        BufferedImage bufferedImage = ResourceManager.explodes[step++];
        this.setX(this.tank.getX() - (bufferedImage.getWidth() - this.tank.getWidth()) / 2);
        this.setY(this.tank.getY() - (bufferedImage.getHeight() - this.tank.getHeight()) / 2);
        graphics.drawImage(bufferedImage, this.getX(), this.getY(), null);
        if (step >= ResourceManager.explodes.length) {
            step = 0;
            this.setLive(false);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public BaseTank getTank() {
        return tank;
    }

    public void setTank(BaseTank tank) {
        this.tank = tank;
    }
}
