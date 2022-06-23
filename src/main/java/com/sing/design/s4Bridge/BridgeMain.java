package com.sing.design.s4Bridge;

/**
 * 桥接模式
 * 将系统的抽象部分与实现部分分离解耦，使他们可以独立的变化；用聚合方式(桥)连接抽象与具体
 * @author songbo
 * @since 2022-06-23
 */
public class BridgeMain {
    public static void main(String[] args) {
        Gift gift = new WarmGift(new Flower());
        gift.operation();
    }
}

// 抽象
abstract class Gift{
    GiftImpl giftImpl;

    public Gift(GiftImpl giftImpl) {
        this.giftImpl = giftImpl;
    }

    public void operation(){
        giftImpl.operationImpl();
    }
}
class WarmGift extends Gift{

    public WarmGift(GiftImpl giftImpl) {
        super(giftImpl);
    }

    public void operation(){
        System.out.println("温暖的");
       super.operation();
    }
}

// 具体实现的接口
interface GiftImpl {
    void operationImpl();
}

class Flower implements GiftImpl {

    @Override
    public void operationImpl() {
        System.out.println("一朵花");
    }
}
