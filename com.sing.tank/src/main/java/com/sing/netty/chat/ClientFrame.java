package com.sing.netty.chat;

import com.sing.netty.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author songbo
 * @since 2022-08-16
 */
public class ClientFrame extends JFrame {
    JTextArea textArea = new JTextArea();
    JTextField textField = new JTextField();
    private Client client = new Client();

    public ClientFrame() throws HeadlessException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super("客户端聊天室");
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        getContentPane().setLayout(null);
        this.setSize(600, 400);
        this.setLocation(100, 20);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 600, 370);
        mainPanel.setBackground(Color.GREEN);
        textArea.setBounds(10, 10, 575, 300);
        textField.setBounds(10, 310, 575, 50);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 把字符串发送到服务器
                getClient().sendMsg(textField.getText());
                textField.setText("");
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                getClient().closeClient();
                System.exit(0);
            }
        });
        mainPanel.add(textArea);
        mainPanel.add(textField);
        getContentPane().add(mainPanel);
        this.setVisible(true);
        client.connect(this);
    }

    /**
     * 服务器返回信息到客户端
     *
     * @param msg 信息
     */
    public void updateText(String msg) {
        this.textArea.setText(textArea.getText() + System.getProperty("line.separator") + msg);
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        ClientFrame clientFrame = new ClientFrame();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
