package com.sing.tank.decotator;

import com.sing.tank.abstractfactory.GameObject;

import java.awt.*;

/**
 * 装饰模式
 * @author songbo
 * @since 2022-07-26
 */
public class GameDecorator extends GameObject {
    GameObject gameObject;

    public GameDecorator(GameObject gameObject){
        this.gameObject = gameObject;
    }

    @Override
    public void paint(Graphics graphics) {
        gameObject.paint(graphics);
    }
}
