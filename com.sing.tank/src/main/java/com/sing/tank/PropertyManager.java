package com.sing.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static PropertyManager propertyManager;
    public Integer initTankCount;
    public int tankSpeed;
    public int bulletSpeed;
    public int gameWidth;
    public int gameHeight;
    public long paintDiff;

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
