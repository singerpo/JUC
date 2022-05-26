package com.sing.jvm.runtime;

/**
 * 2.01
 * 1.Java运行时数据区
 * 包含堆、栈、方法区、本地方法栈、程序计数器、直接内存
 * 2.栈包含多个栈帧，一个栈帧包含局部变量表、操作数栈、帧数据区（包含常量池指针、异常处理表、返回值）
 * 3.创建对象指令
 *  --new #2  //<com.xx.xx> 申请内存、成员变量赋默认值，压栈
 *  --dup
 *  --invokespecial #3  //<com.xx.xx.<init>> 成员表里赋初始值、执行构造方法
 *  --astore_1 赋值
 */
public class TestIPlusPlus {

    public static void main(String[] args) {
        int i = 8;
        i = i++;//先把i的值8压栈，然后局部表里的i自增1变为9，最后栈里的i值8赋值给i,所以i变为8
        //i = ++i;// 先把局部表里的i自增变为9，然后把i的值9压栈，最后栈李的i值9赋值给i,所以i变为9
        System.out.println(i);
    }
}
