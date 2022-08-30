package com.sing.herostory;

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
    /***客户端信道数组，必须是static **/
    private static final ChannelGroup CLIENTS = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /***用户信息 **/
    private static final Map<Integer, GameMsgProtocol.WhoElseIsHereResult.UserInfo> USER_MAP = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CLIENTS.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 用户离线
        // 1.移除客户端信道数组
        CLIENTS.remove(ctx.channel());
        Integer userId = (Integer) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (userId == null) {
            return;
        }
        // 2.移除用户信息
        USER_MAP.remove(userId);
        // 3.构建用户退场消息并群发
        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(userId);
        CLIENTS.writeAndFlush(resultBuilder.build());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) {
        System.out.println("收到客户端消息" + msg);
        if (msg instanceof GameMsgProtocol.UserEntryCmd) {
            // 用户入场消息
            GameMsgProtocol.UserEntryCmd userEntryCmd = (GameMsgProtocol.UserEntryCmd) msg;
            int userId = userEntryCmd.getUserId();
            String heroAvatar = userEntryCmd.getHeroAvatar();

            // 存储用户消息到USER_MAP
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            GameMsgProtocol.WhoElseIsHereResult.UserInfo userInfo = userInfoBuilder.setUserId(userId)
                    .setHeroAvatar(heroAvatar).build();
            USER_MAP.put(userInfo.getUserId(), userInfo);

            // 将userId绑定到channel属性
            channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).set(userId);

            // 构建用户入场结果并群发
            GameMsgProtocol.UserEntryResult.Builder userEntryResultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
            GameMsgProtocol.UserEntryResult userEntryResult = userEntryResultBuilder.setUserId(userId)
                    .setHeroAvatar(heroAvatar).build();
            CLIENTS.writeAndFlush(userEntryResult);
        } else if (msg instanceof GameMsgProtocol.WhoElseIsHereCmd) {
            // 构建还有谁在场结果消息并发送
            GameMsgProtocol.WhoElseIsHereResult.Builder whoElseIsHereResultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();
            GameMsgProtocol.WhoElseIsHereResult whoElseIsHereResult = whoElseIsHereResultBuilder.addAllUserInfo(USER_MAP.values()).build();
            channelHandlerContext.writeAndFlush(whoElseIsHereResult);
        } else if (msg instanceof GameMsgProtocol.UserMoveToCmd) {
            // 获取当前用户Id
            Integer userId = (Integer) channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).get();
            if (userId == null) {
                return;
            }
            GameMsgProtocol.UserMoveToCmd userMoveToCmd = (GameMsgProtocol.UserMoveToCmd) msg;
            // 起始位置 X
            float moveFromPosX = userMoveToCmd.getMoveFromPosX();
            // 起始位置 Y
            float moveFromPosY = userMoveToCmd.getMoveFromPosY();
            // 移动到位置 X
            float moveToPosX = userMoveToCmd.getMoveToPosX();
            // 移动到位置 Y
            float moveToPosY = userMoveToCmd.getMoveToPosY();
            // 构建用户移动结果消息
            GameMsgProtocol.UserMoveToResult.Builder userMoveToResultBuilder = GameMsgProtocol.UserMoveToResult.newBuilder();
            userMoveToResultBuilder.setMoveUserId(userId);
            userMoveToResultBuilder.setMoveFromPosX(moveFromPosX);
            userMoveToResultBuilder.setMoveFromPosY(moveFromPosY);
            userMoveToResultBuilder.setMoveToPosX(moveToPosX);
            userMoveToResultBuilder.setMoveToPosY(moveToPosY);

            //更新用户信息
            GameMsgProtocol.WhoElseIsHereResult.UserInfo userInfo = USER_MAP.get(userId);
            if (userInfo == null) {
                return;
            }
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            userInfoBuilder.setUserId(userId);
            userInfoBuilder.setHeroAvatar(userInfo.getHeroAvatar());
            userInfoBuilder.setMoveState(GameMsgProtocol.WhoElseIsHereResult.UserInfo.MoveState.newBuilder()
                    .setFromPosX(moveFromPosX)
                    .setFromPosY(moveFromPosY)
                    .setToPosX(moveToPosX)
                    .setToPosY(moveFromPosY)
                    .build()
            );
            USER_MAP.put(userId, userInfoBuilder.build());

            // 群发用户移动结果消息
            CLIENTS.writeAndFlush(userMoveToResultBuilder.build());
        }
    }
}
