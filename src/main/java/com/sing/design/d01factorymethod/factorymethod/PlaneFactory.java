package com.sing.design.d01factorymethod.factorymethod;

import com.sing.design.d01factorymethod.bean.Plane;
import com.sing.design.d01factorymethod.bean.Toy;

/**
 * @author songbo
 * @since 2022-06-15
 */
public class PlaneFactory implements ToyFactory{
    @Override
    public Toy getToy() {
        return new Plane();
    }
}
