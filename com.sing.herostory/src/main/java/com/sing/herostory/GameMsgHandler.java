package com.sing.herostory;

import com.sing.herostory.cmdHandler.UserEntryCmdHandler;
import com.sing.herostory.cmdHandler.UserMoveToCmdHandler;
import com.sing.herostory.cmdHandler.WhoElseIsHereCmdHandler;
import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.BroadCaster;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        System.out.println("收到客户端消息" + msg);
        if (msg instanceof GameMsgProtocol.UserEntryCmd) {
            new UserEntryCmdHandler().handle(channelHandlerContext, (GameMsgProtocol.UserEntryCmd) msg);
        } else if (msg instanceof GameMsgProtocol.WhoElseIsHereCmd) {
            new WhoElseIsHereCmdHandler().handle(channelHandlerContext, (GameMsgProtocol.WhoElseIsHereCmd) msg);
        } else if (msg instanceof GameMsgProtocol.UserMoveToCmd) {
           new UserMoveToCmdHandler().handle(channelHandlerContext, (GameMsgProtocol.UserMoveToCmd) msg);
        }
    }




}
