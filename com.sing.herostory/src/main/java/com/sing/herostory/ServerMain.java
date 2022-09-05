package com.sing.herostory;

import com.sing.herostory.cmdHandler.CmdHandlerFactory;
import com.sing.herostory.mq.MQProducer;
import com.sing.herostory.util.RedisUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1.下载工具将.proto文件转换为java类
 * https://github.com/protocolbuffers/protobuf/releases protoc-3.16.0-rc-1-win64
 * protoc.exe --java_out=F:\sourceCode\JUC\com.sing.herostory\src\main\java .\GameMsgProtocol.proto
 * 2.前端
 * Cocos Creator 2.2.0
 * 开发环境：VS Code
 * 3.调试连接本地服务器
 * http://cdn0001.afrxvk.cn/hero_story/demo/step040/index.html?serverAddr=127.0.0.1:8686&userId=1
 * 4.Redis
 * 5.RocketMQ http://rocketmq.apache.org/
 *  启动broker并开启自动创建topic: mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true
 *  查看主题 ./mqadmin topicList -n 127.0.0.1:9876
 *  查看主题下的消息 ./mqadmin printMsg -n 127.0.0.1:9876 -t victor
 *  ./mqadmin clusterlist -n 127.0.0.1:9876
 *  删除主题 ./mqadmin deleteTopic -c DefaultClust -n 127.0.0.1:9876
 * 双核4G10M 3千左右连接
 * 9RocketMQ.vep
 */
public class ServerMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);


    public static void main(String[] args) {
        GameMsgRecognizer.init();
        CmdHandlerFactory.init();
        RedisUtil.init();
        MQProducer.init();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup);
        // 服务器信道的处理方式
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childOption(ChannelOption.SO_REUSEADDR, true)//快速复用端口
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);
        // 处理连接客户端
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast("socketChoose", new SocketChooseHandler())
                        // 自定义消息解码器
                        .addLast("msgDecoder", new GameMsgDecoder())
                        //自定义编码器
                        .addLast("msgEncoder", new GameMsgEncoder())
                        // 自定义消息处理器
                        .addLast("msgHandler", new GameMsgHandler());
            }
        });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 8686).sync();
            if (channelFuture.isSuccess()) {
                LOGGER.info("服务器启动成功");
            }
            // 等待服务器信道关闭（阻塞在这里）
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
