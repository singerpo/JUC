package com.sing.tank;

import com.sing.tank.facade.GameModel;

import javax.swing.*;
import java.awt.*;

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
            // Color color = offGraphics.getColor();
            // offGraphics.setColor(new Color(128, 64, 0));
            // offGraphics.fillRect(0, 0, TankFrame.GAME_WIDTH, TankFrame.GAME_HEIGHT);
            // offGraphics.setColor(color);
            paint(offGraphics);
            graphics.drawImage(offScreenImage, 0, 0, null);
    }

    public void paint(Graphics graphics) {
        super.paintComponent(graphics);
        GameModel.getInstance().paint(graphics);
    }
}
