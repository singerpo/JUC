package com.sing.tank.decotator;

import com.sing.tank.abstractfactory.GameObject;

import java.awt.*;

/**
 * @author songbo
 * @since 2022-07-26
 */
public class TailDecorator extends GameDecorator {
    public TailDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        this.setLive(super.gameObject.getLive());
        if(!super.gameObject.getLive()){
            return;
        }
        this.setX(super.gameObject.getX());
        this.setY(super.gameObject.getY());
        this.setWidth(super.gameObject.getX() + super.gameObject.getWidth());
        this.setHeight(super.gameObject.getY() + super.gameObject.getHeight());
        Color color = graphics.getColor();
        graphics.setColor(Color.RED);
        graphics.drawLine(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(color);
    }
}
