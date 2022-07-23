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
    private boolean live = true;
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
            collide();
        }
    }

    /**
     * 判断子弹和坦克碰撞
     */
    private void collide() {
        // 通过是否相交来判断是否相撞
        Rectangle bulletRect = this.getRectangle();
        // for (BaseTank tank : this.getGameModel().getTanks()) {
        //     // 坦克自己的子弹不会打自己;同一个组的坦克子弹不打自己组的
        //     if (tank == this.getTank() || tank.getGroupEnum().equals(this.getTank().getGroupEnum())) {
        //         continue;
        //     }
        //     Rectangle tankRect = tank.getRectangle();
        //     if (bulletRect.intersects(tankRect)) {
        //         tank.setLive(false);
        //
        //         this.setLive(false);
        //         //在坦克中心位置爆炸
        //         BaseExplode explode = this.getGameModel().getGameFactory().createExplode(tank);
        //         this.getGameModel().getExplodes().add(explode);
        //         return;
        //     }
        // }
        // if (this.isLive()) {
        //     for (Obstacle obstacle : this.getGameModel().getObstacles()) {
        //         if (bulletRect.intersects(obstacle.getRectangle())) {
        //             this.setLive(false);
        //             obstacle.setLive(false);
        //             return;
        //         }
        //     }
        // }
        // 计算x,y来判断是否碰撞
        // for (Tank tank1 : tankFrame.tanks) {
        //     if (tank1 == this.tank) {
        //         continue;
        //     }
        //     if (x >= tank.getX() && x <= tank.getX() + tank.getWidth() &&
        //             (y >= tank1.getY()
        //                     && y <= tank.getY() + tank.getHeight()
        //             )
        //     ) {
        //         tank.setLive(false);
        //         this.live = false;
        //     }
        //
        // }
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

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
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
