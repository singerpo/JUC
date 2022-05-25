package com.sing.jvm.jmm;

import org.openjdk.jol.info.ClassLayout;

/**
 * 关于对象
 * 1.请解释一下对象的创建过程？
 * (1)class loading
 * (2)class linking(verification,preparation,resolution)
 * (3)class initializing
 * (4)申请对象内存
 * (5)成员变量赋默认值
 * (6)调用构造方法<init>
 *     --成员变量顺序赋初始值
 *     --执行构造方法语句
 * 2.对象在内存中的存储布局？
 *  通过java -XX:+PrintCommandLineFlags -version观察虚拟机配置
 *  （1）普通对象
 *      1.对象头：mark word 8字节
 *      2.对象头：ClassPointer指针：-XX:+UseCompressedClassPointers 为4字节 不开启为8字节
 *      3.实例数据
 *          --引用类型：-XX:+UseCompressedOops 为4字节 不开启为8字节
 *      4.Padding（填充对齐），8的倍数
 *  （2）数组对象
 *      1.对象头：markword 8字节
 *      2.对象头：ClassPointer指针：-XX:+UseCompressedClassPointers 为4字节 不开启为8字节
 *      3.对象头：数组长度：4字节
 *      4.数组数据
 *      5.Padding（填充对齐），8的倍数
 * 3.mark word具体包括什么？
 * 锁标志位（2位；GC标记11）、是否偏向锁（1位）、分代年龄（4位）、对象的hashCode（25位）[线程ID、Epoch]
 * -- 所以分代年龄最大为15，GC年龄默认为15
 * -- 当一个对象计算过hashCode之后，不能进入偏向锁状态
 * 4.对象怎么定位（访问对象的两种方式）？
 * --直接指针（HotPost使用该方式）
 * --句柄池
 * 5.对象怎么分配？
 * 6.Object o = new Object()在内存中占用多少字节？
 * 16字节
 *
 */
public class Test03Object {
    public static void main(String[] args) {
        Object obj = new Object();
        // markword:8 类型指针：4 对齐：4，一共16字节
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        // markword:8 类型指针：4 数组长度:4 数组数据：0 对齐：0，一共16字节
        String[] strs = new String[]{};
        System.out.println(ClassLayout.parseInstance(strs).toPrintable());
    }

}
