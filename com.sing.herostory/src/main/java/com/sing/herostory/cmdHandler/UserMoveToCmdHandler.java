package com.sing.herostory.cmdHandler;

import com.sing.herostory.BroadCaster;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserMoveToCmdHandler implements ICmdHandler<GameMsgProtocol.UserMoveToCmd>{

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserMoveToCmd msg) {
        // 获取当前用户Id
        Integer userId = (Integer) channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).get();
        if (userId == null) {
            return;
        }
        GameMsgProtocol.UserMoveToCmd userMoveToCmd = msg;
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

        // 群发用户移动结果消息
        BroadCaster.broadcast(userMoveToResultBuilder.build());
    }
}
