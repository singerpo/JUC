package com.sing.tank.strategy;


import com.sing.tank.abstractfactory.BaseTank;

import java.io.Serializable;

/**
 * 策略模式
 */
public interface FireStrategy extends Serializable {
    void fire(BaseTank tank);
}
