package com.sing.tank;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 资源管理类
 * 主要加载文件等
 *
 * @author songbo
 * @since 2022-07-09
 */
public class ResourceManager {
    public static BufferedImage goodTankU, goodTankD, goodTankL, goodTankR, tankU, tankD, tankL, tankR;
    public static BufferedImage goodBulletU, goodBulletD, goodBulletL, goodBulletR, bulletU, bulletD, bulletL, bulletR;
    public static BufferedImage[] explodes = new BufferedImage[16];
    // public static BufferedImage obstacle;

    static {
        try {
            goodTankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodTankD = ImageUtil.rotate(goodTankU, 180);
            goodTankL = ImageUtil.rotate(goodTankU, -90);
            goodTankR = ImageUtil.rotate(goodTankU, 90);
            tankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            tankD =  ImageUtil.rotate(tankU, 180);
            tankL =  ImageUtil.rotate(tankU, -90);
            tankR =  ImageUtil.rotate(tankU, 90);

            goodBulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/GoodBulletU.png"));
            goodBulletD = ImageUtil.rotate(goodBulletU, 180);
            goodBulletL = ImageUtil.rotate(goodBulletU, -90);
            goodBulletR = ImageUtil.rotate(goodBulletU, 90);
            bulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletD = ImageUtil.rotate(bulletU, 180);
            bulletL = ImageUtil.rotate(bulletU, -90);
            bulletR = ImageUtil.rotate(bulletU, 90);
            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
            }
            // obstacle = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/obstacle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
