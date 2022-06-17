package com.sing.design.c4builder;

public class AmealBuilder extends MealBuilder {

    public MealBuilder buildFood() {
        meal.setFood("1盒薯条");
        return this;
    }

    public MealBuilder buildDrink() {
        meal.setDrink("1杯可乐");
        return this;
    }

}