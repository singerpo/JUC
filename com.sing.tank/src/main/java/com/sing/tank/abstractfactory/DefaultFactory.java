package com.sing.tank.abstractfactory;

import com.sing.tank.*;

/**
 * @author songbo
 * @since 2022-07-15
 */
public class DefaultFactory extends AbstractGameFactory{
    @Override
    public BaseTank createTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame) {
        return new Tank(x,y,directionEnum,groupEnum,tankFrame);
    }

    @Override
    public BaseExplode createExplode(BaseTank baseTank) {
        return new Explode(baseTank);
    }

    @Override
    public BaseBullet createBullet(BaseTank baseTank) {
        return new Bullet(baseTank);
    }

}
