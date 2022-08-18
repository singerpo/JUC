package com.sing.tank.net;

import com.sing.tank.TankFrame;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.UUID;

/**
 * @author songbo
 * @since 2022-08-16
 */
public class Server {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private ServerFrame serverFrame;
    private int main = 1;

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
                            channelPipeline.addLast(new TankJoinMsgEncoder())
                                    .addLast(new TankJoinMsgDecoder())
                                    .addLast(new ServerChildHandler());

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
            System.out.println(msg);
            if (msg instanceof ByteBuf) {
                ByteBuf byteBuf = (ByteBuf) msg;
                byte[] bytes = new byte[byteBuf.readableBytes()];
                // byteBuf.readBytes(bytes);
                byteBuf.getBytes(byteBuf.readerIndex(), bytes);
                String clientMsg = new String(bytes, "UTF-8");
                if ("come".equals(clientMsg)) {
                    int obstacleSize = GameModel.getInstance().getObstacleSize();
                    TankJoinMsg tankJoinMsg;
                    if (Server.this.main == 1) {
                        tankJoinMsg = new TankJoinMsg(TankFrame.GAME_WIDTH / 2 + obstacleSize / 2 + obstacleSize + 1, TankFrame.GAME_HEIGHT - obstacleSize, DirectionEnum.UP, false, GroupEnum.GOOD, UUID.randomUUID(), true);
                    } else {
                        tankJoinMsg = new TankJoinMsg(TankFrame.GAME_WIDTH / 2 - obstacleSize / 2 - obstacleSize - obstacleSize - 1, TankFrame.GAME_HEIGHT - obstacleSize, DirectionEnum.UP, false, GroupEnum.GOOD, UUID.randomUUID(), true);
                    }
                    Server.clients.writeAndFlush(tankJoinMsg);
                    Server.this.main++;
                }
                getServerFrame().updateClientMsg(clientMsg);
            } else {
                getServerFrame().updateClientMsg(msg.toString());
            }
            Server.clients.writeAndFlush(msg);
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



