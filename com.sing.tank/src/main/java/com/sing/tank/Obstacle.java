package com.sing.tank;

import com.sing.tank.ResourceManager;

import java.awt.*;

public class Obstacle {
    private int x;
    private int y;
    private int width =200;
    private int height=20;
    private boolean live = true;
    private Rectangle rectangle = new Rectangle();

    public Obstacle(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics graphics) {
        if (!this.live) {
            return;
        }
        graphics.drawImage(ResourceManager.obstacle, x, y, this.width, this.height, null);
        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = this.width;
        rectangle.height = this.height;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
