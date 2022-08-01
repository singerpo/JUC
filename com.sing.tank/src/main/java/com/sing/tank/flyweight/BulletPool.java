package com.sing.tank.flyweight;

import com.sing.tank.Bullet;

import java.util.ArrayList;
import java.util.List;

/**
 * 享元模式
 * @author songbo
 * @since 2022-07-28
 */
public class BulletPool {
    static List<Bullet> bullets = new ArrayList<>();

    static {
        for (int i = 0; i < 50; i++) {
            bullets.add(new Bullet());
        }
    }

    public  static Bullet getBullet() {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (!bullet.getLive() && bullet.getRemove()) {
                return bullet;
            }
        }
        return new Bullet();
    }
}
