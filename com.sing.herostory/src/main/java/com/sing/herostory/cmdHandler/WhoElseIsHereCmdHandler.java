package com.sing.herostory.cmdHandler;

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
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            userInfoBuilder.setUserId(user.getUserId())
                    .setHeroAvatar(user.getHeroAvatar());
            whoElseIsHereResultBuilder.addUserInfo(userInfoBuilder.build());
        }

        GameMsgProtocol.WhoElseIsHereResult whoElseIsHereResult = whoElseIsHereResultBuilder.build();
        channelHandlerContext.writeAndFlush(whoElseIsHereResult);
    }

}
