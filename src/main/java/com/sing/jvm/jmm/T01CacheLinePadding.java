package com.sing.jvm.jmm;

/**
 * -、硬件层数据一致性
 * intel用MESI协议
 * 现代CPU的数据一致性实现 = 缓存锁（MES...) + 总线锁
 * 读取缓存以cache line为基本单位，目前大多是64bytes
 * 位于同一缓存行的两个不同数据，被两个不同的CPU锁定，就会产生互相影响的伪共享问题
 * 使用缓存行的对齐提高效率
 * 二、乱序问题
 *  CPU为了提高指令效率，会在一条指令执行过程中（比如去内存读数据），同时去执行另一条指令（前提是两条指令没有依赖关系）
 */
public class T01CacheLinePadding {
    private static class T {
        public volatile long x = 0L;
    }

    public static T[] arr = new T[2];

    static {
        arr[0] = new T();
        arr[1] = new T();
    }

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(()->{
            for (long i = 0; i < 1000_0000L; i++) {
                arr[0].x = i;
            }
        });

        Thread t2 = new Thread(()->{
            for (long i = 0; i < 1000_0000L; i++) {
                arr[1].x = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start)/100_0000);
    }
}