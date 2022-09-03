package com.sing.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.sing.herostory.cmdHandler.CmdHandlerFactory;
import com.sing.herostory.cmdHandler.ICmdHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 主线程处理器
 */
public final class MainThreadProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainThreadProcessor.class);

    private static final MainThreadProcessor INSTANCE = new MainThreadProcessor();

    /*** 创建一单线程池**/
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private MainThreadProcessor() {

    }

    public static MainThreadProcessor getInstance() {
        return INSTANCE;
    }

    /**
     * 处理消息
     *
     * @param channelHandlerContext 客户端信道上下文
     * @param msg                   消息对象
     */
    public void process(ChannelHandlerContext channelHandlerContext, GeneratedMessageV3 msg) {
        this.executorService.submit(() -> {
            ICmdHandler<? extends GeneratedMessageV3> cmdHandler = CmdHandlerFactory.create(msg.getClass());
            if (cmdHandler != null) {

                try {
                    cmdHandler.handle(channelHandlerContext, cast(msg));
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } else {
                LOGGER.error(msg.getClass().getName() + ",消息未处理");
            }
        });
    }

    public void process(Runnable runnable) {
        this.executorService.submit(() -> {
            runnable.run();
        });
    }

    /**
     * 转型消息对象
     *
     * @param msg
     * @param <TCmd>
     * @return
     */
    private <TCmd extends GeneratedMessageV3> TCmd cast(Object msg) {
        if (msg == null) {
            return null;
        } else {
            return (TCmd) msg;
        }
    }

}
