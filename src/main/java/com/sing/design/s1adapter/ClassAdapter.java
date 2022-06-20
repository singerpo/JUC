package com.sing.design.s1adapter;

/**
 * 类的适配器模式
 *
 * @author songbo
 * @since 2022-06-20
 */
public class ClassAdapter {
    public static void main(String[] args) {
        // 使用普通功能类
        Target concreteTarget = new ConcreteTarget();
        concreteTarget.request();

        // 使用特殊功能类，即适配类
        Target adapter = new Adapter();
        adapter.request();
    }
}

//被适配的类不符合标准接口
class Special {
    public void specialRequest() {
        System.out.println("被适配的类 具有特殊功能");
    }
}

// 目标接口或称为标准接口
interface Target {
    public void request();
}

// 符合标准接口的实现类
class ConcreteTarget implements Target {
    public void request() {
        System.out.println("符合标准接口的实现类 具有普通功能");
    }
}

// 适配器类，继承了被适配类，同时实现标准接口
class Adapter extends Special implements Target {
    public void request() {
        super.specialRequest();
    }
}


