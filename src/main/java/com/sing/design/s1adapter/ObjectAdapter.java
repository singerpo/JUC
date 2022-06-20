package com.sing.design.s1adapter;

/**
 * 对象的适配器模式
 * @author songbo
 * @since 2022-06-20
 */
public class ObjectAdapter {
    public static void main(String[] args) {
        // 使用特殊功能类，即适配类，
        // 需要先创建一个被适配类的对象作为参数
        Target adapter = new SpecialAdapter(new Special());
        adapter.request();

    }
}

// 适配器类，直接关联被适配类，同时实现标准接口
class SpecialAdapter implements Target{
    // 直接关联被适配类
    private Special special;

    // 可以通过构造函数传入具体需要适配的被适配类对象
    public SpecialAdapter (Special special) {
        this.special = special;
    }

    public void request() {
        // 这里是使用委托的方式完成特殊功能
        this.special.specialRequest();
    }
}

