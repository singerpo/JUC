package com.sing.juc.c06;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**1.02
 * @author songbo
 * @since 2022-05-07
 */
public class T01Map {

    public static void main(String[] args) {
        /**
         * HashTable继承自Dictionary类,是线程安全的，key和value都不允许为空
         * JDK1.0引进的基本不使用
         * 负载因子0.75和初始容量11，扩容，大小为原来的两倍加1
         */
        Map<String, Object> hashTable = new Hashtable<>();

        /**
         *HashMap是无序的，key和value都可以为空，负载因子0.75和初始容量16，扩容，大小为原来的两倍
         * JDK1.2引进的
         * JDK7采用的是头插法,会导致死循环；主要是数组+链表
         * JDK8采用的是尾插法；主要是数组+链表+红黑树
         * （当链表长度大于8，hash桶大于64时，会转换到红黑树；当红黑树节点数量小于等于6时，会从红黑树转换回去单链表）
         */
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put(null,null);
        // 采用红黑树排序，key不允许为空，value可以为空
        Map<String, Object> treeMap = new TreeMap<>();
        treeMap.put("",null);

        Map<String, Object> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        synchronizedMap.put(null,null);

        Map<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        // 高并发并且排序（链表+跳表）
        Map<String,Object> concurrentSkipListMap = new ConcurrentSkipListMap<>();

    }
}
