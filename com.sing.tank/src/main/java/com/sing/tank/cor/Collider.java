package com.sing.tank.cor;

import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

public interface Collider {
    void collide(GameObject gameObject1, GameObject gameObject2, GameModel gameModel);
}
