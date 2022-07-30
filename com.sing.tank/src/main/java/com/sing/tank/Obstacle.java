package com.sing.tank;

import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

import java.awt.*;

/**
 * 障碍物
 */
public class Obstacle extends GameObject {
    private boolean stable = false;
    public Obstacle(int x, int y) {
        this.setX(x);
        this.setY(y);
    }
    public Obstacle(int x, int y,boolean stable) {
        this.setX(x);
        this.setY(y);
        this.stable = stable;
    }

    public void paint(Graphics graphics) {
        if (!this.getLive()) {
            GameModel.getInstance().remove(this);
            return;
        }
//        graphics.drawImage(ResourceManager.obstacle, x, y, this.width, this.height, null);
        this.setWidth(60);
        this.setHeight(60);
        Color color = graphics.getColor();
        if(this.stable){
            graphics.setColor(Color.WHITE);
        }else {
            graphics.setColor(GameModel.getInstance().getObstacleColor());
        }
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(color);
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();
    }

    public boolean getStable() {
        return stable;
    }

    public void setStable(boolean stable) {
        this.stable = stable;
    }
}
