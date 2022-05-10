package com.sing.juc.c07.T2ThreadPool;

import java.util.concurrent.*;

/**1.27
 * @author songbo
 * @since 2022-05-10
 */
public class T01Futrue {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        // 提交异步执行
        Future<Integer> future = executorService.submit(()->{
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });
        // 阻塞直到获取结果
        System.out.println(future.get());
        System.out.println(future.isDone());
        executorService.shutdown();

        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.MILLISECONDS.sleep(500);
                return 1000;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        // 阻塞
        System.out.println(futureTask.get());

    }
}
