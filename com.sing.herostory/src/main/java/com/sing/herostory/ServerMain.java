package com.sing.herostory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup);
        // 服务器信道的处理方式
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 处理连接客户端
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast(
                        // Http服务器编码解码器
                        new HttpServerCodec(),
                        // 内容长度限制
                        new HttpObjectAggregator(65535),
                        // WebSocet协议处理器，在这里处理握手、ping等消息
                        new WebSocketServerProtocolHandler("/websocket"),
                        // 自定义消息处理器
                        new GameMsgHandler()

                );

            }
        });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 8686).sync();
            if(channelFuture.isSuccess()){
                LOGGER.info("服务器启动成功");
            }
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
