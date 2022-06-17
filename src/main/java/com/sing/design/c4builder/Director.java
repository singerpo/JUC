package com.sing.design.c4builder;

/**
 * 指挥者 Director
 * 构建一个使用 Builder 接口的对象。主要有两个作用，一是隔离用户与对象的生产过程，二是负责控制产品对象的生产过程。
 *
 */
public class Director {
    private MealBuilder mealBuilder;

    public Director(MealBuilder mealBuilder){
        this.mealBuilder = mealBuilder;
    }

    public Meal construct(){
        return mealBuilder.buildFood().buildDrink().build();
    }
}
