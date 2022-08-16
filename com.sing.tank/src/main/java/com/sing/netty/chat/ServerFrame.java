package com.sing.netty.chat;


import com.sing.netty.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends JFrame {
    Server server = new Server();
    JButton buttonStart = new JButton("启动");
    JTextArea textAreaLeft = new JTextArea();
    JTextArea textAreaRight = new JTextArea();

    public ServerFrame() throws HeadlessException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super("服务端");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        getContentPane().setLayout(null);
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 600, 370);
        mainPanel.setBackground(Color.RED);
//        buttonStart.setBounds(260, 10, 100, 50);
//        buttonStart.setFocusable(false);
//        buttonStart.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                server.startServer(ServerFrame.this);
//            }
//        });
        mainPanel.add(buttonStart);
        textAreaLeft.setBounds(10, 60, 590 / 2, 300);
        textAreaRight.setBounds(10 + 590 / 2 + 5, 60, 590 / 2 - 5, 300);
        mainPanel.add(textAreaLeft);
        mainPanel.add(textAreaRight);
        getContentPane().add(mainPanel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);
    }

    public void updateServerMsg(String msg) {
        this.textAreaLeft.setText(textAreaLeft.getText() + System.getProperty("line.separator") + msg);
    }

    public void updateClientMsg(String msg) {
        this.textAreaRight.setText(textAreaRight.getText() + System.getProperty("line.separator") + msg);
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ServerFrame serverFrame = new ServerFrame();
        serverFrame.server.startServer(serverFrame);

    }
}
