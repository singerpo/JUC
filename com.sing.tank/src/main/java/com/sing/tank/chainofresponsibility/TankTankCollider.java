package com.sing.tank.chainofresponsibility;

import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

/**
 * @author songbo
 * @since 2022-07-23
 */
public class TankTankCollider implements Collider {
    @Override
    public boolean collide(GameObject gameObject1, GameObject gameObject2, GameModel gameModel) {
        if (gameObject1.getLive() && gameObject2.getLive()) {
            if (gameObject1 instanceof BaseTank && gameObject2 instanceof BaseTank) {
                BaseTank baseTank1 = (BaseTank) gameObject1;
                BaseTank baseTank2 = (BaseTank) gameObject2;
                if ((gameObject1).getRectangle().intersects((gameObject2).getRectangle())) {
                    baseTank1.stay();
                    baseTank2.stay();
                    return true;
                }
            }
        }
        return true;
    }
}
