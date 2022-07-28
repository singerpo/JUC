package com.sing.tank;

import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author songbo
 * @since 2022-07-28
 */
public class Location {
    private int x;
    private int y;
    static List<Location> locations = new ArrayList<>();
    static {
        locations.add(new Location(120,820));
        locations.add(new Location(870,820));
        locations.add(new Location(120,125));
        locations.add(new Location(870,125));
    }

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Location getRandomLocation(){
        Random random = new Random();
        int i = random.nextInt(4);
        return locations.get(i);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
