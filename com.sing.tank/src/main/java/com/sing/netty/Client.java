package com.sing.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author songbo
 * @since 2022-08-15
 */
public class Client {
    public static void main(String[] args) {
        // 事件处理的线程池
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap();
        try {
            ChannelFuture channelFuture = bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("127.0.0.1", 8686)
                    .sync();

            // // 如果没有.sync，则添加listener监听是否连接成功
            // channelFuture.addListener(new ChannelFutureListener() {
            //     @Override
            //     public void operationComplete(ChannelFuture channelFuture) throws Exception {
            //         System.out.println(channelFuture.isSuccess());
            //
            //     }
            // });
            // channelFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println(socketChannel);
    }
}