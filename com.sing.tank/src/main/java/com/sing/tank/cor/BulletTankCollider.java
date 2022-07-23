package com.sing.tank.cor;

import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.BaseExplode;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

/**
 * @author songbo
 * @since 2022-07-23
 */
public class BulletTankCollider implements Collider {
    @Override
    public void collide(GameObject gameObject1, GameObject gameObject2, GameModel gameModel) {
        if (gameObject1.getLive() && gameObject2.getLive()) {
            if (gameObject1 instanceof BaseBullet && gameObject2 instanceof BaseTank) {
                BaseBullet baseBullet = (BaseBullet) gameObject1;
                BaseTank baseTank = (BaseTank) gameObject2;
                // 坦克自己的子弹不会打自己;同一个组的坦克子弹不打自己组的
                if (baseBullet.getTank() == baseTank || baseBullet.getTank().getGroupEnum() == baseTank.getGroupEnum()) {
                    return;
                }
                if ((gameObject1).getRectangle().intersects((gameObject2).getRectangle())) {
                    gameObject1.setLive(false);
                    gameObject2.setLive(false);
                    //在坦克中心位置爆炸
                    BaseExplode explode = gameModel.getGameFactory().createExplode(baseTank);
                    gameModel.add(explode);
                }
            } else if (gameObject1 instanceof BaseTank && gameObject2 instanceof BaseBullet) {
                collide(gameObject2, gameObject1, gameModel);
            }
        }
    }
}
