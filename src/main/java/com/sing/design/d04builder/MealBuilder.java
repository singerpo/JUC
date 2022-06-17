package com.sing.design.d04builder;

/**
 * 套餐构造器抽象类
 */
public abstract class MealBuilder {
    Meal meal = new Meal();
    
    public abstract MealBuilder buildFood();
    
    public abstract MealBuilder buildDrink();
    
    public Meal build(){
        return meal;
    }
}