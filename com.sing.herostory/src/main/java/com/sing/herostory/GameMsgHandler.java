package com.sing.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.sing.herostory.cmdHandler.CmdHandlerFactory;
import com.sing.herostory.cmdHandler.ICmdHandler;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * websocket消息协议
 * 前4个字节是消息头（前2个字节是消息长度，后2个字节是消息编号)
 */
public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        BroadCaster.addChannel(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 用户离线
        // 1.移除客户端信道数组
        BroadCaster.removeChannel(ctx.channel());
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (userId == null) {
            return;
        }
        // 2.移除用户信息
        UserManager.removeUser(userId);
        // 3.构建用户退场消息并群发
        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(userId);
        BroadCaster.broadcast(resultBuilder.build());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) {
        System.out.println("收到客户端消息,msgClazz=" + msg.getClass().getName() + "," + msg);
        if (msg instanceof GeneratedMessageV3) {
            MainThreadProcessor.getInstance().process(channelHandlerContext, (GeneratedMessageV3) msg);
        }
    }
}
