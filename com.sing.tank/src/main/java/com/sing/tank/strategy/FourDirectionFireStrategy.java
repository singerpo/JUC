package com.sing.tank.strategy;

import com.sing.tank.*;

public class FourDirectionFireStrategy implements FireStrategy{
    @Override
    public void fire(Tank tank) {
        if (!tank.getLive()) {
            return;
        }
        int diff = 500;
        if (tank.getPaintCount() > diff / TankFrame.PAINT_DIFF) {
            tank.setPaintCount(0);
            if (tank.getGroupEnum() == GroupEnum.GOOD) {
                for (DirectionEnum directionEnum : DirectionEnum.values()) {
                    new Bullet(directionEnum, tank.getTankFrame(), tank);
                }
                new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
            }else {
                new Bullet(tank.getDirectionEnum(), tank.getTankFrame(), tank);
            }
        }
    }
}
