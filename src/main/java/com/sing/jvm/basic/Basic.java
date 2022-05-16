package com.sing.jvm.basic;

/**2.25
 * 常见的JVM实现
 * 1.Hotspot:oracle官方，我们做实验用的JVM
 * 2.azul zing:最新垃圾回收的业界标杆（www.azul.com)
 * 3.TaobaoVM:hotspot深度定制版
 * 4.LiquidVM：直接针对硬件
 * 5.J9:IBM
 * 6.Microsoft VM
 * 7.jrockit:BEA,曾经号称世界上最快的JVM,被Oracle收购合并于hotspot
 */

/**
 * class文件结构
 * magic: ca fe ba be
 * minor version: 00 00
 * major version: 00 34
 * constant_pool_count(常量池数量）:00 10
 * constant_pool(长度为constant_pool_count-1的表）
 * access_flag
 * this_class
 * super_class
 * interfaces_count
 * interfaces
 * fields_count
 * fields
 * methods_count
 * methods
 * attributes_count
 * attributes
 */
public class Basic {
    private final static int field1 = 1;

    private int field2 = 3;

    {
        System.out.println("方法块" + "field2=" + field2);
    }

    static {
        System.out.println("静态方法块" + "field1=" + field1);
    }

    public Basic() {
        System.out.println("构造方法" + "field1=" + field1 + ";field2=" + field2);
    }

    public static void main(String[] args) {
        new Basic();

    }

}
