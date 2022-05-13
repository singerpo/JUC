package com.sing.jmh;

import org.openjdk.jmh.annotations.*;

/**
 * @author songbo
 * @since 2022-05-13
 */
public class ParallelStreamTest {
    @Benchmark
    @Warmup(iterations = 1,time = 3)// 预热：调用一次，等待3秒
    @Fork(5)// 起5个线程
    @BenchmarkMode(Mode.AverageTime)// 基准测试的模式
    @Measurement(iterations = 1,time = 3)// 执行测试
    public void forEachTest(){
        ParallelStream.foreach();
    }
}
