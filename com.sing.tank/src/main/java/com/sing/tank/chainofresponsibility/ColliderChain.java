package com.sing.tank.chainofresponsibility;

import com.sing.tank.abstractfactory.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 责任链
 *
 * @author songbo
 * @since 2022-07-25
 */
public class ColliderChain implements Collider {
    // 不需要随机访问，只要在尾部添加，所以用LinkedList
    private final List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        add(new BulletTankCollider());
        add(new ObstacleBulletCollider());
        add(new TankTankCollider());
        add(new ObstacleTankCollider());
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }

    @Override
    public boolean collide(GameObject gameObject1, GameObject gameObject2) {
        for (Collider collider : colliders) {
            if (!collider.collide(gameObject1, gameObject2)) {
                return false;
            }
        }
        return true;
    }
}
