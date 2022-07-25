package com.sing.tank.chainofresponsibility;

import com.sing.tank.Obstacle;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

/**
 * @author songbo
 * @since 2022-07-23
 */
public class ObstacleTankCollider implements Collider {
    @Override
    public boolean collide(GameObject gameObject1, GameObject gameObject2, GameModel gameModel) {
        if (gameObject1.getLive() && gameObject2.getLive()) {
            if (gameObject1 instanceof Obstacle && gameObject2 instanceof BaseTank) {
                BaseTank baseTank = (BaseTank) gameObject2;
                if ((gameObject1).getRectangle().intersects((gameObject2).getRectangle())) {
                    baseTank.stay();
                    return true;
                }
            } else if (gameObject1 instanceof BaseTank && gameObject2 instanceof Obstacle) {
                collide(gameObject2, gameObject1, gameModel);
            }
        }
        return true;
    }
}
