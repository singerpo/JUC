package com.sing.herostory.cmdHandler;

import com.sing.herostory.BroadCaster;
import com.sing.herostory.model.MoveState;
import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserMoveToCmdHandler implements ICmdHandler<GameMsgProtocol.UserMoveToCmd>{

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserMoveToCmd userMoveToCmd) {
        // 获取当前用户Id
        Integer userId = (Integer) channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).get();
        if (userId == null) {
            return;
        }
        // 获取移动用户
        User moveUser = UserManager.getUser(userId);
        MoveState moveState = moveUser.getMoveState();
        moveState.setFromPosX(userMoveToCmd.getMoveFromPosX());
        moveState.setFromPosY(userMoveToCmd.getMoveFromPosY());
        moveState.setToPosX(userMoveToCmd.getMoveToPosX());
        moveState.setToPosY(userMoveToCmd.getMoveToPosX());
        moveState.setStartTime(System.currentTimeMillis());

        // 构建用户移动结果消息
        GameMsgProtocol.UserMoveToResult.Builder userMoveToResultBuilder = GameMsgProtocol.UserMoveToResult.newBuilder();
        userMoveToResultBuilder.setMoveUserId(userId);
        userMoveToResultBuilder.setMoveFromPosX(moveState.getFromPosX());
        userMoveToResultBuilder.setMoveFromPosY(moveState.getFromPosY());
        userMoveToResultBuilder.setMoveToPosX(moveState.getToPosX());
        userMoveToResultBuilder.setMoveToPosY(moveState.getToPosY());
        userMoveToResultBuilder.setMoveStartTime(moveState.getStartTime());

        // 群发用户移动结果消息
        BroadCaster.broadcast(userMoveToResultBuilder.build());
    }
}
