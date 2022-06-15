package com.sing.design.d1factorymethod.factorymethod;

import com.sing.design.d1factorymethod.bean.Plane;
import com.sing.design.d1factorymethod.bean.Toy;

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
