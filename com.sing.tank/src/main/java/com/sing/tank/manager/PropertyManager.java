package com.sing.tank.manager;

import java.io.IOException;
import java.util.Properties;

/**
 * 同步双检查的单例模式
 * 加载property配置文件
 */
public class PropertyManager {
    private static PropertyManager propertyManager;
    public Integer initTankCount;
    public int tankSpeed;
    public int bulletSpeed;
    public int gameWidth;
    public int gameHeight;
    public long paintDiff;
    public int badTankLife;
    public int goodTankLife;
    public int badRefreshTimes;
    public int badRefreshDiff;

    private PropertyManager() {

    }

    {
        Properties properties = new Properties();
        try {
            properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.initTankCount = Integer.parseInt(properties.get("initTankCount").toString());
        this.tankSpeed = Integer.parseInt(properties.get("tankSpeed").toString());
        this.bulletSpeed = Integer.parseInt(properties.get("bulletSpeed").toString());
        this.gameWidth = Integer.parseInt(properties.get("gameWidth").toString());
        this.gameHeight = Integer.parseInt(properties.get("gameHeight").toString());
        this.paintDiff = Long.parseLong(properties.get("paintDiff").toString());
        this.badTankLife = Integer.parseInt(properties.get("badTankLife").toString());
        this.goodTankLife = Integer.parseInt(properties.get("goodTankLife").toString());
        this.badRefreshTimes = Integer.parseInt(properties.get("badRefreshTimes").toString());
        this.badRefreshDiff = Integer.parseInt(properties.get("badRefreshDiff").toString());
    }

    public static PropertyManager getInstance() {
        if (propertyManager == null) {
            synchronized (PropertyManager.class) {
                if (propertyManager == null) {
                    propertyManager = new PropertyManager();
                }
            }
        }
        return propertyManager;
    }

}
