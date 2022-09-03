package com.sing.herostory.async;

import com.sing.herostory.MainThreadProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步操作处理器
 */
public class AsyncOperationProcessor {
    private static final AsyncOperationProcessor INSTANCE = new AsyncOperationProcessor();
    /*** 创建一单线程池**/
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private AsyncOperationProcessor() {
    }

    public static AsyncOperationProcessor getInstance() {
        return INSTANCE;
    }

    /**
     * 处理异步操作
     * @param asyncOperation 异步操作
     */
    public void process(IAsyncOperation asyncOperation){
        if(asyncOperation == null){
            return;
        }
        executorService.submit(()->{
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
