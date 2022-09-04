package com.sing.herostory.cmdHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sing.herostory.async.AsyncOperationProcessor;
import com.sing.herostory.async.IAsyncOperation;
import com.sing.herostory.model.User;
import com.sing.herostory.model.UserManager;
import com.sing.herostory.msg.GameMsgProtocol;
import com.sing.herostory.util.RedisUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class UserLoginCmdHandler implements ICmdHandler<GameMsgProtocol.UserLoginCmd> {
    private static AtomicInteger maxUserId = new AtomicInteger();

    @Override
    public void handle(ChannelHandlerContext channelHandlerContext, GameMsgProtocol.UserLoginCmd userLoginCmd) {
        String userName = userLoginCmd.getUserName();
        String password = userLoginCmd.getPassword();
        // 从数据库查询省略
        userLogin(userName, password, (user) -> {
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
            return null;
        });


    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @param callback 回调函数
     */
    private void userLogin(String userName, String password, Function<User, Void> callback) {
        IAsyncOperation asyncOperation = new UserLoginAsyncOperation(userName, password, callback);
        AsyncOperationProcessor.getInstance().process(asyncOperation);
    }

    private class UserLoginAsyncOperation implements IAsyncOperation {
        private String userName;
        private String password;
        private User user;
        private Function<User, Void> callback;

        public UserLoginAsyncOperation(String userName, String password, Function<User, Void> callback) {
            this.userName = userName;
            this.password = password;
            this.callback = callback;

        }

        /**
         * 更新Redis中的用户信息
         * @param user
         */
        private void updateUserInRedis(User user) {
            Jedis redis = RedisUtil.getRedis();
            String userJson = JSONObject.toJSONString(user);
            redis.hset("user_"+user.getUserId(),"user",userJson);

        }

        @Override
        public void doAsync() {
            maxUserId.incrementAndGet();
            user = new User();
            user.setUserId(maxUserId.get());
            user.setUserName(userName);
            int userCase = maxUserId.get() % 3;
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
            this.updateUserInRedis(user);
        }

        @Override
        public void doFinish() {
            callback.apply(user);
        }

        @Override
        public int bindId() {
            return userName.charAt(userName.length() - 1);
        }


    }
}
