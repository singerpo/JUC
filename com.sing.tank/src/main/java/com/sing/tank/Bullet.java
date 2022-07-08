package com.sing.tank;

import java.awt.*;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Bullet {
    private int speed = 6;
    private int x, y;
    private DirectionEnum directionEnum;
    private Color color;
    private boolean live = true;
    private TankFrame tankFrame;

    public Bullet(int x, int y, DirectionEnum directionEnum, Color color, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
        this.color = color;
        this.tankFrame = tankFrame;
    }

    public void paint(Graphics graphics) {
        if (!live) {
            this.tankFrame.bullets.remove(this);
            return;
        }
        graphics.setColor(this.color);
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
            if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
                this.live = false;
            }
            if (x >= tankFrame.otherTank.getX() && x <= tankFrame.otherTank.getX() + tankFrame.otherTank.getWidth() &&
                    (y >= tankFrame.otherTank.getY()
                            && y <= tankFrame.otherTank.getY() + tankFrame.otherTank.getHeight()
                    )
            ) {
                tankFrame.otherTank.setLive(false);
            }

        }
    }
}
