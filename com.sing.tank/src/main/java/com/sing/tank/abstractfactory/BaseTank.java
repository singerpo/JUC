package com.sing.tank.abstractfactory;

import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.facade.GameModel;
import com.sing.tank.strategy.FireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

import java.awt.*;
import java.util.Random;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class BaseTank extends GameObject {
    /*** 主窗口 **/
    private GameModel gameModel;
    /*** 坦克方向 **/
    private DirectionEnum directionEnum;
    /*** 坦克速度 **/
    private int speed = PropertyManager.getInstance().tankSpeed;
    /*** 是否移动 **/
    private boolean moving = false;

    /*** 坦克宽度 **/
    private int width = 60;
    /*** 坦克高度 **/
    private int height = 60;
    /*** 坦克分组 **/
    private GroupEnum groupEnum;
    /*** random **/
    private Random random = new Random();

    private int paintCount = 0;
    private FireStrategy fireStrategy = new FourDirectionFireStrategy();

    public BaseTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, GameModel gameModel) {
        this.setX(x);
        this.setY(y);
        this.directionEnum = directionEnum;
        this.gameModel = gameModel;
        this.groupEnum = groupEnum;
    }

    public abstract void paint(Graphics graphics);

    public abstract void fire();

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
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
