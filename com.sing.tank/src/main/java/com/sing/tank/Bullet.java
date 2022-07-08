package com.sing.tank;

import java.awt.*;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Bullet {
    private int speed = 8;
    private int x, y;
    private DirectionEnum directionEnum;

    public Bullet(int x, int y, DirectionEnum directionEnum) {
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
    }

    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(color);
        graphics.fillOval(x, y, 10, 10);
        if (this.directionEnum != null) {
            switch (this.directionEnum) {
                case UP:
                    this.y -= this.speed;
                    break;
                case DOWN:
                    this.y += this.speed;
                    break;
                case LEFT:
                    this.x -= this.speed;
                    break;
                case RIGHT:
                    this.x += this.speed;
                    break;
            }
        }
    }
}
