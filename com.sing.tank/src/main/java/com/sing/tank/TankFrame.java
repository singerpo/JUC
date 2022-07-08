package com.sing.tank;

import java.awt.*;
import java.awt.event.*;

/**
 * 实现MyKeyListener，对键盘作出响应（1）.mp4 00:12
 * @author songbo
 * @since 2022-07-07
 */
public class TankFrame extends Frame {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    Tank mainTank = new Tank(50, 200, DirectionEnum.DOWN, Color.RED);
    Bullet mainBullet = new Bullet(70,220,DirectionEnum.DOWN);
    Tank otherTank = new Tank(800 - 100, 200, DirectionEnum.DOWN, Color.GRAY);
    Bullet otherBullet = new Bullet(800 - 100+20,220,DirectionEnum.DOWN);

    public TankFrame() throws HeadlessException {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("坦克大战");
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
            }
            setMainTankDirection();
        }

        private void setMainTankDirection() {
            if (!up && !down && !left && !right) {
                TankFrame.this.mainTank.setMoving(false);
                TankFrame.this.otherTank.setMoving(false);
            }else{
                TankFrame.this.mainTank.setMoving(true);
                TankFrame.this.otherTank.setMoving(true);
                if (up) {
                    TankFrame.this.mainTank.setDirectionEnum(DirectionEnum.UP);
                    TankFrame.this.otherTank.setDirectionEnum(DirectionEnum.UP);
                }
                if (down) {
                    TankFrame.this.mainTank.setDirectionEnum(DirectionEnum.DOWN);
                    TankFrame.this.otherTank.setDirectionEnum(DirectionEnum.DOWN);
                }
                if (left) {
                    TankFrame.this.mainTank.setDirectionEnum(DirectionEnum.LEFT);
                    TankFrame.this.otherTank.setDirectionEnum(DirectionEnum.LEFT);
                }
                if (right) {
                    TankFrame.this.mainTank.setDirectionEnum(DirectionEnum.RIGHT);
                    TankFrame.this.otherTank.setDirectionEnum(DirectionEnum.RIGHT);
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
    @Override
    public void update(Graphics graphics){
        if(offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics offGraphics = offScreenImage.getGraphics();
        Color color = offGraphics.getColor();
        offGraphics.setColor(Color.YELLOW);
        offGraphics.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        offGraphics.setColor(color);
        paint(offGraphics);
        graphics.drawImage(offScreenImage,0,0,null);
    }

    @Override
    public void paint(Graphics graphics) {
        this.mainTank.paint(graphics);
        this.mainBullet.paint(graphics);
        this.otherTank.paint(graphics);
        this.otherBullet.paint(graphics);
    }
}
