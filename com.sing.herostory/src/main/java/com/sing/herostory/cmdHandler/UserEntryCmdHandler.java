package com.sing.herostory.cmdHandler;

import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.BroadCaster;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd>{

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserEntryCmd msg) {
        // 用户入场消息
        GameMsgProtocol.UserEntryCmd userEntryCmd = msg;
        int userId = userEntryCmd.getUserId();
        String heroAvatar = userEntryCmd.getHeroAvatar();

        // 存储用户信息
        User user = new User();
        user.setUserId(userId);
        user.setHeroAvatar(heroAvatar);
        UserManager.addUser(user);

        // 将userId绑定到channel属性
        channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).set(userId);

        // 构建用户入场结果并群发
        GameMsgProtocol.UserEntryResult.Builder userEntryResultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
        GameMsgProtocol.UserEntryResult userEntryResult = userEntryResultBuilder.setUserId(userId)
                .setHeroAvatar(heroAvatar).build();
        BroadCaster.broadcast(userEntryResult);
    }
}
