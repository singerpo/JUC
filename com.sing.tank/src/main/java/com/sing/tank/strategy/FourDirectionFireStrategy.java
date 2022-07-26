package com.sing.tank.strategy;

import com.sing.tank.*;
import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.decotator.RectDecorator;
import com.sing.tank.decotator.TailDecorator;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;

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
                     BaseBullet baseBullet = GameModel.getInstance().getGameFactory().createBullet(directionEnum, tank);
                     GameModel.getInstance().add(baseBullet);
                 }
                 new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
             } else {
                 BaseBullet baseBullet = GameModel.getInstance().getGameFactory().createBullet(tank.getDirectionEnum(), tank);
                 GameModel.getInstance().add(baseBullet);
             }

        }
    }
}
