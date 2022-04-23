package com.sing.juc.c02;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicXX类本身方法都是原子性的，但不能保证多个方法连续调用时原子性的(Unsafe类)
 * CAS(compare and swap),比较并交换。可以解决多线程并行情况下使用锁造成性能损耗的一种机制.
 * CAS 操作包含三个操作数—内存位置（V）、预期原值（A）和新值(B)。
 * CAS使用的时机:线程数较少、等待时间短可以采用自旋锁进行CAS尝试拿锁，较于synchronized高效。
 * ABA问题
 * 加version来解决
 * JUC提供了了AtomicStampedRefence类，通过控制变量值的版本来保证CAS的正确性
 *
 * @author songbo
 * @since 2022-04-22
 */
public class T02AtomicInteger {
    // 保证 atomicInteger.incrementAndGet()是原子操作
    AtomicInteger atomicInteger = new AtomicInteger(0);
    // 只能保证count线程可见性，但不能保证count++的原子操作
    volatile int count = 0;

    void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
            atomicInteger.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        T02AtomicInteger t02AtomicInteger = new T02AtomicInteger();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t02AtomicInteger::m, "thread-" + i));
        }

        threads.forEach(thread -> {
            thread.start();
            // try {
            //     thread.join();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        });

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t02AtomicInteger.atomicInteger);
        System.out.println(t02AtomicInteger.count);
    }
}
