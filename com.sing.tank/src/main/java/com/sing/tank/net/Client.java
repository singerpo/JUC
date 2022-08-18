package com.sing.tank.net;

import com.sing.netty.chat.ClientFrame;
import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.facade.GameModel;
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

    public void connect() {
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    /**
     * 向服务端发送消息
     *
     * @param msg 消息
     */
    public void sendMsg(String msg) {
        ByteBuf byteBuf = null;
        try {
            byteBuf = Unpooled.copiedBuffer(msg.getBytes("UTF-8"));
            this.getChannelFuture().channel().writeAndFlush(byteBuf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭客户端
     */
    public void closeClient() {
        this.sendMsg("b_bye_b");
    }

    class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline channelPipeline = socketChannel.pipeline();
            channelPipeline.addLast(new TankJoinMsgEncoder())
                    .addLast(new TankJoinMsgDecoder())
                    .addLast(new ClientChildHandler());
        }
    }

    class ClientChildHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg tankJoinMsg) throws Exception {
            if (GameModel.getInstance().getMainTank() != null && tankJoinMsg.id.equals(GameModel.getInstance().getMainTank().getId())) {
                return;
            }
            System.out.println(tankJoinMsg);
            BaseTank baseTank = GameModel.getInstance().getGameFactory().createTank(tankJoinMsg.x, tankJoinMsg.y, tankJoinMsg.directionEnum, tankJoinMsg.groupEnum, tankJoinMsg.repeat);
            GameModel.getInstance().add(baseTank);
            if (GameModel.getInstance().getMainTank() != null) {
                GameModel.getInstance().setMainTank(baseTank);
            }
            ctx.writeAndFlush(new TankJoinMsg(GameModel.getInstance().getMainTank()));
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            ctx.writeAndFlush(new TankJoinMsg(GameModel.getInstance().getMainTank()));
            ByteBuf byteBuf = Unpooled.copiedBuffer("come".getBytes("UTF-8"));
            ctx.writeAndFlush(byteBuf);
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

