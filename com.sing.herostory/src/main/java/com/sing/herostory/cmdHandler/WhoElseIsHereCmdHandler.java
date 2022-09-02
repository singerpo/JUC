package com.sing.herostory.cmdHandler;

import com.sing.herostory.model.MoveState;
import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;

public class WhoElseIsHereCmdHandler implements ICmdHandler<GameMsgProtocol.WhoElseIsHereCmd>{

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.WhoElseIsHereCmd msg) {
        // 构建还有谁在场结果消息并发送
        GameMsgProtocol.WhoElseIsHereResult.Builder whoElseIsHereResultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();
        for (User user : UserManager.listUser()) {
            if (user == null){
                continue;
            }
            // 在这里构建每一个用户的信息
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            userInfoBuilder.setUserId(user.getUserId())
                    .setHeroAvatar(user.getHeroAvatar());

            // 获取移动状态
            MoveState moveState = user.getMoveState();
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.MoveState.Builder moveStateBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.MoveState.newBuilder();
            moveStateBuilder.setFromPosX(moveState.getFromPosX());
            moveStateBuilder.setFromPosY(moveState.getFromPosY());
            moveStateBuilder.setToPosX(moveState.getToPosX());
            moveStateBuilder.setToPosY(moveState.getToPosY());
            moveStateBuilder.setStartTime(moveState.getStartTime());
            // 将移动状态设置到用户
            userInfoBuilder.setMoveState(moveStateBuilder.build());

            whoElseIsHereResultBuilder.addUserInfo(userInfoBuilder.build());
        }

        GameMsgProtocol.WhoElseIsHereResult whoElseIsHereResult = whoElseIsHereResultBuilder.build();
        channelHandlerContext.writeAndFlush(whoElseIsHereResult);
    }

}
