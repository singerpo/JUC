package com.sing.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
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
 * 3.对象何时进入老年代？
 * --超过XX:MaxTenuringThreshold指定次数（CMS默认6其他都是默认15）
 * --大对象直接进入(-XX:PretenureSizeThreshold 默认值为0）
 * --垃圾回收时Edan+S1存活对象超过S2的内存空间50%(TargetSurviveRatio)时 超过的所有年龄段进入
 *
 * 四、10种垃圾回收器
 * Serial(STW单线程拷贝算法）              Serial Old(单线程标记压缩算法) 【内存少于100M建议使用】
 * Parallel Scavenge(STW 多线程拷贝算法）  Parallel Old                【关注吞吐量】
 * ParNew   CMS(经历初始标记(STW 找到GCRoots直接关联对象）、并发标记（三色标记法 + Incremental Update)、重新标记(STW)、并发清理4个阶段) 【关注停顿时间】
 * G1(三色标记法 + SATB) (逻辑分代物理不分代）
 * ZGC（ColoredPointers + 读屏障）
 * Shenandoah（ColoredPointers + 写屏障）
 * Epsilon
 *
 * 五.三色标记算法
 * 1.将对象的颜色分为了黑、灰、白三种颜色。
 * --黑色：该对象已经被标记过了，且该对象下的属性也全部都被标记过了
 * --灰色：该对象已经被标记了，但该对象下的属性没有全被标记完（GC需要从此对象中去寻找垃圾）
 * --白色：该对象没有被标记过（对象垃圾）
 * 2.在并发标记时，引用可能产生变化，白色对象有可能被错误回收
 * 解决方案
 *     --SATB(snapshot at the beginning)
 *     .在起始的时候做一个快照
 *     .当灰色->白色的引用消失时，要把这个引用推到GC的堆栈，保证该白色还能被GC扫描到
 *    -- Incremental Update
 *     .当一个白色对象被一个黑色对象引用
 *     .将黑色对象重新标记为灰色，让GC重新扫描
 *
 *六.常见垃圾回收器组合参数设定
 * ①-XX:+UseSerialGC = Serial New(DefNew) + SerialOld
 * ②-XX:+UseParNewGC = ParNew + SerialOld【已废弃】
 * ③-XX:+UseConc(urrent)MarkSweepGC = ParNew + CMS + Serial Old
 * ④-XX:+UseParallelGC = Parallel Scavenge + Parallel Old(1.8默认）【PS + SerialOld】
 * ⑤-XX:+UseParallelOldGC = Parallel Scanvenge + Parallel Old
 * ⑥-XX:+UseG1GC = G1
 *
 *
 *
 *
 */
public class TestGc {

    public static void main(String[] args) {
        Integer i = 1;
        Integer j = Integer.parseInt("1");
        Integer k = Integer.valueOf(1);
        System.out.println(i == j);
        System.out.println(i == k);
    }
}
