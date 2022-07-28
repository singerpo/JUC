package com.sing.tank;

import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

import java.awt.*;

/**
 * 障碍物
 */
public class Obstacle extends GameObject {
    public Obstacle(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void paint(Graphics graphics) {
        if (!this.getLive()) {
            GameModel.getInstance().remove(this);
            return;
        }
//        graphics.drawImage(ResourceManager.obstacle, x, y, this.width, this.height, null);
        this.setWidth(35);
        this.setHeight(35);
        Color color = graphics.getColor();
        graphics.setColor(GameModel.getInstance().getObstacleColor());
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(color);
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();
    }
}
