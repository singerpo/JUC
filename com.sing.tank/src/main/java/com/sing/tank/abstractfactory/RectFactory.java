package com.sing.tank.abstractfactory;

import com.sing.tank.*;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;

public class RectFactory extends AbstractGameFactory {
    @Override
    public BaseTank createTank(int x, int y, DirectionEnum directionEnum, GroupEnum groupEnum, boolean repeat) {
        return new Tank(x,y,directionEnum,groupEnum,repeat);
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
