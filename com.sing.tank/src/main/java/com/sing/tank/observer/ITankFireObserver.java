package com.sing.tank.observer;

/**
 * @author songbo
 * @since 2022-07-26
 */
public interface ITankFireObserver {
    void actionOnFire(TankFireEvent event);
}
