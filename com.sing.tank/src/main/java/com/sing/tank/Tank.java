package com.sing.tank;

import java.awt.*;

/**
 * 坦克
 * @author songbo
 * @since 2022-07-08
 */
public class Tank {
    private int x;
    private int y;
    private DirectionEnum directionEnum = DirectionEnum.DOWN;
    private int speed = 5;
    private Color color;
    private boolean moving = false;

    public Tank(int x, int y, DirectionEnum directionEnum, Color color) {
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
        this.color = color;

    }

    public void paint(Graphics graphics) {
        graphics.setColor(this.color);
        graphics.fillRect(this.x, this.y, 50, 50);
        if (this.directionEnum != null && moving) {
            switch (this.directionEnum) {
                case UP:
                    if (this.y - this.speed >= 25) {
                        this.y -= this.speed;
                    }
                    break;
                case DOWN:
                    if (this.y + this.speed <= 600 - 50) {
                        this.y += this.speed;
                    }

                    break;
                case LEFT:
                    if (this.x - this.speed >= 0) {
                        this.x -= this.speed;
                    }
                    break;
                case RIGHT:
                    if (this.x + this.speed <= 800 - 50) {
                        this.x += this.speed;
                    }

                    break;
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public DirectionEnum getDirectionEnum() {
        return directionEnum;
    }

    public void setDirectionEnum(DirectionEnum directionEnum) {
        this.directionEnum = directionEnum;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
