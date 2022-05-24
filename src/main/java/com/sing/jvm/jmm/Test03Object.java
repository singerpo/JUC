package com.sing.jvm.jmm;

import org.openjdk.jol.info.ClassLayout;

/**2.
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
 *      1.对象头：markword 8字节
 *      2.对象头：ClassPointer指针：-XX:+UseCompressedClassPointers 为4字节 不开启为8字节
 *      3.实例变量
 *          --引用类型：-XX:+UseCompressedOops 为4字节 不开启为8字节
 *      4.Padding对齐，8的倍数
 *  （2）数组对象
 *      1.对象头：markword 8字节
 *      2.对象头：ClassPointer指针：-XX:+UseCompressedClassPointers 为4字节 不开启为8字节
 *      3.对象头：数组长度：4字节
 *      4.数组数据
 *      5.Padding对齐，8的倍数
 * 3.对象头具体包括什么？
 * 锁标志位、是否偏向锁、分代年龄、对象的hashCode
 * 4.对象怎么定位？
 * 5.对象怎么分配？
 * 6.Object o = new Object()在内存中占用多少字节？
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
