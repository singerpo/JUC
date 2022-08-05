package com.sing.tank;


import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.manager.ResourceManager;
import com.sing.tank.strategy.DefaultFireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * 坦克主窗口
 * <p>
 * 13 Class.forName.newInstance1.mp4
 * 13 抽象出游戏物体的父类1
 * 13 合成GameModel中的各种不同物体1
 *
 * @author songbo
 * @since 2022-07-07
 */
public class TankFrame extends JFrame {
    public static final int GAME_WIDTH = PropertyManager.getInstance().gameWidth;
    public static final int GAME_HEIGHT = PropertyManager.getInstance().gameHeight;
    public static final long PAINT_DIFF = PropertyManager.getInstance().paintDiff;
    public TankPanel mainPanel;


    public TankFrame() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super("坦克大战");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        getContentPane().setLayout(null);
        setIconImage(ResourceManager.tankD);
        setBounds(20, 20, GAME_WIDTH, GAME_HEIGHT + 20);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        mainPanel = new TankPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);
        mainPanel.setBackground(new Color(128, 64, 0));

        JButton button = GameModel.getInstance().getButton();
        button.setVisible(false);
        button.setBounds(140, TankFrame.GAME_HEIGHT / 2 - 100 + 10, 100, 40);
        button.setFocusable(false);
        button.addActionListener(e -> {
            GameModel.getInstance().init();
            button.setVisible(false);
        });
        mainPanel.add(button);
        getContentPane().add(mainPanel);
        mainPanel.setFocusable(true);
        setVisible(true);
        mainPanel.addKeyListener(new MyKeyListener());
    }

    // 键盘监听
    static class MyKeyListener extends KeyAdapter {
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
                    // 黑色
                    GameModel.getInstance().setObstacleColor(Color.BLACK);
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
                    if (GameModel.getInstance().getFireStrategy() instanceof DefaultFireStrategy) {
                        GameModel.getInstance().setFireStrategy(new FourDirectionFireStrategy());
                    } else {
                        GameModel.getInstance().setFireStrategy(new DefaultFireStrategy());
                    }
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

    // Image offScreenImage = null;

    // 用双缓冲解决闪烁的问题
    // @Override
    // public void update(Graphics graphics) {
    //     if (offScreenImage == null) {
    //         offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
    //     }
    //     Graphics offGraphics = offScreenImage.getGraphics();
    //     Color color = offGraphics.getColor();
    //     offGraphics.setColor(Color.GRAY);
    //     offGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
    //     offGraphics.setColor(color);
    //     paint(offGraphics);
    //     graphics.drawImage(offScreenImage, 0, 0, null);
    // }
    //
    // @Override
    // public void paint(Graphics graphics) {
    //     GameModel.getInstance().paint(graphics);
    // }


    //
    // public void myPaint(Graphics graphics) {
    //     GameModel.getInstance().paint(graphics);
    // }

}
