package com.sing.tank.facade;

import com.sing.tank.Obstacle;
import com.sing.tank.TankFrame;
import com.sing.tank.abstractfactory.AbstractGameFactory;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.DefaultFactory;
import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.chainofresponsibility.ColliderChain;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.strategy.FireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songbo
 * @since 2022-07-22
 */
public class GameModel {
    private static final GameModel INSTANCE = new GameModel();
    private List<GameObject> gameObjects;
    private BaseTank mainTank;
    private BaseTank otherTank;
    /*** 是否暂停 **/
    private boolean pause = false;
    /*** 障碍物颜色**/
    private Color obstacleColor = Color.BLUE;
    // 工厂方法
    private AbstractGameFactory gameFactory = new DefaultFactory();
    // 开火策略
    private FireStrategy fireStrategy = new FourDirectionFireStrategy();
    //碰撞责任链
    ColliderChain colliderChain = new ColliderChain();


    static {
        INSTANCE.init();
    }

    private GameModel() {

    }


    public static GameModel getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化添加障碍物、坦克
     */
    public void init() {
        gameObjects = new ArrayList<>();
        initObstacle();
        mainTank = this.gameFactory.createTank(40, 190, DirectionEnum.DOWN, GroupEnum.GOOD);
        otherTank = this.gameFactory.createTank(20, 50, DirectionEnum.DOWN, GroupEnum.GOOD);
        add(mainTank);
        add(otherTank);
        initBadTank();
    }

    /**
     * 初始化敌对坦克
     */
    private void initBadTank() {
        //60为间距
        int max = TankFrame.GAME_HEIGHT / (60 + 60) - 1;
        for (int i = 1; i <= max; i++) {
            add(this.gameFactory.createTank(TankFrame.GAME_WIDTH - 60 * 2 + 5, (60 + 60) * i, DirectionEnum.DOWN, GroupEnum.BAD));
        }
        initObstacleTank();
    }

    /**
     * 初始化障碍物之间的敌对坦克
     */
    private void initObstacleTank() {
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 200 - 100, DirectionEnum.UP, GroupEnum.BAD));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 200 - 100, DirectionEnum.DOWN, GroupEnum.BAD));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 400 - 100, DirectionEnum.UP, GroupEnum.BAD));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 400 - 100, DirectionEnum.DOWN, GroupEnum.BAD));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 600 - 100, DirectionEnum.UP, GroupEnum.BAD));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 600 - 100, DirectionEnum.DOWN, GroupEnum.BAD));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 800 - 100, DirectionEnum.UP, GroupEnum.BAD));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 800 - 100, DirectionEnum.DOWN, GroupEnum.BAD));
    }

    /**
     * 初始化障碍物
     */
    private void initObstacle() {
        //otherTank周围的障碍物
        add(new Obstacle(13 + 81, 50));
        add(new Obstacle(13 + 81, 50 + 36));
        add(new Obstacle(13 + 81 + 36, 50));
        add(new Obstacle(13 + 81 + 36, 50 + 36));
        add(new Obstacle(13 + 81 + 36 + 36, 50));
        add(new Obstacle(13 + 81 + 36 + 36, 50 + 36));
        add(new Obstacle(20, 110));
        add(new Obstacle(20 + 36, 110));
        add(new Obstacle(20, 110 + 36));
        add(new Obstacle(20 + 36, 110 + 36));
        // 四行障碍物
        for (int x = 100; x <= TankFrame.GAME_WIDTH - 100 - 35; x += 36) {
            add(new Obstacle(x, 200));
            add(new Obstacle(x, 380));
            add(new Obstacle(x, 598));
            add(new Obstacle(x, 780));
        }

        for (int y = 55; y <= 200 - 36; y += 36) {
            add(new Obstacle(TankFrame.GAME_WIDTH / 2, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 - 200, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 + 200, y));
        }

        for (int y = 200 + 36; y <= 400 - 36; y += 36) {
            add(new Obstacle(TankFrame.GAME_WIDTH / 2, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 - 200, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 + 200, y));
        }

        for (int y = 380 + 36; y <= 600 - 36; y += 36) {
            add(new Obstacle(TankFrame.GAME_WIDTH / 2, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 - 200, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 + 200, y));
        }

        for (int y = 598 + 36; y <= 800 - 36; y += 36) {
            add(new Obstacle(TankFrame.GAME_WIDTH / 2, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 - 200, y));
            add(new Obstacle(TankFrame.GAME_WIDTH / 2 + 200, y));
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
                colliderChain.collide(gameObject1, gameObject2);
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

    public boolean getPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public FireStrategy getFireStrategy() {
        return fireStrategy;
    }

    public void setFireStrategy(FireStrategy fireStrategy) {
        this.fireStrategy = fireStrategy;
    }

    public Color getObstacleColor() {
        return obstacleColor;
    }

    public void setObstacleColor(Color obstacleColor) {
        this.obstacleColor = obstacleColor;
    }
}
