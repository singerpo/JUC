package com.sing.tank.abstractfactory;

import com.sing.tank.*;

import java.awt.*;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class BaseBullet {
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
    public abstract void paint(Graphics graphics);

    public BaseBullet(DirectionEnum directionEnum,BaseTank tank) {
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
        if (this.isLive()) {
            for (Obstacle obstacle : this.getTankFrame().getObstacles()) {
                if(bulletRect.intersects(obstacle.getRectangle())){
                    this.setLive(false);
                    obstacle.setLive(false);
                    return;
                }
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void setTankFrame(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
