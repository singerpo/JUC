package com.sing.tank.observer;

import java.io.Serializable;

/**
 * @author songbo
 * @since 2022-07-26
 */
public interface ITankFireObserver extends Serializable {
    void actionOnFire(TankFireEvent event);
}
