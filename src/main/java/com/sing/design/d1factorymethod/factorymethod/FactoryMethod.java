package com.sing.design.d1factorymethod.factorymethod;

/**
 * 创建型-工厂方法：只有一个抽象产品类和一个抽象工厂类(每个具体工厂类只能创建一个具体产品类的实例)
 * @author songbo
 * @since 2022-06-15
 */
public class FactoryMethod {
    public static void main(String[] args) {
        ToyFactory toyFactory = new CarFactroy();
        toyFactory.getToy().play();
        toyFactory = new PlaneFactory();
        toyFactory.getToy().play();
    }
}
