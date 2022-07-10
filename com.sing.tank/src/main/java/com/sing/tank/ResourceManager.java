package com.sing.tank;


import cn.hutool.core.img.Img;
import cn.hutool.core.img.ImgUtil;

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

    static {
        try {
            goodTankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
            goodTankD = (BufferedImage) ImgUtil.rotate(goodTankU, 180);
            goodTankL = (BufferedImage) ImgUtil.rotate(goodTankU, -90);
            goodTankR = (BufferedImage) ImgUtil.rotate(goodTankU, 90);
            tankU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
            tankD = (BufferedImage) ImgUtil.rotate(tankU, 180);
            tankL = (BufferedImage) ImgUtil.rotate(tankU, -90);
            tankR = (BufferedImage) ImgUtil.rotate(tankU, 90);

            goodBulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            goodBulletD = (BufferedImage) ImgUtil.rotate(goodBulletU, 180);
            goodBulletL = (BufferedImage) ImgUtil.rotate(goodBulletU, -90);
            goodBulletR = (BufferedImage) ImgUtil.rotate(goodBulletU, 90);
            bulletU = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletU.gif"));
            bulletD = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            bulletL = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletL.gif"));
            bulletR = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/bulletR.gif"));
            for (int i = 0; i < 16; i++) {
                explodes[i] = ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
