package com.sing.tank.abstractfactory;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author songbo
 * @since 2022-07-22
 */
public abstract class GameObject implements Serializable {
    private UUID id = UUID.randomUUID();
    private int x, y;
    private int oldX, oldY;
    private int initX, initY;
    /*** 宽度 **/
    private int width;
    /*** 高度 **/
    private int height;
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

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
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

    public int getInitX() {
        return initX;
    }

    public void setInitX(int initX) {
        this.initX = initX;
    }

    public int getInitY() {
        return initY;
    }

    public void setInitY(int initY) {
        this.initY = initY;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                ", oldX=" + oldX +
                ", oldY=" + oldY +
                ", initX=" + initX +
                ", initY=" + initY +
                ", width=" + width +
                ", height=" + height +
                ", live=" + live +
                ", rectangle=" + rectangle +
                '}';
    }
}
