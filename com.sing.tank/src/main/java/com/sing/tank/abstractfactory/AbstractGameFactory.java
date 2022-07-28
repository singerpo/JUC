package com.sing.tank.abstractfactory;

import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;

/**
 * 抽象工厂
 * @author songbo
 * @since 2022-07-15
 */
public abstract class AbstractGameFactory {
    public abstract BaseTank createTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, boolean repeat);

    public abstract BaseExplode createExplode(BaseTank baseTank);

    public abstract BaseBullet createBullet(DirectionEnum directionEnum, BaseTank baseTank);

}
