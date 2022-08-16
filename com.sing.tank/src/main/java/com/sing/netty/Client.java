package com.sing.netty;

import com.sing.netty.chat.ClientFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @author songbo
 * @since 2022-08-15
 */
public class Client {

    private ChannelFuture channelFuture;
    private ClientFrame clientFrame;

    public void connect(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        // 事件处理的线程池
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap();
        try {
            channelFuture = bootstrap.group(eventLoopGroup)
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
            // 阻塞等待closeFuture
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline channelPipeline = socketChannel.pipeline();
            channelPipeline.addLast(new ClientChildHandler());
        }
    }

    class ClientChildHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            try {
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.getBytes(byteBuf.readerIndex(), bytes);
                System.out.println(new String(bytes, "UTF-8"));
                getClientFrame().setText(new String(bytes, "UTF-8"));

            } finally {
                if (byteBuf != null) {
                    // 释放内存
                    ReferenceCountUtil.release(byteBuf);
                }
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // channel第一次连上可用，写出一个字符串(ByteBuf访问直接内存实现零拷贝)
            ByteBuf byteBuf = Unpooled.copiedBuffer("你好，我进门了开始点餐".getBytes("UTF-8"));
            //会自动释放
            ctx.writeAndFlush(byteBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public ClientFrame getClientFrame() {
        return clientFrame;
    }

    public void setClientFrame(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
    }
}

