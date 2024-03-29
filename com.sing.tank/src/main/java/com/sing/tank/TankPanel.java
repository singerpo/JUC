package com.sing.tank;

import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.facade.GameModel;
import com.sing.tank.net.Client;
import com.sing.tank.net.TankDirectionChangedMsg;
import com.sing.tank.net.TankStartMovingMsg;
import com.sing.tank.net.TankStopMsg;
import com.sing.tank.strategy.DefaultFireStrategy;
import com.sing.tank.strategy.FourDirectionFireStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author songbo
 * @since 2022-08-05
 */
public class TankPanel extends JPanel {
    Image offScreenImage;

    @Override
    public void paintComponent(Graphics graphics) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(TankFrame.GAME_WIDTH, TankFrame.GAME_HEIGHT);
        }
        Graphics offGraphics = offScreenImage.getGraphics();
//             Color color = offGraphics.getColor();
//             offGraphics.setColor(Color.GRAY);
//             offGraphics.fillRect(0, 0, TankFrame.GAME_WIDTH, TankFrame.GAME_HEIGHT);
//             offGraphics.setColor(color);
        paint(offGraphics);
        graphics.drawImage(offScreenImage, 0, 0, null);
    }

    public void paint(Graphics graphics) {
        super.paintComponent(graphics);
        GameModel.getInstance().paint(graphics);
        super.paintChildren(graphics);
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
                case KeyEvent.VK_U:
                    GameModel.getInstance().save();
                    break;
                case KeyEvent.VK_I:
                    GameModel.getInstance().load();
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
                // 向服务器发送坦克停止消息
                Client.INSTANCE.send(new TankStopMsg(GameModel.getInstance().getMainTank()));
            } else {
                DirectionEnum directionEnum = GameModel.getInstance().getMainTank().getDirectionEnum();
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
                if (!directionEnum.equals(GameModel.getInstance().getMainTank().getDirectionEnum())) {
                    // 向服务器发送坦克方向变动消息
                    Client.INSTANCE.send(new TankDirectionChangedMsg(GameModel.getInstance().getMainTank()));
                }
                if (!GameModel.getInstance().getMainTank().getMoving()) {
                    // 向服务器发送坦克移动消息
                    Client.INSTANCE.send(new TankStartMovingMsg(GameModel.getInstance().getMainTank()));
                }
                GameModel.getInstance().getMainTank().setMoving(true);
            }
            // if (!upOther && !downOther && !leftOther && !rightOther) {
            //     GameModel.getInstance().getOtherTank().setMoving(false);
            // } else {
            //     GameModel.getInstance().getOtherTank().setMoving(true);
            //     if (upOther) {
            //         GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.UP);
            //     }
            //     if (downOther) {
            //         GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.DOWN);
            //     }
            //     if (leftOther) {
            //         GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.LEFT);
            //     }
            //     if (rightOther) {
            //         GameModel.getInstance().getOtherTank().setDirectionEnum(DirectionEnum.RIGHT);
            //     }
            // }
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
}
