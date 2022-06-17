package com.sing.design.d04builder;

/**
 * 套餐类
 */
public class Meal {
    private String food;
    private String drink;

    static class Builder{
        Meal meal = new Meal();
        public Builder buildFood(String food){
            meal.setFood(food);
            return this;
        }
        public Builder buildDrink(String drink){
            meal.setDrink(drink);
            return this;
        }
        public Meal build(){
            return this.meal;
        }
    }

    public String getFood() {
        return food;
    }
 
    public void setFood(String food) {
        this.food = food;
    }
 
    public String getDrink() {
        return drink;
    }
 
    public void setDrink(String drink) {
        this.drink = drink;
    }
}