package com.sing.herostory;

import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket消息协议
 * 前4个字节是消息头（前2个字节是消息长度，后2个字节是消息编号)
 */
public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {
    /***客户端信道数组，必须是static **/
    private static final ChannelGroup CLIENTS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /***用户信息 **/
    private static final Map<Integer, GameMsgProtocol.WhoElseIsHereResult.UserInfo> USER_MAP = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CLIENTS.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) {
        System.out.println("收到客户端消息" + msg);
        if(msg instanceof GameMsgProtocol.UserEntryCmd){
            // 从指令对象中获取用户Id和英雄形象
            GameMsgProtocol.UserEntryCmd userEntryCmd = (GameMsgProtocol.UserEntryCmd) msg;
            int userId = userEntryCmd.getUserId();
            String heroAvatar = userEntryCmd.getHeroAvatar();

            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            GameMsgProtocol.WhoElseIsHereResult.UserInfo userInfo = userInfoBuilder.setUserId(userId)
                    .setHeroAvatar(heroAvatar).build();
            USER_MAP.put(userInfo.getUserId(),userInfo);

            // 构建结果并发送
            GameMsgProtocol.UserEntryResult.Builder userEntryResultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
            GameMsgProtocol.UserEntryResult userEntryResult = userEntryResultBuilder.setUserId(userId)
                    .setHeroAvatar(heroAvatar).build();
            CLIENTS.writeAndFlush(userEntryResult);
        }else if(msg instanceof  GameMsgProtocol.WhoElseIsHereCmd){
            GameMsgProtocol.WhoElseIsHereResult.Builder whoElseIsHereResultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();
            GameMsgProtocol.WhoElseIsHereResult whoElseIsHereResult = whoElseIsHereResultBuilder.addAllUserInfo(USER_MAP.values()).build();
            channelHandlerContext.writeAndFlush(whoElseIsHereResult);
        }
    }
}
