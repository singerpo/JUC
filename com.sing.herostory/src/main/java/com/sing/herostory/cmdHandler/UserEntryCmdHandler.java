package com.sing.herostory.cmdHandler;

import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.BroadCaster;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd> {

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserEntryCmd userEntryCmd) {
        // 获取当前用户Id
        Integer userId = (Integer) channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).get();
        if (userId == null) {
            return;
        }
        User user = UserManager.getUser(userId);
        if (user == null) {
            return;
        }

        // 构建用户入场结果并群发
        GameMsgProtocol.UserEntryResult.Builder userEntryResultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
        GameMsgProtocol.UserEntryResult userEntryResult = userEntryResultBuilder.setUserId(userId)
                .setUserName(user.getUserName())
                .setHeroAvatar(user.getHeroAvatar()).build();
        BroadCaster.broadcast(userEntryResult);
    }
}
