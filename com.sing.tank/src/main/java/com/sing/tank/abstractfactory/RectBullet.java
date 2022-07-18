package com.sing.tank.abstractfactory;

import com.sing.tank.DirectionEnum;
import com.sing.tank.GroupEnum;
import com.sing.tank.ResourceManager;
import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.BaseTank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class RectBullet extends BaseBullet {


    public RectBullet(BaseTank tank) {
        this(tank.getDirectionEnum(), tank);
    }

    public RectBullet(DirectionEnum directionEnum, BaseTank tank) {
        super(directionEnum, tank);
    }


    public void paint(Graphics graphics) {
        if (!this.isLive()) {
            this.getTankFrame().getBullets().remove(this);
            return;
        }
        Color color = graphics.getColor();
        graphics.setColor(Color.YELLOW);
        this.setWidth(20);
        this.setHeight(30);
        graphics.fillRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        graphics.setColor(color);
        move();
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();

    }


}