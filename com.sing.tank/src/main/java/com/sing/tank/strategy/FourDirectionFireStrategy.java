package com.sing.tank.strategy;

import com.sing.tank.*;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;

/**
 * 四个方向开火策略
 */
public class FourDirectionFireStrategy implements FireStrategy {
    @Override
    public void fire(BaseTank tank) {
        if (!tank.getLive()) {
            return;
        }
        int diff = 500;
        if (tank.getPaintCount() > diff / TankFrame.PAINT_DIFF) {
            tank.setPaintCount(0);
            if (tank.getGroupEnum() == GroupEnum.GOOD) {
                for (DirectionEnum directionEnum : DirectionEnum.values()) {
                    tank.getGameModel().getGameFactory().createBullet(directionEnum, tank);
                }
                new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
            } else {
                tank.getGameModel().getGameFactory().createBullet(tank.getDirectionEnum(), tank);
            }
        }
    }
}
