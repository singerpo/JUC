package com.sing.herostory.async;

import com.sing.herostory.MainThreadProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 异步操作处理器
 */
public class AsyncOperationProcessor {
    private static final AsyncOperationProcessor INSTANCE = new AsyncOperationProcessor();
    /*** 创建一单线程池数组**/
    private final ExecutorService[] executorServices = new ExecutorService[8];

    private AsyncOperationProcessor() {
        for (int i = 0; i < executorServices.length; i++) {
            String threadName = "AsyncOperationProcessor-" + i;
            executorServices[i] = Executors.newSingleThreadExecutor(runnable -> {
                        Thread thread = new Thread(runnable);
                        thread.setName(threadName);
                        return thread;
                    }
            );
        }
    }

    public static AsyncOperationProcessor getInstance() {
        return INSTANCE;
    }

    /**
     * 处理异步操作
     *
     * @param asyncOperation 异步操作
     */
    public void process(IAsyncOperation asyncOperation) {
        if (asyncOperation == null) {
            return;
        }
        int bindId = Math.abs(asyncOperation.bindId());
        ExecutorService executorService = executorServices[bindId % executorServices.length];
        executorService.submit(() -> {
            try {
                // 执行异步操作
                asyncOperation.doAsync();
                // 返回主线程执行完成逻辑
                MainThreadProcessor.getInstance().process(asyncOperation::doFinish);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
