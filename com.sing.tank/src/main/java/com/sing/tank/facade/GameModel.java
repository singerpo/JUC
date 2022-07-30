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
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.strategy.DefaultFireStrategy;
import com.sing.tank.strategy.FireStrategy;

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
    /*** 主站坦克 **/
    private BaseTank mainTank;
    /*** 副坦克 **/
    private BaseTank otherTank;
    /*** 是否暂停 **/
    private boolean pause = false;
    /*** 障碍物颜色**/
    private Color obstacleColor = Color.BLUE;
    /*** 敌对坦克数量**/
    private int badTankNum;
    /*** 击败坦克数量**/
    private int beatTankNum;
    /*** 障碍物数量**/
    private int obstacleNum;
    /*** 是否无尽模式 **/
    private boolean endless = false;

    // 工厂方法
    private AbstractGameFactory gameFactory = new DefaultFactory();
    // 开火策略
    private FireStrategy fireStrategy = new DefaultFireStrategy();
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
        this.badTankNum = 0;
        this.beatTankNum = 0;
        this.obstacleNum = 0;
        initObstacle();
        mainTank = this.gameFactory.createTank(40, 190, DirectionEnum.DOWN, GroupEnum.GOOD, false);
        otherTank = this.gameFactory.createTank(20, 50, DirectionEnum.DOWN, GroupEnum.GOOD, false);
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
            add(this.gameFactory.createTank(TankFrame.GAME_WIDTH - 60 * 2 + 5, (60 + 60) * i, DirectionEnum.DOWN, GroupEnum.BAD, false));
        }
        initObstacleTank();
    }

    /**
     * 初始化障碍物之间的敌对坦克
     */
    private void initObstacleTank() {
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 200 - 100, DirectionEnum.UP, GroupEnum.BAD, true));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 200 - 100, DirectionEnum.DOWN, GroupEnum.BAD, false));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 400 - 100, DirectionEnum.UP, GroupEnum.BAD, true));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 400 - 100, DirectionEnum.DOWN, GroupEnum.BAD, false));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 600 - 100, DirectionEnum.UP, GroupEnum.BAD, true));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 600 - 100, DirectionEnum.DOWN, GroupEnum.BAD, false));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - 100, 800 - 100, DirectionEnum.UP, GroupEnum.BAD, true));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + 100, 800 - 100, DirectionEnum.DOWN, GroupEnum.BAD, true));
    }

    /**
     * 初始化障碍物
     */
    private void initObstacle() {
        // 稳定的障碍物不消失
        add(new Obstacle(100 , 150,true));
        add(new Obstacle(100 , 300,true));
        add(new Obstacle(100 , 450,true));
        add(new Obstacle(100 , 486,true));
        add(new Obstacle(200 , 500,true));
        add(new Obstacle(200 , 536,true));
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
        if (gameObject instanceof BaseTank && gameObject.getLive()) {
            if (((BaseTank) gameObject).getGroupEnum() == GroupEnum.BAD) {
                this.badTankNum++;
            }
        } else if (gameObject instanceof Obstacle && gameObject.getLive()) {
            this.obstacleNum++;
        }
        this.gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        if (gameObject instanceof BaseTank) {
            if (((BaseTank) gameObject).getGroupEnum() == GroupEnum.BAD) {
                this.badTankNum--;
            }
        } else if (gameObject instanceof Obstacle) {
            this.obstacleNum--;
        }
        this.gameObjects.remove(gameObject);
    }

    public void paint(Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(Color.YELLOW);
        if (this.endless) {
            graphics.drawString("击败坦克数量：" + this.beatTankNum, 10, 40);
        }else {
            graphics.drawString("敌对坦克数量：" + this.badTankNum, 10, 40);
        }
        graphics.drawString("障碍物数量：" + this.obstacleNum, 130, 40);
        graphics.setColor(color);
        boolean isVectory = false;
        if (this.endless) {
            if (this.beatTankNum == PropertyManager.getInstance().beatTankNum) {
                isVectory = true;
            }
        } else {
            if (this.badTankNum == 0) {
                isVectory = true;
            }
        }
        if (isVectory) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font(null, Font.BOLD, 70));
            graphics.drawString("恭喜安安,获得了最伟大的胜利", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100);
            graphics.setFont(new Font(null, Font.BOLD, 40));
            graphics.drawString("按数字键更换障碍物颜色", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100 + 70);
            graphics.drawString("按E切换无尽模式,无尽模式需击败坦克" + PropertyManager.getInstance().beatTankNum + "个", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100 + 70 + 40);
            graphics.drawString("按G切换开火模式", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100 + 70 + 40 + 40);
            graphics.drawString("按R重新开始", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100 + 70 + 40 + 40 + 40);
            graphics.drawString("按P暂停", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100 + 70 + 40 + 40 + 40 + 40);
            return;
        }
        if(!mainTank.getLive() && !otherTank.getLive()){
            graphics.setColor(Color.RED);
            graphics.setFont(new Font(null, Font.BOLD, 70));
            graphics.drawString("Game Over", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100);
            graphics.setFont(new Font(null, Font.BOLD, 40));
            graphics.drawString("按R重新开始", TankFrame.GAME_WIDTH / 2 - 480, TankFrame.GAME_HEIGHT / 2 - 100 + 70);
            return;
        }
        GameObject gameObject;
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObject = gameObjects.get(i);
            gameObject.paint(graphics);
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

    public int getBadTankNum() {
        return badTankNum;
    }

    public void setBadTankNum(int badTankNum) {
        this.badTankNum = badTankNum;
    }

    public int getObstacleNum() {
        return obstacleNum;
    }

    public void setObstacleNum(int obstacleNum) {
        this.obstacleNum = obstacleNum;
    }

    public void setEndless(boolean endless) {
        this.endless = endless;
    }

    public boolean getEndless() {
        return endless;
    }

    public int getBeatTankNum() {
        return beatTankNum;
    }

    public void setBeatTankNum(int beatTankNum) {
        this.beatTankNum = beatTankNum;
    }
}
