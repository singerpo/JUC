package com.sing.design.s3proxy;

/**
 * 静态代理
 *
 * @author songbo
 * @since 2022-06-21
 */
public class StaticProxy {
    public static void main(String[] args) {
        new BoyProxy(new Boy()).giveFlowers();
    }
}

interface GiveGift {
    void giveFlowers();
}

class Boy implements GiveGift{

    @Override
    public void giveFlowers() {
        System.out.println("送美女一束花");
    }
}

class BoyProxy implements GiveGift{
    GiveGift giveGift;

    public BoyProxy(GiveGift giveGift) {
        this.giveGift = giveGift;
    }

    @Override
    public void giveFlowers() {
        System.out.println("说一些男孩的优点");
        giveGift.giveFlowers();

    }
}


