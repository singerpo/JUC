package com.sing.herostory.cmdHandler;

import com.sing.herostory.BroadCaster;
import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserAttackCmdHandler implements ICmdHandler<GameMsgProtocol.UserAttackCmd> {
    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserAttackCmd userAttackCmd) {
        // 获取当前用户Id
        Integer userId = (Integer) channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).get();
        if (userId == null) {
            return;
        }
        Integer targetUserId = userAttackCmd.getTargetUserId();
        // 构建攻击结果消息并群发
        GameMsgProtocol.UserAttackResult.Builder resultBuilder = GameMsgProtocol.UserAttackResult.newBuilder();
        resultBuilder.setAttackUserId(userId);
        resultBuilder.setTargetUserId(targetUserId);
        BroadCaster.broadcast(resultBuilder.build());

        // 构建用户减血结果消息并群发
        GameMsgProtocol.UserSubtractHpResult.Builder userSubtractHpBuilder = GameMsgProtocol.UserSubtractHpResult.newBuilder();
        userSubtractHpBuilder.setTargetUserId(targetUserId)
                .setSubtractHp(10);
        BroadCaster.broadcast(userSubtractHpBuilder.build());

        // 构建用户死亡结果消息并群发
        User targetUser = UserManager.getUser(targetUserId);
        if (targetUser == null) {
            return;
        }
        targetUser.setHp(targetUser.getHp() - 10);
        UserManager.addUser(targetUser);
        if (targetUser.getHp() <= 0) {
            GameMsgProtocol.UserDieResult.Builder userDieBuilder = GameMsgProtocol.UserDieResult.newBuilder();
            userDieBuilder.setTargetUserId(targetUserId);
            BroadCaster.broadcast(userDieBuilder.build());
        }
    }
}
