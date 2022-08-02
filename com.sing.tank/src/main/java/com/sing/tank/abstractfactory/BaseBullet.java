package com.sing.tank.abstractfactory;

import com.sing.tank.TankFrame;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.manager.ResourceManager;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class BaseBullet extends GameObject {
    private final int speed = PropertyManager.getInstance().bulletSpeed;
    private DirectionEnum directionEnum;
    private BaseTank tank;
    private boolean remove;

    public BaseBullet() {
        setLive(false);
        this.remove = true;
    }

    public BaseBullet(DirectionEnum directionEnum, BaseTank tank) {
        initBaseBullet(directionEnum, tank);
    }

    public void initBaseBullet(DirectionEnum directionEnum, BaseTank tank) {
        this.setLive(true);
        this.remove = false;
        this.directionEnum = directionEnum;
        this.tank = tank;
        switch (directionEnum) {
            case UP:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.setWidth(ResourceManager.goodBulletU.getWidth());
                    this.setHeight(ResourceManager.goodBulletU.getHeight());
                } else {

                    this.setWidth(ResourceManager.bulletU.getWidth());
                    this.setHeight(ResourceManager.bulletU.getHeight());
                }
                this.setX(tank.getX() + (tank.getWidth() - this.getWidth()) / 2);
                this.setX(this.getX() + 1);
                this.setY(tank.getY());
                break;
            case DOWN:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.setWidth(ResourceManager.goodBulletD.getWidth());
                    this.setHeight(ResourceManager.goodBulletD.getHeight());
                } else {
                    this.setWidth(ResourceManager.bulletD.getWidth());
                    this.setHeight(ResourceManager.bulletD.getHeight());
                }
                this.setX(tank.getX() + (tank.getWidth() - this.getWidth()) / 2);
                this.setX(this.getX() - 1);
                this.setY(tank.getY() + tank.getHeight() - this.getHeight());
                break;
            case LEFT:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.setWidth(ResourceManager.goodBulletL.getWidth());
                    this.setHeight(ResourceManager.goodBulletL.getHeight());
                } else {
                    this.setWidth(ResourceManager.bulletL.getWidth());
                    this.setHeight(ResourceManager.bulletL.getHeight());
                }
                this.setX(tank.getX());
                this.setY(tank.getY() + (tank.getHeight() - this.getHeight()) / 2);
                break;
            case RIGHT:
                if (GroupEnum.GOOD == this.tank.getGroupEnum()) {
                    this.setWidth(ResourceManager.goodBulletR.getWidth());
                    this.setHeight(ResourceManager.goodBulletR.getHeight());
                } else {
                    this.setWidth(ResourceManager.bulletR.getWidth());
                    this.setHeight(ResourceManager.bulletR.getHeight());
                }
                this.setX(tank.getX() + tank.getWidth() - this.getWidth());
                this.setY(tank.getY() + (tank.getHeight() - this.getHeight()) / 2);
                this.setY(this.getY() + 1);
                break;
        }
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
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getSpeed() {
        return speed;
    }

    public DirectionEnum getDirectionEnum() {
        return directionEnum;
    }

    public BaseTank getTank() {
        return tank;
    }

    public void setTank(BaseTank tank) {
        this.tank = tank;
    }

    public boolean getRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }
}
