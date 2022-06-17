package com.sing.design.d04builder;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

/**
 * 创建型-建造者模式
 * 分离复杂对象的构建和表示
 * 同样的构造过程可以创建不同的表示
 */
public class Main {
    public static void main(String[] args) {
        // 第三方apache用静态内部类实现
        new BasicThreadFactory.Builder().daemon(true).build();

        // 直接调用
        Meal mealA = new AmealBuilder().buildFood().buildDrink().build();
        System.out.println(mealA.getFood() + " -- " + mealA.getDrink());

        // 由指挥者调用构建[标准]
        mealA = new Director(new AmealBuilder()).construct();
        System.out.println(mealA.getFood() + " -- " + mealA.getDrink());

        // 静态内部类调用[常用]
        mealA = new Meal.Builder().buildFood("1盒薯条").buildDrink("1杯可乐").build();
        System.out.println(mealA.getFood() + " -- " + mealA.getDrink());
    }

}
