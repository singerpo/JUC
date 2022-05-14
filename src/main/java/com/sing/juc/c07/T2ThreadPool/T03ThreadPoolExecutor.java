package com.sing.juc.c07.T2ThreadPool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**8  1.33
 * 参数含义
 * --corePoolSize:线程池中核心线程数量
 * --maximumPoolSize:线程池中最大线程数量（当corePoolSize和worQueue都满了，才创建新的非核心线程）
 * --keepAliveTime:空闲线程存活时间
 * --unit：空闲线程存活时间的单位
 * --worQueue:用于缓存任务的阻塞队列
 * --threadFactory:创建线程的工厂
 * --handler：拒绝策略
 * 拒绝策略
 * --AbortPolicy:舍弃，抛异常
 * --DiscardPolicy:直接丢弃，不抛异常
 * --DiscardOldestPolicy:丢弃排队时间最久的
 * --CallerRunsPolicy:调用者处理任务
 * 实现RejectedExecutionHandler自定义策略（存放MQ等）
 */
public class T03ThreadPoolExecutor {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.shutdown();

        //指定线程工厂（定义名称，是否守护线程）
        new BasicThreadFactory.Builder().namingPattern("job-schedule-pool-%d").daemon(false).build();

        // LinkedBlockingQueue
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        // LinkedBlockingQueue
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        // SynchronousQueue
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        // DelayedWorkQueue
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);

    }
}
