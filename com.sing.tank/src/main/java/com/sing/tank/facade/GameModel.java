package com.sing.tank.facade;

import com.sing.tank.Obstacle;
import com.sing.tank.TankFrame;
import com.sing.tank.abstractfactory.AbstractGameFactory;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.DefaultFactory;
import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.cor.*;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.manager.PropertyManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author songbo
 * @since 2022-07-22
 */
public class GameModel {
    private List<GameObject> gameObjects;
    private BaseTank mainTank;
    private BaseTank otherTank;
    private AbstractGameFactory gameFactory = new DefaultFactory();

    Collider collider = new BulletTankCollider();
    Collider collider1 = new TankTankCollider();
    Collider collider2 = new ObstacleTankCollider();
    Collider collider3 = new ObstacleBulletCollider();

    public GameModel() {

    }

    public void init() {
        gameObjects = new ArrayList<>();
        for (int x = 100; x <= TankFrame.GAME_WIDTH - 100 - 30; x += 31) {
            Obstacle obstacle = new Obstacle(x, 200, this);
            add(obstacle);
        }


        mainTank = this.gameFactory.createTank(50, 60, DirectionEnum.DOWN, GroupEnum.GOOD, this);
        otherTank = this.gameFactory.createTank(TankFrame.GAME_WIDTH - 50 * 4, 60, DirectionEnum.DOWN, GroupEnum.GOOD, this);
        add(mainTank);
        add(otherTank);
        //60为间距
        int max = TankFrame.GAME_HEIGHT / (60 + mainTank.getHeight()) - 1;
        for (int i = 1; i <= max; i++) {
            add(this.gameFactory.createTank(TankFrame.GAME_WIDTH - mainTank.getWidth() * 2, (60 + mainTank.getHeight()) * i + 60, DirectionEnum.DOWN, GroupEnum.BAD, this));
        }
        Random random = new Random();
        for (int i = 0; i < PropertyManager.getInstance().initTankCount - 2 - max; i++) {
            add(this.gameFactory.createTank(random.nextInt(TankFrame.GAME_WIDTH - 100), random.nextInt(TankFrame.GAME_HEIGHT - 100), DirectionEnum.DOWN, GroupEnum.BAD, this));
        }
    }

    public void add(GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.YELLOW);
        // graphics.drawString("子弹的数量：" + bullets.size(), 10, 60);
        // graphics.drawString("坦克的数量：" + tanks.size(), 100, 60);
        graphics.setColor(color);
        GameObject gameObject;
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObject = gameObjects.get(i);
            if (gameObject.getLive()) {
                gameObject.paint(graphics);
            }
        }
        //互相碰撞
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject1 = gameObjects.get(i);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject gameObject2 = gameObjects.get(j);
                collider.collide(gameObject1, gameObject2, this);
                collider1.collide(gameObject1, gameObject2, this);
                collider2.collide(gameObject1, gameObject2, this);
                collider3.collide(gameObject1, gameObject2, this);
            }
        }
    }

    public BaseTank getMainTank() {
        return mainTank;
    }

    public void setMainTank(BaseTank mainTank) {
        this.mainTank = mainTank;
    }

    public BaseTank getOtherTank() {
        return otherTank;
    }

    public void setOtherTank(BaseTank otherTank) {
        this.otherTank = otherTank;
    }

    public AbstractGameFactory getGameFactory() {
        return gameFactory;
    }

    public void setGameFactory(AbstractGameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }
}
