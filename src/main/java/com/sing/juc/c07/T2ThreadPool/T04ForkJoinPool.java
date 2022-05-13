package com.sing.juc.c07.T2ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class T04ForkJoinPool {
    public static void main(String[] args) {
        /**
         * 多个work queue
         * 采用work stealing算法
         */
        ExecutorService workStealingPool = Executors.newWorkStealingPool();
        // .parallelStream

    }
}
