package com.sing.design.d04builder;

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