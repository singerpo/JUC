package com.sing.tank;

import com.sing.tank.strategy.DefaultFireStrategy;
import com.sing.tank.strategy.FireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

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
    private DirectionEnum directionEnum;
    /*** 坦克速度 **/
    private int speed = PropertyManager.getInstance().tankSpeed;
    /*** 是否移动 **/
    private boolean moving = false;
    /*** 是否存活 **/
    private boolean live = true;
    /*** 坦克宽度 **/
    private int width = 60;
    /*** 坦克高度 **/
    private int height = 60;
    /*** 坦克分组 **/
    private GroupEnum groupEnum;
    /*** random **/
    Random random = new Random();
    Rectangle rectangle = new Rectangle();
    int paintCount = 0;
    FireStrategy fireStrategy = new FourDirectionFireStrategy();

    public Tank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
        this.tankFrame = tankFrame;
        this.groupEnum = groupEnum;

    }

    public void paint(Graphics graphics) {
        if (!this.live) {
            // 当坦克发生销毁，随机产生一个敌对坦克
//            int r = random.nextInt(4);
//            if (r == 3) {
//                this.setGroupEnum(GroupEnum.BAD);
//                this.setX(random.nextInt(TankFrame.GAME_WIDTH - 100));
//                this.setY(random.nextInt(TankFrame.GAME_HEIGHT - 100));
//                this.setLive(true);
//            } else {
//            }
            tankFrame.getTanks().remove(this);
            return;
        }
        switch (this.directionEnum) {
            case UP:
                if (this.groupEnum == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankU, x, y, this.width, this.height, null);
                } else {
                    graphics.drawImage(ResourceManager.tankU, x, y, this.width, this.height, null);
                }
                break;
            case DOWN:
                if (this.groupEnum == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankD, x, y, this.width, this.height, null);
                } else {
                    graphics.drawImage(ResourceManager.tankD, x, y, this.width, this.height, null);
                }
                break;
            case LEFT:
                if (this.groupEnum == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankL, x, y, this.width, this.height, null);
                } else {
                    graphics.drawImage(ResourceManager.tankL, x, y, this.width, this.height, null);
                }
                break;
            case RIGHT:
                if (this.groupEnum == GroupEnum.GOOD) {
                    graphics.drawImage(ResourceManager.goodTankR, x, y, this.width, this.height, null);
                } else {
                    graphics.drawImage(ResourceManager.tankR, x, y, this.width, this.height, null);
                }
                break;

        }
        randomDirection();
        move();
        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = this.width;
        rectangle.height = this.height;
    }

    /**
     * 敌对坦克随机方向
     */
    private void randomDirection() {
        if (this.groupEnum == GroupEnum.BAD) {
            this.moving = true;
            if (random.nextInt(100) > 95) {
                int direct = random.nextInt(4);
                switch (direct) {
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
        }
    }

    /**
     * 坦克移动
     */
    private void move() {
        if (this.directionEnum != null && moving) {
            int oldX = this.x;
            int oldY = this.y;

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

            if (this.rectangle.intersects(this.getTankFrame().getObstacle().getRectangle())) {
                if(this.y < this.getTankFrame().getObstacle().getY()){
                    this.y = oldY-1;
                    this.x = oldX;
                }
                if(this.y > this.getTankFrame().getObstacle().getY()){
                    this.y = oldY+1;
                    this.x = oldX;
                }
            }
        }
        boundCheck();
        this.paintCount++;
        if (GroupEnum.BAD.equals(this.groupEnum)) {
            if (random.nextInt(100) > 95) {
                this.fire();
            }
        }
    }

    private void boundCheck() {
        if (this.x < 2) {
            this.x = 2;
        }
        if (this.x > TankFrame.GAME_WIDTH - this.width - 2) {
            this.x = TankFrame.GAME_WIDTH - this.width - 2;
        }
        if (this.y < 30 - 2) {
            this.y = 30 - 2;
        }
        if (this.y > TankFrame.GAME_HEIGHT - this.height - 2) {
            this.y = TankFrame.GAME_HEIGHT - this.height - 2;
        }
    }

    public void fire() {
        fireStrategy.fire(this);
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


    public boolean getLive() {
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public int getPaintCount() {
        return paintCount;
    }

    public void setPaintCount(int paintCount) {
        this.paintCount = paintCount;
    }
}
