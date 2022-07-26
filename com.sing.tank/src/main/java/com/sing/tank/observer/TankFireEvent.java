package com.sing.tank.observer;

import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.GameObject;

/**
 * @author songbo
 * @since 2022-07-26
 */
public class TankFireEvent {
    BaseTank baseTank;

    public TankFireEvent(BaseTank baseTank){
        this.baseTank = baseTank;
    }

    public BaseTank getSource(){
        return this.baseTank;
    }
}
