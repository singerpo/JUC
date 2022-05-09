package com.sing.juc.c06;

import java.util.*;
import java.util.concurrent.*;

/**
 * Queue List的区别
 * --Queue提供了对线程友好的API: offer  peek poll
 * --BlockingQueue提供了 put take(阻塞)
 * @author songbo
 * @since 2022-05-09
 */
public class T03Collection {
    public static void main(String[] args) throws InterruptedException {
        /**
         * Vector是线程安全的
         * JDK1.0引进的基本不使用
         */
        List<String> vector = new Vector<>();
        List<String> list = new ArrayList<>();
        /**
         * 写复制，适合多线程写少读多场景(添加的时候加锁复制内部数组）
         */
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add("");
        copyOnWriteArrayList.get(0);

        /**
         * 多线程并发优先考虑使用队列
         */
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        // 添加元素
        queue.offer("a");
        // 获取头元素
        queue.peek();
        // 获取头元素并移除
        queue.poll();

        Queue<String> priorityQueue = new PriorityQueue<>();

        //无界队列
        BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
        // 如果满了，则阻塞
        linkedBlockingQueue.put("a");
        // 如果没有取到，则阻塞
        linkedBlockingQueue.take();

        // 有界队列
        BlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(100);

        // 按时间排序，时间短的在前面
        BlockingQueue delayQueue = new DelayQueue();
        // 容量为0，通常用于线程通知任务
        BlockingQueue<String> synchronousQueue = new SynchronousQueue<>();

        TransferQueue<String> linkedTransferQueue = new LinkedTransferQueue<>();
        // 阻塞等待被消费
        linkedTransferQueue.transfer("a");



    }
}
