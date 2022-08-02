package com.sing.tank.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    /**
     * 旋转图片
     *
     * @param bufferedImage 源图片
     * @param degree 旋转度数
     * @return 返回结果
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
        graphics2D.rotate(Math.toRadians(degree), w / 2.0, h / 2.0);
        graphics2D.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();
        return img;
    }
}
