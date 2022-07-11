package com.sing.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    static PropertyManager propertyManager = new PropertyManager();
    public int initTankCount;

    private PropertyManager() {

    }

    public static PropertyManager getInstance() {
        if (propertyManager.initTankCount == 0) {
            Properties properties = new Properties();
            try {
                properties.load(PropertyManager.class.getClassLoader().getResourceAsStream("conf.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            propertyManager.initTankCount = Integer.parseInt(properties.get("initTankCount").toString());
        }
        return propertyManager;
    }

}
