package com.sing.tank;


import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.manager.ResourceManager;
import com.sing.tank.strategy.DefaultFireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 坦克主窗口
 * <p>
 * 11 测试Properties类的使用（1）
 *
 * @author songbo
 * @since 2022-07-07
 */
public class TankFrame extends Frame {
    public static final int GAME_WIDTH = PropertyManager.getInstance().gameWidth;
    public static final int GAME_HEIGHT = PropertyManager.getInstance().gameHeight;
    public static final long PAINT_DIFF = PropertyManager.getInstance().paintDiff;

    public TankFrame() throws HeadlessException {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("坦克大战");
        setIconImage(ResourceManager.tankD);
        setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.addKeyListener(new MyKeyListener());
    }

    // 键盘监听
    class MyKeyListener extends KeyAdapter {
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
        boolean upOther = false;
        boolean downOther = false;
        boolean leftOther = false;
        boolean rightOther = false;

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            // System.out.println(Integer.toHexString(key));
            switch (key) {
                case KeyEvent.VK_UP:
                    up = true;
                    break;
                case KeyEvent.VK_DOWN:
                    down = true;
                    break;
                case KeyEvent.VK_LEFT:
                    left = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    break;
                case KeyEvent.VK_CONTROL:
                    GameModel.getInstance().getMainTank().handleFireKey();
                    break;
                case KeyEvent.VK_W:
                    upOther = true;
                    break;
                case KeyEvent.VK_S:
                    downOther = true;
                    break;
                case KeyEvent.VK_A:
                    leftOther = true;
                    break;
                case KeyEvent.VK_D:
                    rightOther = true;
                    break;
                case KeyEvent.VK_F:
                    GameModel.getInstance().getOtherTank().handleFireKey();
                    break;
                case KeyEvent.VK_R:
                    GameModel.getInstance().init();
                    break;
                case KeyEvent.VK_P:
                    GameModel.getInstance().setPause(!GameModel.getInstance().getPause());
                    break;
                case KeyEvent.VK_0:
                    GameModel.getInstance().setObstacleColor(Color.BLUE);
                    break;
                case KeyEvent.VK_1:
                    GameModel.getInstance().setObstacleColor(Color.YELLOW);
                    break;
                case KeyEvent.VK_2:
                    // 紫色
                    GameModel.getInstance().setObstacleColor(new Color(128, 0, 128));
                    break;
                case KeyEvent.VK_3:
                    // 棕色
                    GameModel.getInstance().setObstacleColor(new Color(128, 64, 0));
                    break;
                case KeyEvent.VK_4:
                    // 粉红色
                    GameModel.getInstance().setObstacleColor(Color.PINK);
                    break;
                case KeyEvent.VK_5:
                    // 褐色
                    GameModel.getInstance().setObstacleColor(new Color(150, 75, 0));
                    break;
                case KeyEvent.VK_6:
                    // 绿色
                    GameModel.getInstance().setObstacleColor(Color.GREEN);
                    break;
                case KeyEvent.VK_7:
                    // 橙色
                    GameModel.getInstance().setObstacleColor(Color.ORANGE);
                    break;
                case KeyEvent.VK_8:
                    // 青色
                    GameModel.getInstance().setObstacleColor(Color.CYAN);
                    break;
                case KeyEvent.VK_9:
                    // 品红色
                    GameModel.getInstance().setObstacleColor(Color.MAGENTA);
                    break;
                case KeyEvent.VK_G:
                    // 切换开火策略
                    if(GameModel.getInstance().getFireStrategy() instanceof DefaultFireStrategy){
                        GameModel.getInstance().setFireStrategy(new FourDirectionFireStrategy());
                    }else{
                        GameModel.getInstance().setFireStrategy(new DefaultFireStrategy());
                    }
                    break;
                case KeyEvent.VK_E:
                    // 切换无尽模式
                    GameModel.getInstance().setEndless(!GameModel.getInstance().getEndless());
                    break;

            }
            // new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
            setMainTankDirection();
        }

        private void setMainTankDirection() {
            if (!up && !down && !left && !right) {
                GameModel.getInstance().getMainTank().setMoving(false);
            } else {
                GameModel.getInstance().getMainTank().setMoving(true);
                if (up) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.UP);
                }
                if (down) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.DOWN);
                }
                if (left) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.LEFT);
                }
                if (right) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.RIGHT);
                }
            }
            if (!upOther && !downOther && !leftOther && !rightOther) {
                GameModel.getInstance().getOtherTank().setMoving(false);
            } else {
                GameModel.getInstance().getOtherTank().setMoving(true);
                if (upOther) {
                    GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.UP);
                }
                if (downOther) {
                    GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.DOWN);
                }
                if (leftOther) {
                    GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.LEFT);
                }
                if (rightOther) {
                    GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.RIGHT);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
                case KeyEvent.VK_W:
                    upOther = false;
                    break;
                case KeyEvent.VK_S:
                    downOther = false;
                    break;
                case KeyEvent.VK_A:
                    leftOther = false;
                    break;
                case KeyEvent.VK_D:
                    rightOther = false;
                    break;
            }
            setMainTankDirection();
        }
    }

    Image offScreenImage = null;

    //用双缓冲解决闪烁的问题
    @Override
    public void update(Graphics graphics) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics offGraphics = offScreenImage.getGraphics();
        Color color = offGraphics.getColor();
        offGraphics.setColor(Color.GRAY);
        offGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        offGraphics.setColor(color);
        paint(offGraphics);
        graphics.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics graphics) {
        GameModel.getInstance().paint(graphics);
    }
}
