package com.sing.tank;

import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.manager.ResourceManager;

import java.awt.*;
import java.util.Random;

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
            if(this.getRepeat()){
                this.setRepeat(false);
                this.setLive(true);
                this.setLife(PropertyManager.getInstance().goodTankLife);
                this.setPaintCount(0);
                this.setX(this.getInitX());
                this.setY(this.getInitY());
                this.setDirectionEnum(DirectionEnum.UP);
            }else {
                GameModel.getInstance().remove(this);
            }
            return;
        }
        this.setPaintCount(this.getPaintCount() + 1);
        switch (this.getDirectionEnum()) {
            case UP:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankU, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                } else {
                    graphics.drawImage(ResourceManager.tankU, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                }
                break;
            case DOWN:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankD, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                } else {
                    graphics.drawImage(ResourceManager.tankD, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                }
                break;
            case LEFT:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankL, this.getX(), getY(), this.getWidth(), this.getHeight(), null);
                } else {
                    graphics.drawImage(ResourceManager.tankL, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                }
                break;
            case RIGHT:
                if (this.getGroupEnum() == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankR, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                } else {
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
            Random random = new Random();
            if (random.nextInt(100) > 95) {
                int direct = random.nextInt(4);
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
        if (GroupEnum.BAD.equals(this.getGroupEnum())) {
            Random random = new Random();
            if (random.nextInt(100) > 95) {
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
