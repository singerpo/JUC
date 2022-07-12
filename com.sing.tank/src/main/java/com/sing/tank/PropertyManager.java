package com.sing.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    static PropertyManager propertyManager = new PropertyManager();
    public Integer initTankCount;
    public int tankSpeed;
    public int bulletSpeed;
    public int gameWidth;
    public int gameHeight;
    public long paintDiff;

    private PropertyManager() {

    }

    public static PropertyManager getInstance() {
        if (propertyManager.initTankCount == null) {
            Properties properties = new Properties();
            try {
                properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("conf.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            propertyManager.initTankCount = Integer.parseInt(properties.get("initTankCount").toString());
            propertyManager.tankSpeed = Integer.parseInt(properties.get("tankSpeed").toString());
            propertyManager.bulletSpeed = Integer.parseInt(properties.get("bulletSpeed").toString());
            propertyManager.gameWidth = Integer.parseInt(properties.get("gameWidth").toString());
            propertyManager.gameHeight = Integer.parseInt(properties.get("gameHeight").toString());
            propertyManager.paintDiff = Long.parseLong(properties.get("paintDiff").toString());
        }
        return propertyManager;
    }

}
