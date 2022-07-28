package com.sing.tank.abstractfactory;

import com.sing.tank.*;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;

/**
 * @author songbo
 * @since 2022-07-15
 */
public class DefaultFactory extends AbstractGameFactory {
    @Override
    public BaseTank createTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, boolean repeat) {
        return new Tank(x, y, directionEnum, groupEnum, repeat);
    }

    @Override
    public BaseExplode createExplode(BaseTank baseTank) {
        return new Explode(baseTank);
    }

    @Override
    public BaseBullet createBullet(DirectionEnum directionEnum, BaseTank baseTank) {
        return new Bullet(directionEnum, baseTank);
    }

}
