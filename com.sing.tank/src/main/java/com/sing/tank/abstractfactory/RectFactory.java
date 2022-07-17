package com.sing.tank.abstractfactory;

import com.sing.tank.DirectionEnum;
import com.sing.tank.GroupEnum;
import com.sing.tank.TankFrame;

public class RectFactory extends AbstractGameFactory{
    @Override
    public BaseTank createTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame) {
        return null;
    }

    @Override
    public BaseExplode createExplode(BaseTank baseTank) {
        return null;
    }

    @Override
    public BaseBullet createBullet(BaseTank baseTank) {
        return null;
    }
}
