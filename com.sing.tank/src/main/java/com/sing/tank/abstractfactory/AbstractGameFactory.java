package com.sing.tank.abstractfactory;

import com.sing.tank.DirectionEnum;
import com.sing.tank.GroupEnum;
import com.sing.tank.TankFrame;

/**
 * @author songbo
 * @since 2022-07-15
 */
public abstract class AbstractGameFactory {
    public abstract BaseTank createTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, TankFrame tankFrame);

    public abstract BaseExplode createExplode(BaseTank baseTank);

    public abstract BaseBullet createBullet(BaseTank baseTank);

}
