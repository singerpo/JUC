package com.sing.design.c4builder;

public class BmealBuilder extends MealBuilder{

    public MealBuilder buildFood() {
        meal.setFood("3个鸡翅");
        return this;
    }

    public MealBuilder buildDrink() {
        meal.setDrink("1杯柠檬果汁");
        return this;
    }
 

 
}