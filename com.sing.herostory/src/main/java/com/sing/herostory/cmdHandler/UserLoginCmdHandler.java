package com.sing.herostory.cmdHandler;

import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

public class UserLoginCmdHandler implements ICmdHandler<GameMsgProtocol.UserLoginCmd> {
    private static Integer maxUserId = 0;

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserLoginCmd userLoginCmd) {
        String userName = userLoginCmd.getUserName();
        String password = userLoginCmd.getPassword();
        // 从数据库查询省略
        User user = userLogin(userName, password);

        // 存储用户信息
        UserManager.addUser(user);
        // 将userId绑定到channel属性
        channelHandlerContext.channel().attr(AttributeKey.valueOf("userId")).set(user.getUserId());

        // 构建用户登录结果
        GameMsgProtocol.UserLoginResult.Builder loginResultBuilder = GameMsgProtocol.UserLoginResult.newBuilder();
        loginResultBuilder.setUserId(user.getUserId());
        loginResultBuilder.setUserName(user.getUserName());
        loginResultBuilder.setHeroAvatar(user.getHeroAvatar());
        channelHandlerContext.writeAndFlush(loginResultBuilder.build());
    }

    private User userLogin(String userName, String password) {
        maxUserId += 1;
        User user = new User();
        user.setUserId(maxUserId);
        user.setUserName(userName);
        int userCase = maxUserId % 3;
        switch (userCase) {
            case 1:
                user.setHeroAvatar("Hero_Hammer");
                break;
            case 2:
                user.setHeroAvatar("Hero_Shaman");
                break;
            case 0:
                user.setHeroAvatar("Hero_Skeleton");
                break;
        }
        return user;
    }
}
