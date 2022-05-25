package com.sing.jvm.jmm;

/**
 * JMM(Java内存模型)定义了Java虚拟机在计算机内存中的工作方式。可见性、有序性、原子性
 *volatile的实现细节
 * 1.字节码层面
 *  flags: ACC_VOLATILE
 * 2.JVM层面
 * 在volatile内存区的读写前后都加屏障，具体如下
 *  StoreStoreBarrier    LoadLoadBarrier
 *   volatile 写操作        volatile 读操作
 *  StoreLoadBarrier     LoadStoreBarrier
 *
 * 3.OS和硬件层面
 *    https://blog.csdn.net/qq_26222859/article/details/52235930
 *    hsdis - HotSpot Dis Assembler
 *    windows lock 指令实现 | MESI实现
 *
 */
public class Test01Volatile {
    int i;
    volatile int j;

    public static void main(String[] args) {

    }
}
