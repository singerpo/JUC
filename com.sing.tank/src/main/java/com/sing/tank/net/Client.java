package com.sing.tank.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @author songbo
 * @since 2022-08-15
 */
public class Client {
    public static final Client INSTANCE = new Client();
    private Channel channel;

    private Client() {

    }

    public void connect() {
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
            this.channel = channelFuture.channel();
            // 阻塞等待closeFuture
            this.channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    /**
     * 向服务器发送自定义信息
     *
     * @param msg 自定义信息
     */
    public void send(Msg msg) {
        this.channel.writeAndFlush(msg);
    }

    /**
     * 向服务端发送消息
     *
     * @param msg 消息
     */
    public void sendMsg(String msg) {
        ByteBuf byteBuf;
        try {
            byteBuf = Unpooled.copiedBuffer(msg.getBytes(StandardCharsets.UTF_8));
            this.channel.writeAndFlush(byteBuf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) {
            ChannelPipeline channelPipeline = socketChannel.pipeline();
            channelPipeline.addLast(new MsgEncoder())
                    .addLast(new MsgDecoder())
                    .addLast(new ClientChildHandler());
        }
    }


    class ClientChildHandler extends SimpleChannelInboundHandler<Msg> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
            msg.handleClient();
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
//            ctx.writeAndFlush(new TankJoinMsg(GameModel.getInstance().getMainTank()));
//             ByteBuf byteBuf = Unpooled.copiedBuffer("come".getBytes("UTF-8"));
//             ctx.writeAndFlush(byteBuf);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}

