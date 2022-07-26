package com.sing.tank;

import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

import java.awt.*;

/**
 * 障碍物
 */
public class Obstacle extends GameObject {
    private int width = 30;
    private int height = 30;


    public Obstacle(int x, int y, GameModel gameModel) {
        this.setX(x);
        this.setY(y);
        GameModel.getInstance().add(this);

    }

    public void paint(Graphics graphics) {
        if (!this.getLive()) {
            GameModel.getInstance().remove(this);
            return;
        }
//        graphics.drawImage(ResourceManager.obstacle, x, y, this.width, this.height, null);
        Color color = graphics.getColor();
        graphics.setColor(Color.BLUE);
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(color);
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.width;
        this.getRectangle().height = this.height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
