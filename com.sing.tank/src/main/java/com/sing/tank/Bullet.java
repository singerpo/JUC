package com.sing.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Bullet extends BaseBullet {
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

    public Bullet(BaseTank tank) {
        this(tank.getDirectionEnum(),tank);
    }

    public Bullet(DirectionEnum directionEnum,BaseTank tank) {
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



    public void paint(Graphics graphics) {
        if (!live) {
            this.tankFrame.getBullets().remove(this);
            return;
        }
        switch (this.directionEnum) {
            case UP:
                BufferedImage bufferedImage = GroupEnum.GOOD == this.tank.getGroupEnum()? ResourceManager.goodBulletU:ResourceManager.bulletU;
                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();
                graphics.drawImage(bufferedImage, x, y, this.width, this.height, null);
                break;
            case DOWN:
                bufferedImage = GroupEnum.GOOD == this.tank.getGroupEnum()? ResourceManager.goodBulletD:ResourceManager.bulletD;
                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();
                graphics.drawImage(bufferedImage, x, y, this.width, this.height, null);
                break;
            case LEFT:
                bufferedImage = GroupEnum.GOOD == this.tank.getGroupEnum()? ResourceManager.goodBulletL:ResourceManager.bulletL;
                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();
                graphics.drawImage(bufferedImage, x, y, this.width, this.height, null);
                break;
            case RIGHT:
                bufferedImage = GroupEnum.GOOD == this.tank.getGroupEnum()? ResourceManager.goodBulletR:ResourceManager.bulletR;
                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();
                graphics.drawImage(bufferedImage, x, y, this.width, this.height, null);
                break;
        }
        move();
        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = this.width;
        rectangle.height = this.height;

    }

    /**
     * 子弹移动
     */
    private void move() {
        if (this.directionEnum != null) {
            switch (this.directionEnum) {
                case UP:
                    this.y -= this.speed;
                    break;
                case DOWN:
                    this.y += this.speed;
                    break;
                case LEFT:
                    this.x -= this.speed;
                    break;
                case RIGHT:
                    this.x += this.speed;
                    break;
            }
            if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) {
                this.live = false;
            }
            collide();
        }
    }

    /**
     * 判断子弹和坦克碰撞
     */
    public void collide() {
        // 通过是否相交来判断是否相撞
        Rectangle bulletRect = this.rectangle;
        for (Tank tank : tankFrame.getTanks()) {
            // 坦克自己的子弹不会打自己;同一个组的坦克子弹不打自己组的
            if (tank == this.tank || tank.getGroupEnum().equals(this.tank.getGroupEnum())) {
                continue;
            }
            Rectangle bulletRect = this.rectangle;
            Rectangle tankRect = tank.rectangle;
            if (bulletRect.intersects(tankRect)) {
                tank.setLive(false);
                this.live = false;
                //在坦克中心位置爆炸
                Explode explode = new Explode(tank);
                tankFrame.getExplodes().add(explode);
                return;
            }
        }
        if(this.live && bulletRect.intersects(this.tankFrame.getObstacle().getRectangle())){
            this.live = false;
            this.tankFrame.getObstacle().setLive(false);
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
