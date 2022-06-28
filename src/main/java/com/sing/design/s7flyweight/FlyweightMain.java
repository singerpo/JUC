package com.sing.design.s7flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * 享元模式
 * 重复利用对象
 * 享元模式通过共享技术有效地支持细粒度、状态变化小的对象复用，当系统中存在有多个相同的对象，那么只共享一份，不必每个都去实例化一个对象，极大地减少系统中对象的数量。
 * @author songbo
 * @since 2022-06-28
 */
public class FlyweightMain {
    public static void main(String[] args) {
        Circle circle1 = FlyweightFactory.getShape("红色");
        circle1.draw();
        Circle circle2 = FlyweightFactory.getShape("红色");
        circle2.draw();

        System.out.println(circle1 == circle2);
    }
}

//享元工厂类
class FlyweightFactory{
    static Map<String, Circle> circles = new HashMap<>();

    public static Circle getShape(String key){
        Circle circle = circles.get(key);
        //如果shape==null,表示不存在,则新建,并且保持到共享池中
        if(circle == null){
            circle = new Circle(key);
            circles.put(key, circle);
        }
        return circle;
    }

    public static int getSum(){
        return circles.size();
    }
}


class Circle {
    private String color;

    public Circle(String color) {
        this.color = color;
    }

    public void draw() {
        System.out.println("画了一个" + color + "的圆形");
    }
}

