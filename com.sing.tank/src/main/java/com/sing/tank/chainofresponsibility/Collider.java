package com.sing.tank.chainofresponsibility;

import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

/**
 * 碰撞接口
 */
public interface Collider {
    boolean collide(GameObject gameObject1, GameObject gameObject2);
}
