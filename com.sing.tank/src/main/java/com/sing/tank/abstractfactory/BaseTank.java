package com.sing.tank.abstractfactory;

import com.sing.tank.Tank;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.facade.GameModel;
import com.sing.tank.observer.ITankFireObserver;
import com.sing.tank.observer.TankFireEvent;
import com.sing.tank.observer.TankFireObserver;
import com.sing.tank.strategy.DefaultFireStrategy;
import com.sing.tank.strategy.FireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class BaseTank extends GameObject {
    /*** 坦克方向 **/
    private DirectionEnum directionEnum;
    /*** 坦克速度 **/
    private int speed = PropertyManager.getInstance().tankSpeed;
    /*** 是否移动 **/
    private boolean moving = false;

    /*** 坦克分组 **/
    private GroupEnum groupEnum;
    /*** random **/
    private Random random = new Random();

    private int paintCount = 0;
    /*** 是否重复复活 */
    private boolean repeat = false;

    /*** 生命值 */
    private int life = 1;


    public BaseTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum,boolean repeat) {
        this.setX(x);
        this.setY(y);
        this.setInitX(x);
        this.setInitY(y);
        this.directionEnum = directionEnum;
        this.groupEnum = groupEnum;
        this.repeat = repeat;
        if(this.groupEnum == GroupEnum.GOOD){
            this.setLife(PropertyManager.getInstance().goodTankLife);
        }else {
            this.setLife(PropertyManager.getInstance().badTankLife);
            this.setSpeed(3);
        }
    }

    public abstract void paint(Graphics graphics);

    public void fire() {
        GameModel.getInstance().getFireStrategy().fire(this);
    }

    /**
     * 退回上一次位置
     */
    public void back() {
        this.setX(this.getOldX());
        this.setY(this.getOldY());
    }

    List<ITankFireObserver> tankFireObserverList = new ArrayList<>();
    {
        tankFireObserverList.add(new TankFireObserver());
    }

    public void handleFireKey() {
        TankFireEvent tankFireEvent = new TankFireEvent(this);
        for (ITankFireObserver tankFireObserver : tankFireObserverList) {
            tankFireObserver.actionOnFire(tankFireEvent);
        }
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

    public boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
