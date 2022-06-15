package com.sing.design.d1factorymethod.staticfactory;

import com.sing.design.d1factorymethod.bean.Car;
import com.sing.design.d1factorymethod.bean.Plane;
import com.sing.design.d1factorymethod.bean.Toy;
import com.sing.design.d1factorymethod.simplefactory.SimpleToyFactory;

/**
 * 静态工厂
 * @author songbo
 * @since 2022-06-15
 */
public class StaticToyFactory {
    public static Toy getToy(int type) {
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
        Toy toy1 = StaticToyFactory.getToy(1);
        toy1.play();
        Toy toy2 = StaticToyFactory.getToy(2);
        toy2.play();
    }
}
