package com.sing.tank;

import com.sing.tank.abstractfactory.BaseBullet;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.ResourceManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 子弹
 *
 * @author songbo
 * @since 2022-07-08
 */
public class Bullet extends BaseBullet {


    public Bullet(){
        super();
    }
    public Bullet(BaseTank tank) {
        this(tank.getDirectionEnum(), tank);
    }

    public Bullet(DirectionEnum directionEnum, BaseTank tank) {
        super(directionEnum, tank);
    }


    public void paint(Graphics graphics) {
        if(!this.getLive()){
            GameModel.getInstance().remove(this);
            this.setRemove(true);
            return;
        }
        switch (this.getDirectionEnum()) {
            case UP:
                BufferedImage bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletU : ResourceManager.bulletU;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
            case DOWN:
                bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletD : ResourceManager.bulletD;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
            case LEFT:
                bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletL : ResourceManager.bulletL;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
            case RIGHT:
                bufferedImage = GroupEnum.GOOD == this.getTank().getGroupEnum() ? ResourceManager.goodBulletR : ResourceManager.bulletR;
                this.setWidth(bufferedImage.getWidth());
                this.setHeight(bufferedImage.getHeight());
                graphics.drawImage(bufferedImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
                break;
        }
        move();
        this.getRectangle().x = this.getX();
        this.getRectangle().y = this.getY();
        this.getRectangle().width = this.getWidth();
        this.getRectangle().height = this.getHeight();

    }


}
