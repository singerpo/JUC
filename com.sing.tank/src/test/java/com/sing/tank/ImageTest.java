package com.sing.tank;

import cn.hutool.core.lang.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author songbo
 * @since 2022-07-09
 */
public class ImageTest {
    @Test
    public void test() throws IOException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/timg.jpg");
        BufferedImage image = ImageIO.read(inputStream);
        Assert.notNull(image);
    }
}
