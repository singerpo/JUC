package com.sing.tank;

import com.sing.tank.facade.GameModel;
import com.sing.tank.net.Client;

import javax.swing.*;
import java.awt.*;

/**
 * @author songbo
 * @since 2022-07-07
 */
public class Main {

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        TankFrame tankFrame = new TankFrame();
        // new Thread(() -> new Audio("audio/war1.wav").loop()).start();
        // MyDialog myDialog = new MyDialog();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(TankFrame.PAINT_DIFF);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!GameModel.getInstance().getPause()) {
                    tankFrame.mainPanel.repaint();
                }
            }
        }).start();
        Client client = Client.INSTANCE;
        client.connect();
    }
}

//弹窗的窗口
class MyDialog extends JDialog {
    JTextField textField = new JTextField();

    public MyDialog() {
        this.setTitle("设置服务器地址");
        this.setVisible(true);
        this.setBounds(100, 100, 200, 200);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);​
        Container container = this.getContentPane();
        container.setLayout(null);
        textField.setBounds(10, 10, 100, 20);
        textField.setText("127.0.0.1");
        container.add(textField);
    }
}

