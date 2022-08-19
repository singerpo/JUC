package com.sing.tank;


import com.sing.tank.facade.GameModel;
import com.sing.tank.manager.PropertyManager;
import com.sing.tank.manager.ResourceManager;

import javax.swing.*;
import java.awt.*;


/**
 * 坦克主窗口
 * <p>
 * 19 把主战坦克也加入到List中（1）
 * 19 完成坦克大战网络版（1）
 *-Dsun.java2d.uiScale=1.0
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
        setBounds(20, 20, GAME_WIDTH+5, GAME_HEIGHT + 30);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        mainPanel = new TankPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT);
        mainPanel.setBackground(Color.GRAY);

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
        mainPanel.addKeyListener(new TankPanel.MyKeyListener());
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
