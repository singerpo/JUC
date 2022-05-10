package com.sing.juc.c07.T2ThreadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 2.10
 */
public class T03ThreadPoolExecutor {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        /**
         * 拒绝策略
         * --Abort:抛异常
         * --Discard:扔掉，不抛异常
         * --DiscardOldest:扔掉排队时间最久的
         * --CallerRuns:调用者处理任务
         */
    }
}
