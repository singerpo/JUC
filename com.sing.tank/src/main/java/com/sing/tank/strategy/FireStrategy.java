package com.sing.tank.strategy;


import com.sing.tank.abstractfactory.BaseTank;

/**
 * 策略模式
 */
public interface FireStrategy {
    void fire(BaseTank tank);
}
