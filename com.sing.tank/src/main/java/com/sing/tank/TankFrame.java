package com.sing.tank;


import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.manager.ResourceManager;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 坦克主窗口
 * <p>
 * 8 分离玩家和机器人，对边界进行不同的处理（1）
 *
 * @author songbo
 * @since 2022-07-07
 */
public class TankFrame extends Frame {
    public static final int GAME_WIDTH = PropertyManager.getInstance().gameWidth;
    public static final int GAME_HEIGHT = PropertyManager.getInstance().gameHeight;
    public static final long PAINT_DIFF = PropertyManager.getInstance().paintDiff;
    GameModel gameModel = new GameModel();

    public TankFrame() throws HeadlessException {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("坦克大战");
        setIconImage(ResourceManager.tankD);
        setVisible(true);
        gameModel.init();
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
                    TankFrame.this.gameModel.getMainTank().fire();
                    break;
                case KeyEvent.VK_L:
                    TankFrame.this.gameModel.getOtherTank().fire();
                    break;
                case KeyEvent.VK_R:
                    TankFrame.this.gameModel.init();
                    break;
            }
           // new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
            setMainTankDirection();
        }

        private void setMainTankDirection() {
            if (!up && !down && !left && !right) {
                TankFrame.this.gameModel.getMainTank().setMoving(false);
                TankFrame.this.gameModel.getOtherTank().setMoving(false);
            } else {
                TankFrame.this.gameModel.getMainTank().setMoving(true);
                TankFrame.this.gameModel.getOtherTank().setMoving(true);
                if (up) {
                    TankFrame.this.gameModel.getMainTank().setDirectionEnum(DirectionEnum.UP);
                    TankFrame.this.gameModel.getOtherTank().setDirectionEnum(DirectionEnum.UP);
                }
                if (down) {
                    TankFrame.this.gameModel.getMainTank().setDirectionEnum(DirectionEnum.DOWN);
                    TankFrame.this.gameModel.getOtherTank().setDirectionEnum(DirectionEnum.DOWN);
                }
                if (left) {
                    TankFrame.this.gameModel.getMainTank().setDirectionEnum(DirectionEnum.LEFT);
                    TankFrame.this.gameModel.getOtherTank().setDirectionEnum(DirectionEnum.LEFT);
                }
                if (right) {
                    TankFrame.this.gameModel.getMainTank().setDirectionEnum(DirectionEnum.RIGHT);
                    TankFrame.this.gameModel.getOtherTank().setDirectionEnum(DirectionEnum.RIGHT);
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
        gameModel.paint(graphics);
    }
}
