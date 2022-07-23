package com.sing.tank.cor;

import com.sing.tank.abstractfactory.GameObject;

public interface Collider {
    void collide(GameObject gameObject1, GameObject gameObject2);
}
