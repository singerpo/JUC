package com.sing.herostory;

import com.sing.herostory.msg.GameMsgDecoder;
import com.sing.herostory.msg.GameMsgEncoder;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.下载工具将.proto文件转换为java类
 *  https://github.com/protocolbuffers/protobuf/releases protoc-3.16.0-rc-1-win64
 *  protoc.exe --java_out=目标目录 .\GameMsgProtocol.proto
 *  2.前端
 *  Cocos Creator 2.2.0
 *  开发环境：VS Code
 *  3.调试连接本地服务器
 *  http://cdn0001.afrxvk.cn/hero_story/demo/step010/index.html?serverAddr=127.0.0.1:8686&userId=1
 *
 */
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
                        // 自定义消息解码器
                        new GameMsgDecoder(),
                        //自定义编码器
                        new GameMsgEncoder(),
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
            // 等待服务器信道关闭（阻塞在这里）
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
