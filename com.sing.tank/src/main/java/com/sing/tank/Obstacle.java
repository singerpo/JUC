package com.sing.tank;

import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.facade.GameModel;

import java.awt.*;

/**
 * 障碍物
 */
public class Obstacle extends GameObject {
    private boolean stable = false;
    private boolean home = false;
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
        this.setWidth(GameModel.getInstance().getObstacleSize());
        this.setHeight(GameModel.getInstance().getObstacleSize());
        Color color = graphics.getColor();
        // 不消失的障碍物白色，其他变化
        if(this.stable){
            graphics.setColor(Color.WHITE);
        }else if(this.home){
            graphics.setColor(new Color(255,215,0));
        } else {
            graphics.setColor(GameModel.getInstance().getObstacleColor());
        }
        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.setColor(color);
        // 如果障碍物需要保护，加上文字
        if(this.home){
            color = graphics.getColor();
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font(null, Font.BOLD, 18));
            graphics.drawString("我的家",this.getX(), this.getY()+this.getHeight()/2);
            graphics.setColor(color);
        }
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();
    }

    public boolean getStable() {
        return stable;
    }

    public void setHome(boolean home) {
        this.home = home;
    }
}
