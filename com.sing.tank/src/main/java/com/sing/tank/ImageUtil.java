package com.sing.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    /**
     * 旋转图片
     *
     * @param bufferedImage
     * @param degree
     * @return
     */
    public static BufferedImage rotate(BufferedImage bufferedImage, int degree) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int type = bufferedImage.getColorModel().getTransparency();
        BufferedImage img = new BufferedImage(w, h, type);
        Graphics2D graphics2D = img.createGraphics();
        graphics2D.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2D.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();
        return img;
    }
}
