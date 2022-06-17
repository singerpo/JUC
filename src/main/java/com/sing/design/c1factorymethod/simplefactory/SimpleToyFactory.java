package com.sing.design.c1factorymethod.simplefactory;

import com.sing.design.c1factorymethod.bean.Car;
import com.sing.design.c1factorymethod.bean.Plane;
import com.sing.design.c1factorymethod.bean.Toy;

/**
 * 简单工厂:核心是定义一个创建对象的接口，将对象的创建和本身的业务逻辑分离.(当以后实现改变时，只需要修改工厂类即可)
 * 缺点在于不符合“开闭原则”，每次添加新产品就需要修改工厂类。
 */
public class SimpleToyFactory {

    public Toy getToy(int type) {
        switch (type) {
            case 1:
                return new Car();
            case 2:
                return new Plane();
            default:
                break;
        }
        return null;
    }

    public static void main(String[] args) {
        SimpleToyFactory simpleToyFactory = new SimpleToyFactory();
        Toy toy = simpleToyFactory.getToy(1);
        toy.play();
        toy = simpleToyFactory.getToy(2);
        toy.play();
    }
}
