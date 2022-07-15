package com.sing.tank.strategy;


import com.sing.tank.Tank;

/**
 * 开火策略
 */
public interface FireStrategy {
    void fire(Tank tank);
}
