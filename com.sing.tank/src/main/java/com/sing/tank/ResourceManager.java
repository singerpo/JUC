package com.sing.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 资源管理类
 * 主要加载文件等
 * @author songbo
 * @since 2022-07-09
 */
public class ResourceManager {
    public static BufferedImage tankU,tankD,tankL,tankR;
    public static BufferedImage bulletU,bulletD,bulletL,bulletR;

    static {
        try {
            tankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankU.gif"));
            tankD = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankD.gif"));
            tankL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            tankR = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/tankR.gif"));

            bulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletD = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            bulletL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
            bulletR = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
