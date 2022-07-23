package com.sing.tank.abstractfactory;

import java.awt.*;

/**
 * @author songbo
 * @since 2022-07-22
 */
public abstract class GameObject {
    private int x, y;
    /*** 是否存活 **/
    private boolean live = true;
    private Rectangle rectangle = new Rectangle();

    public abstract void paint(Graphics graphics);

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

    public boolean getLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
