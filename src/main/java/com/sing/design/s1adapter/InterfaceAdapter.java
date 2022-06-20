package com.sing.design.s1adapter;

/**
 * 接口的适配器模式
 * @author songbo
 * @since 2022-06-20
 */
public class InterfaceAdapter {
    public static void main(String[] args) {
        Sourceable source1 = new SourceSub1();
        Sourceable source2 = new SourceSub2();

        source1.method1();
        source1.method2();
        source2.method1();
        source2.method2();
    }

}


interface Sourceable {

    public void method1();
    public void method2();
}

abstract class Wrapper2 implements Sourceable{

    public void method1(){}
    public void method2(){}
}

class SourceSub1 extends Wrapper2 {
    public void method1(){
        System.out.println("实现第一个方法method1");
    }
}

class SourceSub2 extends Wrapper2 {
    public void method1(){
        System.out.println("实现第二个方法method2");
    }
}