package com.sing.tank.chainofresponsibility;

import com.sing.tank.Obstacle;
import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

/**
 * 障碍物和子弹碰撞
 * @author songbo
 * @since 2022-07-23
 */
public class ObstacleBulletCollider implements Collider {
    @Override
    public boolean collide(GameObject gameObject1, GameObject gameObject2) {
        if (gameObject1.getLive() && gameObject2.getLive()) {
            if (gameObject1 instanceof Obstacle && gameObject2 instanceof BaseBullet) {
                if ((gameObject1).getRectangle().intersects((gameObject2).getRectangle())) {
                    if(!((Obstacle) gameObject1).getStable()){
                        gameObject1.setLive(false);
                    }
                    gameObject2.setLive(false);
                    return false;
                }
            } else if (gameObject1 instanceof BaseBullet && gameObject2 instanceof Obstacle) {
                collide(gameObject2, gameObject1);
            }
        }
        return true;
    }
}
