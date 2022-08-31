package com.sing.herostory;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public final class BroadCaster {
    /***客户端信道数组，必须是static **/
    private static final ChannelGroup CLIENTS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private BroadCaster() {

    }

    /**
     * 添加信道
     *
     * @param channel
     */
    public static void addChannel(Channel channel) {
        CLIENTS.add(channel);
    }

    /**
     * 移除信道
     *
     * @param channel
     */
    public static void removeChannel(Channel channel) {
        CLIENTS.remove(channel);
    }

    /**
     * 广播消息
     *
     * @param msg
     */
    public static void broadcast(Object msg) {
        if (msg != null) {
            CLIENTS.writeAndFlush(msg);
        }
    }


}
