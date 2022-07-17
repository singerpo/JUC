package com.sing.tank;

import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.BaseTank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Bullet extends BaseBullet {


    public Bullet(BaseTank tank) {
        this(tank.getDirectionEnum(), tank);
    }

    public Bullet(DirectionEnum directionEnum, BaseTank tank) {
        super(directionEnum, tank);
    }


    public void paint(Graphics graphics) {
        if (!this.isLive()) {
            this.getTankFrame().getBullets().remove(this);
            return;
        }
        switch (this.getDirectionEnum()) {
            case UP:
                BufferedImage bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletU : ResourceManager.bulletU;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
            case DOWN:
                bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletD : ResourceManager.bulletD;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
            case LEFT:
                bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletL : ResourceManager.bulletL;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
            case RIGHT:
                bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletR : ResourceManager.bulletR;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
        }
        move();
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();

    }

    /**
     * 子弹移动
     */
    private void move() {
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
    public void collide() {
        // 通过是否相交来判断是否相撞
        Rectangle bulletRect = this.getRectangle();
        for (BaseTank tank : this.getTankFrame().getTanks()) {
            // 坦克自己的子弹不会打自己;同一个组的坦克子弹不打自己组的
            if (tank == this.getTank() || tank.getGroupEnum().equals(this.getTank().getGroupEnum())) {
                continue;
            }
            Rectangle tankRect = tank.getRectangle();
            if (bulletRect.intersects(tankRect)) {
                tank.setLive(false);

                this.setLive(false);
                //在坦克中心位置爆炸
                Explode explode = new Explode(tank);
                this.getTankFrame().getExplodes().add(explode);
                return;
            }
        }
        if (this.isLive() && bulletRect.intersects(this.getTankFrame().getObstacle().getRectangle())) {
            this.setLive(false);
            this.getTankFrame().getObstacle().setLive(false);
        }
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
}
