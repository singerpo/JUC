package com.sing.tank;

import com.sing.tank.abstractfactory.BaseTank;

import java.awt.*;

/**
 * 坦克
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Tank extends BaseTank {


    public Tank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame) {
        super(x, y, directionEnum, groupEnum, tankFrame);
    }

    public void paint(Graphics graphics) {
        if (!this.getLive()) {
            // 当坦克发生销毁，随机产生一个敌对坦克
//            int r = random.nextInt(4);
//            if (r == 3) {
//                this.setGroupEnum(GroupEnum.BAD);
//                this.setX(random.nextInt(TankFrame.GAME_WIDTH - 100));
//                this.setY(random.nextInt(TankFrame.GAME_HEIGHT - 100));
//                this.setLive(true);
//            } else {
//            }
            this.getTankFrame().getTanks().remove(this);
            return;
        }
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
        if (this.getDirectionEnum() != null && this.getMoving()) {
            int oldX = this.getX();
            int oldY = this.getY();
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

            if (this.getRectangle().intersects(this.getTankFrame().getObstacle().getRectangle()) && this.getTankFrame().getObstacle().isLive()) {
                if (this.getY() < this.getTankFrame().getObstacle().getY()) {
                    this.setY(oldY - 1);
                    this.setX(oldX);
                }
                if (this.getY() > this.getTankFrame().getObstacle().getY()) {
                    this.setY(oldY + 1);
                    this.setX(oldX);
                }
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

    public void fire() {
        this.getFireStrategy().fire(this);
    }

}
