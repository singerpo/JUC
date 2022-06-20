package com.sing.design.s2decorator;

/**
 * 结构型-装饰器模式
 * 可以动态给对象添加一些额外的职责从而实现功能的拓展，在运行时选择不同的装饰器，从而实现不同的行为；比使用继承更加灵活，通过对不同的装饰类进行排列组合，创造出很多不同行为，得到功能更为强大的对象；
 *
 * @author songbo
 * @since 2022-06-20
 */
public class Main {
    public static void main(String[] args) {
        Human person = new Person();
        Decorator decorator = new DecoratorThird(new DecoratorSecond(new DecoratorFirst(person)));
        decorator.wearClothes();
    }
}

//定义被装饰者
interface Human {
    void wearClothes();
}

// 定义装饰者
abstract class Decorator implements Human{
    private Human human;
    public Decorator(Human human){
        this.human = human;
    }

    @Override
    public void wearClothes() {
        human.wearClothes();
    }
}

// 下面定义三种装饰者实现类，这是第一个，第二个第三个功能依次细化，即装饰者的功能越来越多
class DecoratorFirst extends Decorator {

    public DecoratorFirst(Human human) {
        super(human);
    }

    public void goHome() {
        System.out.println("进房间...");
    }

    @Override
    public void wearClothes() {
        super.wearClothes();
        goHome();
    }
}

class DecoratorSecond extends Decorator {

    public DecoratorSecond(Human human) {
        super(human);
    }

    public void goClothespress() {
        System.out.println("去衣柜找找看...");
    }

    @Override
    public void wearClothes() {
        super.wearClothes();
        goClothespress();
    }
}

class DecoratorThird extends Decorator {

    public DecoratorThird(Human human) {
        super(human);
    }

    public void findClothes() {
        System.out.println("找到一件牛仔裤");
    }

    @Override
    public void wearClothes() {
        super.wearClothes();
        findClothes();
    }
}


class Person implements Human {

    @Override
    public void wearClothes() {
        System.out.println("穿什么呢?");
    }
}

