package com.sing.tank.chainofresponsibility;

import com.sing.tank.abstractfactory.GameObject;

/**
 * 碰撞接口
 */
public interface Collider {
    boolean collide(GameObject gameObject1, GameObject gameObject2);
}
