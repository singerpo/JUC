package com.sing.tank;

import java.awt.*;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Bullet {
    private int speed = 6;
    private int x, y;
    private DirectionEnum directionEnum;
    private Color color;
    private boolean live = true;
    private TankFrame tankFrame;
    private Tank tank;
    private int width = 12;
    private int height = 12;

    public Bullet(DirectionEnum directionEnum, TankFrame tankFrame, Tank tank) {
        switch (directionEnum) {
            case UP:
                this.width = ResourceManager.bulletU.getWidth();
                this.height = ResourceManager.bulletU.getHeight();
                this.x = tank.getX() + (tank.getWidth() - this.width) / 2;
                this.x += 1;
                this.y = tank.getY();
                break;
            case DOWN:
                this.width = ResourceManager.bulletD.getWidth();
                this.height = ResourceManager.bulletD.getHeight();
                this.x = tank.getX() + (tank.getWidth() - this.width) / 2;
                this.x -= 1;
                this.y = tank.getY() + tank.getHeight() - this.height;
                break;
            case LEFT:
                this.width = ResourceManager.bulletL.getWidth();
                this.height = ResourceManager.bulletL.getHeight();
                this.x = tank.getX();
                this.y = tank.getY() + (tank.getHeight() - this.height) / 2;
                break;
            case RIGHT:
                this.width = ResourceManager.bulletR.getWidth();
                this.height = ResourceManager.bulletR.getHeight();
                this.x = tank.getX() + tank.getWidth() - this.width;
                this.y = tank.getY() + (tank.getHeight() - this.height) / 2;
                this.y +=1;
                break;
        }


        this.directionEnum = directionEnum;
        this.tankFrame = tankFrame;
        this.tank = tank;
    }

    public void paint(Graphics graphics) {
        if (!live) {
            this.tankFrame.bullets.remove(this);
            return;
        }
        switch (this.directionEnum) {
            case UP:
                graphics.drawImage(ResourceManager.bulletU, x, y, this.width, this.height, null);
                break;
            case DOWN:
                this.width = ResourceManager.bulletD.getWidth();
                this.height = ResourceManager.bulletD.getHeight();
                graphics.drawImage(ResourceManager.bulletD, x, y, this.width, this.height, null);
                break;
            case LEFT:
                graphics.drawImage(ResourceManager.bulletL, x, y, this.width, this.height, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceManager.bulletR, x, y, this.width, this.height, null);
                break;
        }
        move();

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
    public void collide(){
        // 通过是否相交来判断是否相撞
        for (Tank tank : tankFrame.tanks) {
            // 坦克自己的子弹不会打自己;同一个组的坦克子弹不打自己组的
            if (tank == this.tank || tank.getGroupEnum().equals(this.tank.getGroupEnum())) {
                continue;
            }
            Rectangle bulletRect = new Rectangle(this.x,this.y,this.width,this.height);
            Rectangle tankRect = new Rectangle(tank.getX(),tank.getY(),tank.getWidth(),tank.getHeight());
            if(bulletRect.intersects(tankRect)){
                tank.setLive(false);
                this.live = false;
            }
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
