package com.sing.tank.abstractfactory;

import com.sing.tank.*;

public class RectFactory extends AbstractGameFactory {
    @Override
    public BaseTank createTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame) {
        return new Tank(x,y,directionEnum,groupEnum,tankFrame);
    }

    @Override
    public BaseExplode createExplode(BaseTank baseTank) {
        return new RectExplode(baseTank);
    }

    @Override
    public BaseBullet createBullet(DirectionEnum directionEnum, BaseTank baseTank) {
        return new RectBullet(directionEnum,baseTank);
    }
}
