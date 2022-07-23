package com.sing.tank.abstractfactory;

import com.sing.tank.*;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.manager.ResourceManager;

import java.awt.*;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class BaseBullet extends GameObject {
    private int speed = PropertyManager.getInstance().bulletSpeed;
    private DirectionEnum directionEnum;
    private GameModel gameModel;
    private BaseTank tank;
    private int width;
    private int height;


    public BaseBullet(DirectionEnum directionEnum, BaseTank tank) {
        this.directionEnum = directionEnum;
        this.gameModel = tank.getGameModel();
        this.tank = tank;
        switch (directionEnum) {
            case UP:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.width = ResourceManager.goodBulletU.getWidth();
                    this.height = ResourceManager.goodBulletU.getHeight();
                } else {
                    this.width = ResourceManager.bulletU.getWidth();
                    this.height = ResourceManager.bulletU.getHeight();
                }
                this.setX(tank.getX() + (tank.getWidth() - this.width) / 2);
                this.setX(this.getX() + 1);
                this.setY(tank.getY());
                break;
            case DOWN:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.width = ResourceManager.goodBulletD.getWidth();
                    this.height = ResourceManager.goodBulletD.getHeight();
                } else {
                    this.width = ResourceManager.bulletD.getWidth();
                    this.height = ResourceManager.bulletD.getHeight();
                }
                this.setX(tank.getX() + (tank.getWidth() - this.width) / 2);
                this.setX(this.getX() - 1);
                this.setY(tank.getY() + tank.getHeight() - this.height);
                break;
            case LEFT:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.width = ResourceManager.goodBulletL.getWidth();
                    this.height = ResourceManager.goodBulletL.getHeight();
                } else {
                    this.width = ResourceManager.bulletL.getWidth();
                    this.height = ResourceManager.bulletL.getHeight();
                }
                this.setX(tank.getX());
                this.setY(tank.getY() + (tank.getHeight() - this.height) / 2);
                break;
            case RIGHT:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.width = ResourceManager.goodBulletR.getWidth();
                    this.height = ResourceManager.goodBulletR.getHeight();
                } else {
                    this.width = ResourceManager.bulletR.getWidth();
                    this.height = ResourceManager.bulletR.getHeight();
                }
                this.setX(tank.getX() + tank.getWidth() - this.width);
                this.setY(tank.getY() + (tank.getHeight() - this.height) / 2);
                this.setY(this.getY() + 1);
                break;
        }
        this.gameModel.add(this);
    }

    /**
     * 子弹移动
     */
    public void move() {
        if (this.getDirectionEnum() != null) {
            switch (this.getDirectionEnum()) {
                case UP:
                    this.setY(this.getY() - this.getSpeed());
                    break;
                case DOWN:
                    this.setY(this.getY() + this.getSpeed());
                    break;
                case LEFT:
                    this.setX(this.getX() - this.getSpeed());
                    break;
                case RIGHT:
                    this.setX(this.getX() + this.getSpeed());
                    break;
            }
            if (this.getX() < 0 || this.getY() < 0 || this.getX() > TankFrame.GAME_WIDTH || this.getY() > TankFrame.GAME_HEIGHT) {
                this.setLive(false);
            }
        }
    }



    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public DirectionEnum getDirectionEnum() {
        return directionEnum;
    }

    public void setDirectionEnum(DirectionEnum directionEnum) {
        this.directionEnum = directionEnum;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
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
}
