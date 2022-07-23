package com.sing.tank.cor;

import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.GameObject;

/**
 * @author songbo
 * @since 2022-07-23
 */
public class BulletTankCollider implements Collider {
    @Override
    public void collide(GameObject gameObject1, GameObject gameObject2) {
        if (gameObject1 instanceof BaseBullet && gameObject2 instanceof BaseTank) {
            if ((gameObject1).getRectangle().intersects((gameObject2).getRectangle())) {
                gameObject1.setLive(false);
                gameObject2.setLive(false);
            }
        }
    }
}
