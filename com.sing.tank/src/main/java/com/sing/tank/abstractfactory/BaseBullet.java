package com.sing.tank.abstractfactory;

import com.sing.tank.*;

import java.awt.*;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class BaseBullet {
    private int speed = PropertyManager.getInstance().bulletSpeed;
    private int x, y;
    private DirectionEnum directionEnum;
    private Color color;
    private boolean live = true;
    private TankFrame tankFrame;
    private BaseTank tank;
    private int width;
    private int height;
    private Rectangle rectangle = new Rectangle();
    public abstract void paint(Graphics graphics);

    public BaseBullet(DirectionEnum directionEnum,BaseTank tank) {
        this.directionEnum = directionEnum;
        this.tankFrame = tank.getTankFrame();
        this.tank = tank;
        switch (directionEnum) {
            case UP:
                if(GroupEnum.GOOD == this.tank.getGroupEnum()){
                    this.width = ResourceManager.goodBulletU.getWidth();
                    this.height = ResourceManager.goodBulletU.getHeight();
                }else {
                    this.width = ResourceManager.bulletU.getWidth();
                    this.height = ResourceManager.bulletU.getHeight();
                }
                this.x = tank.getX() + (tank.getWidth() - this.width) / 2;
                this.x += 1;
                this.y = tank.getY();
                break;
            case DOWN:
                if(GroupEnum.GOOD == this.tank.getGroupEnum()){
                    this.width = ResourceManager.goodBulletD.getWidth();
                    this.height = ResourceManager.goodBulletD.getHeight();
                }else {
                    this.width = ResourceManager.bulletD.getWidth();
                    this.height = ResourceManager.bulletD.getHeight();
                }
                this.x = tank.getX() + (tank.getWidth() - this.width) / 2;
                this.x -= 1;
                this.y = tank.getY() + tank.getHeight() - this.height;
                break;
            case LEFT:
                if(GroupEnum.GOOD == this.tank.getGroupEnum()){
                    this.width = ResourceManager.goodBulletL.getWidth();
                    this.height = ResourceManager.goodBulletL.getHeight();
                }else {
                    this.width = ResourceManager.bulletL.getWidth();
                    this.height = ResourceManager.bulletL.getHeight();
                }
                this.x = tank.getX();
                this.y = tank.getY() + (tank.getHeight() - this.height) / 2;
                break;
            case RIGHT:
                if(GroupEnum.GOOD == this.tank.getGroupEnum()){
                    this.width = ResourceManager.goodBulletR.getWidth();
                    this.height = ResourceManager.goodBulletR.getHeight();
                }else {
                    this.width = ResourceManager.bulletR.getWidth();
                    this.height = ResourceManager.bulletR.getHeight();
                }
                this.x = tank.getX() + tank.getWidth() - this.width;
                this.y = tank.getY() + (tank.getHeight() - this.height) / 2;
                this.y += 1;
                break;
        }
        this.tankFrame.getBullets().add(this);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void setTankFrame(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
    }

    public BaseTank getTank() {
        return tank;
    }

    public void setTank(BaseTank tank) {
        this.tank = tank;
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
}
