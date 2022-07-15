package com.sing.tank.abstractfactory;

import com.sing.tank.DirectionEnum;
import com.sing.tank.GroupEnum;
import com.sing.tank.PropertyManager;
import com.sing.tank.TankFrame;
import com.sing.tank.strategy.FireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

import java.awt.*;
import java.util.Random;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class BaseTank {
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
    private Random random = new Random();
    private Rectangle rectangle = new Rectangle();
    private int paintCount = 0;
    private FireStrategy fireStrategy = new FourDirectionFireStrategy();

    public BaseTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
        this.tankFrame = tankFrame;
        this.groupEnum = groupEnum;
    }

    public TankFrame getTankFrame() {
        return tankFrame;
    }

    public void setTankFrame(TankFrame tankFrame) {
        this.tankFrame = tankFrame;
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

    public boolean getMoving() {
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

    public GroupEnum getGroupEnum() {
        return groupEnum;
    }

    public void setGroupEnum(GroupEnum groupEnum) {
        this.groupEnum = groupEnum;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
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

    public FireStrategy getFireStrategy() {
        return fireStrategy;
    }

    public void setFireStrategy(FireStrategy fireStrategy) {
        this.fireStrategy = fireStrategy;
    }
}
