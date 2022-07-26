package com.sing.tank.observer;

import com.sing.tank.abstractfactory.BaseTank;

/**
 * @author songbo
 * @since 2022-07-26
 */
public class TankFireObserver implements ITankFireObserver {
    @Override
    public void actionOnFire(TankFireEvent event) {
        BaseTank baseTank = event.getSource();
        baseTank.fire();
    }
}
