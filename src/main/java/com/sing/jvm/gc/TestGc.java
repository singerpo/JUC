package com.sing.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**110:2.02
 *
 * 熟悉GC常用算法，熟悉常见垃圾收集器，具有实际JVM调优实战经验
 * -.找出垃圾的方法
 * ①引用计数法（无法解决循环引用问题）
 * ②Root Searching(根可达算法或根搜索算法）
 * --GC roots(根）包含：JVM stack,native method stack,runtime constant pool,
 *   static references in method area,Clazz(也就是线程栈变量、静态变量、常量池、JNI指针)
 *
 * 二.垃圾回收算法
 * ①Mark-Sweep(标记清除）
 * ②Copying（拷贝）
 * ③Mark-Compact(标记压缩）
 *
 * 三.JVM内存分代模型（用于分代垃圾回收算法）
 * 1.除ZGC Epsilon Shenandoah之外的GC都是使用分代模型
 *  G1是逻辑分代，物理不分代
 *  除此之外的分代模型，不仅逻辑分代而且物理也分代
 * 2.虚拟机对象分配流程：尝试栈上分配→尝试线程本地分配TLAB→是否满足进入老年代→eden分配
 * 3，对象何时进入老年代？
 * --超过XX:MaxTenuringThreshold指定次数（CMS默认6其他都是默认15）
 * --大对象直接进入(-XX:PretenureSizeThreshold 默认值为0）
 * --垃圾回收时Edan+S1存活对象超过S2的内存空间50%(TargetSurviveRatio)时 超过的所有年龄段进入
 *
 *
 *
 *
 *
 *
 */
public class TestGc {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            if(i == 0){
                list.remove(0);
            }
        }

    }
}
