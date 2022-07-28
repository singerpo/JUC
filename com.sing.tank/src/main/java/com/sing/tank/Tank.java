package com.sing.tank;

import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.ResourceManager;

import java.awt.*;

/**
 * 坦克
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Tank extends BaseTank {

    public Tank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, boolean repeat) {
        super(x, y, directionEnum, groupEnum, repeat);
    }

    public void paint(Graphics graphics) {
        if (!this.getLive()) {
            if (this.getGroupEnum() == GroupEnum.BAD) {
                GameModel.getInstance().setBeatTankNum(GameModel.getInstance().getBeatTankNum() + 1);
            }
            if (GameModel.getInstance().getEndless()) {
                if (this.getRepeat() && this.getGroupEnum() == GroupEnum.BAD) {
                    this.setLive(true);
                    this.setX(this.getInitX());
                    this.setY(this.getInitY());
                    this.setDirectionEnum(DirectionEnum.DOWN);
                } else {
                    GameModel.getInstance().remove(this);
                }
            } else {
                GameModel.getInstance().remove(this);
            }
            return;
        }
        switch (this.getDirectionEnum()) {
            case UP:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    this.setWidth(ResourceManager.goodTankU.getWidth());
                    this.setHeight(ResourceManager.goodTankU.getHeight());
                    graphics.drawImage(ResourceManager.goodTankU, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                } else {
                    this.setWidth(ResourceManager.tankU.getWidth());
                    this.setHeight(ResourceManager.tankU.getHeight());
                    graphics.drawImage(ResourceManager.tankU, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                }
                break;
            case DOWN:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    this.setWidth(ResourceManager.goodTankD.getWidth());
                    this.setHeight(ResourceManager.goodTankD.getHeight());
                    graphics.drawImage(ResourceManager.goodTankD, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                } else {
                    this.setWidth(ResourceManager.tankD.getWidth());
                    this.setHeight(ResourceManager.tankD.getHeight());
                    graphics.drawImage(ResourceManager.tankD, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                }
                break;
            case LEFT:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    this.setWidth(ResourceManager.goodTankL.getWidth());
                    this.setHeight(ResourceManager.goodTankL.getHeight());
                    graphics.drawImage(ResourceManager.goodTankL, this.getX(), getY(), this.getWidth(), this.getHeight(), null);
                } else {
                    this.setWidth(ResourceManager.tankL.getWidth());
                    this.setHeight(ResourceManager.tankL.getHeight());
                    graphics.drawImage(ResourceManager.tankL, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                }
                break;
            case RIGHT:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    this.setWidth(ResourceManager.goodTankR.getWidth());
                    this.setHeight(ResourceManager.goodTankR.getHeight());
                    graphics.drawImage(ResourceManager.goodTankR, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                } else {
                    this.setWidth(ResourceManager.tankR.getWidth());
                    this.setHeight(ResourceManager.tankR.getHeight());
                    graphics.drawImage(ResourceManager.tankR, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                }
                break;

        }
        randomDirection();
        move();
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();
    }

    /**
     * 敌对坦克随机方向
     */
    private void randomDirection() {
        if (this.getGroupEnum() == GroupEnum.BAD) {
            this.setMoving(true);
            if (this.getRandom().nextInt(100) > 95) {
                int direct = this.getRandom().nextInt(4);
                this.setDirectionEnum(DirectionEnum.values()[direct]);
            }
        }
    }

    /**
     * 坦克移动
     */
    private void move() {
        this.setOldX(this.getX());
        this.setOldY(this.getY());
        if (this.getDirectionEnum() != null && this.getMoving()) {
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
        }
        boundCheck();
        this.setPaintCount(this.getPaintCount() + 1);
        if (GroupEnum.BAD.equals(this.getGroupEnum())) {
            if (this.getRandom().nextInt(100) > 95) {
                this.fire();
            }
        }
    }

    private void boundCheck() {
        if (this.getX() < 2) {
            this.setX(2);
        }
        if (this.getX() > TankFrame.GAME_WIDTH - this.getWidth() - 2) {
            this.setX(TankFrame.GAME_WIDTH - this.getWidth() - 2);
        }
        if (this.getY() < 30 - 2) {
            this.setY(30 - 2);
        }
        if (this.getY() > TankFrame.GAME_HEIGHT - this.getHeight() - 2) {
            this.setY(TankFrame.GAME_HEIGHT - this.getHeight() - 2);
        }
    }


}
