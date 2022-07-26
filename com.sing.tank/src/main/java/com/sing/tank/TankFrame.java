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
 * 8 做碰撞检测（1）.mp4
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
                    GameModel.getInstance().getMainTank().fire();
                    break;
                case KeyEvent.VK_L:
                    GameModel.getInstance().getOtherTank().fire();
                    break;
                case KeyEvent.VK_R:
                    GameModel.getInstance().init();
                    break;
            }
           // new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
            setMainTankDirection();
        }

        private void setMainTankDirection() {
            if (!up && !down && !left && !right) {
                GameModel.getInstance().getMainTank().setMoving(false);
                GameModel.getInstance().getOtherTank().setMoving(false);
            } else {
                GameModel.getInstance().getMainTank().setMoving(true);
                GameModel.getInstance().getOtherTank().setMoving(true);
                if (up) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.UP);
                    GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.UP);
                }
                if (down) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.DOWN);
                    GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.DOWN);
                }
                if (left) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.LEFT);
                    GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.LEFT);
                }
                if (right) {
                    GameModel.getInstance().getMainTank().setDirectionEnum(DirectionEnum.RIGHT);
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
