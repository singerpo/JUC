package com.sing.tank;

import java.awt.*;
import java.util.Random;

/**
 * 坦克
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Tank {
    /*** 主窗口 **/
    private TankFrame tankFrame;
    /*** 坦克x坐标 **/
    private int x;
    /*** 坦克y坐标 **/
    private int y;
    /*** 坦克方向 **/
    private DirectionEnum directionEnum = DirectionEnum.DOWN;
    /*** 坦克速度 **/
    private int speed = 5;
    /*** 是否移动 **/
    private boolean moving = false;
    /*** 是否存活 **/
    private boolean live = true;
    /*** 坦克宽度 **/
    private int width = 50;
    /*** 坦克高度 **/
    private int height = 50;
    /*** 坦克分组 **/
    private GroupEnum groupEnum;
    /*** random **/
    Random random = new Random();

    public Tank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
        this.tankFrame = tankFrame;
        this.groupEnum = groupEnum;

    }

    public void paint(Graphics graphics) {
        if (!this.live) {
            tankFrame.tanks.remove(this);
            return;
        }
        switch (this.directionEnum){
            case UP:
                graphics.drawImage(ResourceManager.tankU, x, y, this.width, this.height, null);
                break;
            case DOWN:
                graphics.drawImage(ResourceManager.tankD, x, y, this.width, this.height, null);
                break;
            case LEFT:
                graphics.drawImage(ResourceManager.tankL, x, y, this.width, this.height, null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceManager.tankR, x, y, this.width, this.height, null);
                break;

        }
        if (this.groupEnum == GroupEnum.BAD) {
            this.moving = true;
            int direct = random.nextInt(20);
            switch (direct){
                case 1:
                    this.directionEnum = DirectionEnum.UP;
                    break;
                case 2:
                    this.directionEnum = DirectionEnum.DOWN;
                    break;
                case 3:
                    this.directionEnum = DirectionEnum.LEFT;
                    break;
                case 4:
                    this.directionEnum = DirectionEnum.RIGHT;
                    break;
            }

        }
        move();

    }

    /**
     * 坦克移动
     */
    private void move(){
        if (this.directionEnum != null && moving) {
            switch (this.directionEnum) {
                case UP:
                    if (this.y - this.speed >= 25) {
                        this.y -= this.speed;
                    }
                    break;
                case DOWN:
                    if (this.y + this.speed <= TankFrame.GAME_HEIGHT - this.height) {
                        this.y += this.speed;
                    }

                    break;
                case LEFT:
                    if (this.x - this.speed >= 0) {
                        this.x -= this.speed;
                    }
                    break;
                case RIGHT:
                    if (this.x + this.speed <= TankFrame.GAME_WIDTH - this.width) {
                        this.x += this.speed;
                    }

                    break;
            }
        }
        if(GroupEnum.BAD.equals(this.groupEnum)){
            if(random.nextInt(20) > 16){
                this.fire();
            }
        }
    }

    public void fire() {
        Bullet bullet = new Bullet(this.directionEnum, this.tankFrame, this);
        this.tankFrame.bullets.add(bullet);
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }


    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
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

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void setTankFrame(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
    }

    public GroupEnum getGroupEnum() {
        return groupEnum;
    }

    public void setGroupEnum(GroupEnum groupEnum) {
        this.groupEnum = groupEnum;
    }
}
