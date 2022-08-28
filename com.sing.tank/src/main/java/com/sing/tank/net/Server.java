package com.sing.tank.net;

import com.sing.tank.TankFrame;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author songbo
 * @since 2022-08-16
 */
public class Server {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private ServerFrame serverFrame;
    private boolean mainStatus = true;
    private boolean otherStatus = true;
    private final Map<String, Integer> mainMap = new ConcurrentHashMap<>();

    public void startServer(ServerFrame serverFrame) {
        this.serverFrame = serverFrame;
        // 只负责连接
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //禁用TCP-Nagle算法
                    .option(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            ChannelPipeline channelPipeline = socketChannel.pipeline();
                            channelPipeline.addLast(new MsgEncoder())
                                    .addLast(new MsgDecoder());
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
        public void channelActive(ChannelHandlerContext ctx) {
            Server.clients.add(ctx.channel());
            TankJoinMsg tankJoinMsg = null;
            int obstacleSize = GameModel.getInstance().getObstacleSize();
            if (mainStatus) {
                tankJoinMsg = new TankJoinMsg(TankFrame.GAME_WIDTH / 2 + obstacleSize / 2 + obstacleSize + 1, TankFrame.GAME_HEIGHT - obstacleSize, DirectionEnum.UP, false, GroupEnum.GOOD, UUID.randomUUID(), true);
                mainStatus = false;
                mainMap.put(ctx.channel().id().asLongText(), 1);
            } else if (otherStatus) {
                tankJoinMsg = new TankJoinMsg(TankFrame.GAME_WIDTH / 2 - obstacleSize / 2 - obstacleSize - obstacleSize - 1, TankFrame.GAME_HEIGHT - obstacleSize, DirectionEnum.UP, false, GroupEnum.GOOD, UUID.randomUUID(), true);
                otherStatus = false;
                mainMap.put(ctx.channel().id().asLongText(), 2);
            }
            if (tankJoinMsg != null) {
                ctx.writeAndFlush(tankJoinMsg);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            serverFrame.updateClientMsg(msg.toString());
            Server.clients.writeAndFlush(msg);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            // 删除出异常的客户端channel并关闭
            Server.clients.remove(ctx.channel());
            int mainMapValue = mainMap.get(ctx.channel().id().asLongText());
            switch (mainMapValue) {
                case 1:
                    mainStatus = true;
                    break;
                case 2:
                    otherStatus = true;
                    break;
            }
            ctx.close();

        }
    }

}



