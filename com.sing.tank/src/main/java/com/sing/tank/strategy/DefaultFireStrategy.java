package com.sing.tank.strategy;

import com.sing.tank.*;

public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        if (!tank.getLive()) {
            return;
        }
        int diff = 500;
        if(tank.getGroupEnum() == GroupEnum.GOOD){
            diff = 200;
        }
        if (tank.getPaintCount() > diff / TankFrame.PAINT_DIFF) {
            tank.setPaintCount(0);
            new Bullet(tank.getDirectionEnum(), tank.getTankFrame(), tank);
            if (tank.getGroupEnum() == GroupEnum.GOOD) {
                new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
            }
        }
    }
}
