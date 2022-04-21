package com.sing.juc.c_02;

/**
 * volatile
 * 1.保证线程可见性（缓存一致性协议）
 * 2.禁止指令重排序32
 */
public class T01_Volatile {
    volatile int i = 0;
}
