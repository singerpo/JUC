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
    /*** 障碍物尺寸**/
    private final int obstacleSize = 62;
    /*** 主障碍物**/
    private final Obstacle mainObstacle = new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, TankFrame.GAME_HEIGHT - this.obstacleSize);
    /*** 敌对坦克数量**/
    private int badTankNum;
    /*** 击败坦克数量**/
    private int beatTankNum;
    /*** 记录运行时间**/
    private long paintDiffTime;
    /*** 敌对坦克刷新次数**/
    private int badRefreshTimes;
    // 工厂方法
    private final AbstractGameFactory gameFactory = new DefaultFactory();
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
        this.paintDiffTime = 0;
        this.badRefreshTimes = 0;
        initObstacle();
        mainTank = this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 + this.obstacleSize / 2 + this.obstacleSize + 1, 900, DirectionEnum.UP, GroupEnum.GOOD, true);
        otherTank = this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 - this.obstacleSize - this.obstacleSize - 1, 900, DirectionEnum.UP, GroupEnum.GOOD, true);
        add(mainTank);
        add(otherTank);
        initEndLessBadTank();
    }

    public void add(GameObject gameObject) {
        if (gameObject instanceof BaseTank && gameObject.getLive()) {
            if (((BaseTank) gameObject).getGroupEnum() == GroupEnum.BAD) {
                this.badTankNum++;
            }
        }
        this.gameObjects.add(gameObject);
    }

    public void remove(GameObject gameObject) {
        if (gameObject instanceof BaseTank) {
            if (((BaseTank) gameObject).getGroupEnum() == GroupEnum.BAD) {
                this.badTankNum--;
            }
        }
        this.gameObjects.remove(gameObject);
    }

    public void paint(Graphics graphics) {
        // 固定间隔时间刷新敌对坦克
        if (paintDiffTime > 0 && paintDiffTime % PropertyManager.getInstance().badRefreshDiff == 0 && paintDiffTime <= (PropertyManager.getInstance().badRefreshTimes - 1) * PropertyManager.getInstance().badRefreshDiff + 20) {
            initEndLessBadTank();
        }
        this.paintDiffTime += PropertyManager.getInstance().paintDiff;
        Color color = graphics.getColor();
        graphics.setColor(Color.YELLOW);

        graphics.drawString("敌对坦克数量：" + this.badTankNum, 10, 50);
        graphics.drawString("击败坦克数量：" + this.beatTankNum, 130, 50);
        graphics.setColor(color);
        boolean isVectory = false;
        if (this.badTankNum == 0 && this.badRefreshTimes == PropertyManager.getInstance().badRefreshTimes) {
            isVectory = true;
        }
        if (isVectory) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font(null, Font.BOLD, 40));
            graphics.drawString("恭喜安安,获得了最伟大的胜利", 100, TankFrame.GAME_HEIGHT / 2 - 100);
            graphics.setFont(new Font(null, Font.BOLD, 20));
            graphics.drawString("按数字键更换障碍物颜色", 100, TankFrame.GAME_HEIGHT / 2 - 100 + 41);
            graphics.drawString("按G切换开火模式", 100, TankFrame.GAME_HEIGHT / 2 - 100 + 41 + 21);
            graphics.drawString("按R重新开始", 100, TankFrame.GAME_HEIGHT / 2 - 100 + 41 + 21 * 2);
            graphics.drawString("按P暂停", 100, TankFrame.GAME_HEIGHT / 2 - 100 + 41 + 21 * 3);
            return;
        }
        if ((!mainTank.getLive() && !otherTank.getLive()) || !mainObstacle.getLive()) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font(null, Font.BOLD, 40));
            graphics.drawString("Game Over", 100, TankFrame.GAME_HEIGHT / 2 - 100);
            graphics.setFont(new Font(null, Font.BOLD, 20));
            graphics.drawString("按R重新开始", 100, TankFrame.GAME_HEIGHT / 2 - 100 + 41);
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

    /**
     * 初始化敌对坦克
     */
    private void initEndLessBadTank() {
        this.badRefreshTimes++;
        add(this.gameFactory.createTank(1, 31, DirectionEnum.DOWN, GroupEnum.BAD, false));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, 31, DirectionEnum.DOWN, GroupEnum.BAD, false));
        add(this.gameFactory.createTank(TankFrame.GAME_WIDTH - 60, 31, DirectionEnum.DOWN, GroupEnum.BAD, false));
    }

    /**
     * 初始化障碍物
     */
    private void initObstacle() {
        //主坦克和福坦克之间的第一行3个障碍物
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 - this.obstacleSize - 1, TankFrame.GAME_HEIGHT - this.obstacleSize - this.obstacleSize - 1));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, TankFrame.GAME_HEIGHT - this.obstacleSize - this.obstacleSize - 1));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 + this.obstacleSize + 1, TankFrame.GAME_HEIGHT - this.obstacleSize - this.obstacleSize - 1));
        //主坦克和福坦克之间的第二行3个障碍物
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 - this.obstacleSize - 1, TankFrame.GAME_HEIGHT - this.obstacleSize));
        mainObstacle.setLive(true);
        add(mainObstacle);
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 + this.obstacleSize + 1, TankFrame.GAME_HEIGHT - this.obstacleSize));

        //3个不消失的障碍物
        add(new Obstacle(0, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2, true));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2, true));
        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2, true));

        // 上半部分4行障碍物
        add(new Obstacle(this.obstacleSize + 2, 30 + this.obstacleSize + 1));
        add(new Obstacle(this.obstacleSize + 2, 30 + (this.obstacleSize + 1) * 2));
        add(new Obstacle(this.obstacleSize + 2, 30 + (this.obstacleSize + 1) * 3));
        add(new Obstacle(this.obstacleSize + 2, 30 + (this.obstacleSize + 1) * 4));

        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 - this.obstacleSize, 30 + this.obstacleSize + 1));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 - this.obstacleSize, 30 + (this.obstacleSize + 1) * 2));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 - this.obstacleSize, 30 + (this.obstacleSize + 1) * 3));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 - this.obstacleSize, 30 + (this.obstacleSize + 1) * 4));

        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 + this.obstacleSize + 1, 30 + this.obstacleSize + 1));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 + this.obstacleSize + 1, 30 + (this.obstacleSize + 1) * 2));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 + this.obstacleSize + 1, 30 + (this.obstacleSize + 1) * 3));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2 + this.obstacleSize + 1, 30 + (this.obstacleSize + 1) * 4));

        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, 30 + this.obstacleSize + 1));
        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, 30 + (this.obstacleSize + 1) * 2));
        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, 30 + (this.obstacleSize + 1) * 3));
        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, 30 + (this.obstacleSize + 1) * 4));

        //下班部分3行障碍物
        add(new Obstacle(this.obstacleSize + 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + this.obstacleSize + 1 + this.obstacleSize + 7));
        add(new Obstacle(this.obstacleSize + 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 2 + this.obstacleSize + 7));
        add(new Obstacle(this.obstacleSize + 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 3 + this.obstacleSize + 7));
        add(new Obstacle(this.obstacleSize + 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 4 + this.obstacleSize + 7));

        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + this.obstacleSize + 1 + this.obstacleSize + 7));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 2 + this.obstacleSize + 7));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 3 + this.obstacleSize + 7));
        add(new Obstacle(TankFrame.GAME_WIDTH / 2 - this.obstacleSize / 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 4 + this.obstacleSize + 7));

        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + this.obstacleSize + 1 + this.obstacleSize + 7));
        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 2 + this.obstacleSize + 7));
        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 3 + this.obstacleSize + 7));
        add(new Obstacle(TankFrame.GAME_WIDTH - this.obstacleSize * 2 - 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2 + (this.obstacleSize + 1) * 4 + this.obstacleSize + 7));
        //不消失障碍物之间的障碍物
        add(new Obstacle(this.obstacleSize + 1, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 2, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 3, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 4, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 5, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 7, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 8, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 9, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 10, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
        add(new Obstacle((this.obstacleSize + 1) * 11, TankFrame.GAME_HEIGHT / 2 - this.obstacleSize / 2));
    }


    public BaseTank getMainTank() {
        return mainTank;
    }

    public BaseTank getOtherTank() {
        return otherTank;
    }

    public AbstractGameFactory getGameFactory() {
        return gameFactory;
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

    public int getBeatTankNum() {
        return beatTankNum;
    }

    public void setBeatTankNum(int beatTankNum) {
        this.beatTankNum = beatTankNum;
    }

    public Obstacle getMainObstacle() {
        return mainObstacle;
    }

    public int getObstacleSize() {
        return obstacleSize;
    }

}
