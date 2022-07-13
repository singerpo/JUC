package com.sing.tank;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 坦克主窗口
 * <p>
 * 6
 *
 * @author songbo
 * @since 2022-07-07
 */
public class TankFrame extends Frame {
    public static final int GAME_WIDTH = PropertyManager.getInstance().gameWidth;
    public static final int GAME_HEIGHT = PropertyManager.getInstance().gameHeight;
    public static final long PAINT_DIFF = PropertyManager.getInstance().paintDiff;

    private List<Tank> tanks;
    private List<Bullet> bullets;
    private List<Explode> explodes;
    private Tank mainTank;
    private Tank otherTank;

    public void init() {
        tanks = new ArrayList<>();
        bullets = new ArrayList<>();
        explodes = new ArrayList<>();
        mainTank = new Tank(50, 60, DirectionEnum.DOWN, GroupEnum.GOOD, this);
        otherTank = new Tank(GAME_WIDTH - 50 * 4, 60, DirectionEnum.DOWN, GroupEnum.GOOD, this);
        tanks.add(mainTank);
        tanks.add(otherTank);
        //60为间距
        int max = GAME_HEIGHT / (60 + mainTank.getHeight()) - 1;
        for (int i = 1; i <= max; i++) {
            tanks.add(new Tank(GAME_WIDTH - mainTank.getWidth() * 2, (60 + mainTank.getHeight()) * i + 60, DirectionEnum.DOWN, GroupEnum.BAD, this));
        }
        Random random = new Random();
        for (int i = 0; i < PropertyManager.getInstance().initTankCount - 2 - max; i++) {
            tanks.add(new Tank(random.nextInt(TankFrame.GAME_WIDTH - 100), random.nextInt(TankFrame.GAME_HEIGHT - 100), DirectionEnum.DOWN, GroupEnum.BAD, this));
        }
    }


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
        init();
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
                    TankFrame.this.mainTank.fire();
                    break;
                case KeyEvent.VK_L:
                    TankFrame.this.otherTank.fire();
                    break;
                case KeyEvent.VK_R:
                    TankFrame.this.init();
                    break;
            }
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
            setMainTankDirection();
        }

        private void setMainTankDirection() {
            if (!up && !down && !left && !right) {
                TankFrame.this.mainTank.setMoving(false);
                TankFrame.this.otherTank.setMoving(false);
            } else {
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
        Color color = graphics.getColor();
        graphics.setColor(Color.YELLOW);
        graphics.drawString("子弹的数量：" + bullets.size(), 10, 60);
        graphics.drawString("坦克的数量：" + tanks.size(), 100, 60);
        graphics.setColor(color);

        for (int i = 0; i < this.tanks.size(); i++) {
            this.tanks.get(i).paint(graphics);
        }
        for (int i = 0; i < this.bullets.size(); i++) {
            this.bullets.get(i).paint(graphics);
        }
        for (int i = 0; i < this.explodes.size(); i++) {
            this.explodes.get(i).paint(graphics);
        }
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public void setTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public List<Explode> getExplodes() {
        return explodes;
    }

    public void setExplodes(List<Explode> explodes) {
        this.explodes = explodes;
    }

    public Tank getMainTank() {
        return mainTank;
    }

    public void setMainTank(Tank mainTank) {
        this.mainTank = mainTank;
    }

    public Tank getOtherTank() {
        return otherTank;
    }

    public void setOtherTank(Tank otherTank) {
        this.otherTank = otherTank;
    }

    public Image getOffScreenImage() {
        return offScreenImage;
    }

    public void setOffScreenImage(Image offScreenImage) {
        this.offScreenImage = offScreenImage;
    }
}
