package com.sing.netty;

import com.sing.netty.chat.ServerFrame;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author songbo
 * @since 2022-08-16
 */
public class Server {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private ServerFrame serverFrame;

    public void startServer(ServerFrame serverFrame) {
        this.serverFrame = serverFrame;
        // 只负责连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast(new ServerChildHandler());

                        }
                    })
                    .bind("127.0.0.1", 8686)
                    .sync();
            this.serverFrame.updateServerMsg("server started!");
            // 阻塞等待closeFuture
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    class ServerChildHandler extends ChannelInboundHandlerAdapter {//SimpleChannelInboundHandler


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Server.clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf byteBuf = (ByteBuf) msg;
            try {
                byte[] bytes = new byte[byteBuf.readableBytes()];
                // byteBuf.readBytes(bytes);
                byteBuf.getBytes(byteBuf.readerIndex(), bytes);
                String clientMsg = new String(bytes, "UTF-8");
                getServerFrame().updateClientMsg(clientMsg);
                if ("b_bye_b".equals(clientMsg)) {
                    System.out.println("客户端要求退出");
                    // 删除出异常的客户端channel并关闭
                    Server.clients.remove(ctx.channel());
                    ctx.close();
                    return;
                }
                // 回复信息给客户端
                Server.clients.writeAndFlush(msg);
            } finally {
                // if (byteBuf != null) {
                //     // 释放内存
                //     ReferenceCountUtil.release(byteBuf);
                // }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            // 删除出异常的客户端channel并关闭
            Server.clients.remove(ctx.channel());
            ctx.close();

        }
    }

    public ServerFrame getServerFrame() {
        return serverFrame;
    }

    public void setServerFrame(ServerFrame serverFrame) {
        this.serverFrame = serverFrame;
    }
}


