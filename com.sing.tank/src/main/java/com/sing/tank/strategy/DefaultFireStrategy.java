package com.sing.tank.strategy;

import com.sing.tank.*;
import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.decotator.RectDecorator;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;

/**
 * 默认开火策略
 */
public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(BaseTank tank) {
        if (!tank.getLive()) {
            return;
        }
        int diff = 800;
        if (tank.getGroupEnum() == GroupEnum.GOOD) {
            diff = 200;
        }
        if (tank.getPaintCount() > diff / TankFrame.PAINT_DIFF) {
            tank.setPaintCount(0);
            BaseBullet baseBullet = GameModel.getInstance().getGameFactory().createBullet(tank.getDirectionEnum(), tank);
            GameModel.getInstance().add(baseBullet);
            if (tank.getGroupEnum() == GroupEnum.GOOD) {
                new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
            }
        }
    }
}
