package com.sing.tank.abstractfactory;

import com.sing.tank.enums.DirectionEnum;

import java.awt.*;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class RectBullet extends BaseBullet {


    public RectBullet(DirectionEnum directionEnum, BaseTank tank) {
        super(directionEnum, tank);
    }


    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.YELLOW);
        this.setWidth(10);
        this.setHeight(20);
        graphics.fillRect(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        graphics.setColor(color);
        move();
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();

    }


}
