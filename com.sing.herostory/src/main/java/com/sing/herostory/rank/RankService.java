package com.sing.herostory.rank;

import com.alibaba.fastjson.JSONObject;
import com.sing.herostory.async.AsyncOperationProcessor;
import com.sing.herostory.async.IAsyncOperation;
import com.sing.herostory.cmdHandler.UserLoginCmdHandler;
import com.sing.herostory.model.User;
import com.sing.herostory.util.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * 排行榜服务
 */
public class RankService {
    private static final RankService INSTANCE = new RankService();

    private RankService() {

    }

    public static RankService getInstance() {
        return INSTANCE;
    }

    /**
     * 获取排名列表
     *
     * @param callback 回调函数
     */
    public void getRank(Function<List<RankItem>, Void> callback) {
        IAsyncOperation asyncOperation = new GetRankAsyncOperation(callback);
        AsyncOperationProcessor.getInstance().process(asyncOperation);
    }

    /**
     * 刷新排行榜
     * @param winnerId 赢家Id
     * @param loserId 输家Id
     */
    public void refreshRank(int winnerId, int loserId) {
        Jedis redis = RedisUtil.getRedis();
        // 增加用户属于测试
        redis.hincrBy("user_" + winnerId, "win", 1);
        redis.hincrBy("user_" + loserId, "lose", 1);

        // 修改排行榜
        String winStr = redis.hget("user_"+winnerId,"win");
        int winInt = Integer.parseInt(winStr);
        redis.zadd("rank",winInt, String.valueOf(winnerId));
    }

    private class GetRankAsyncOperation implements IAsyncOperation {
        private Function<List<RankItem>, Void> callback;
        /**
         * 排名列表
         */
        private List<RankItem> rankItems = new ArrayList<>();

        public GetRankAsyncOperation(Function<List<RankItem>, Void> callback) {
            this.callback = callback;
        }

        @Override
        public int bindId() {
            return IAsyncOperation.super.bindId();
        }

        @Override
        public void doAsync() {
            Jedis redis = RedisUtil.getRedis();
            // 获取字符串集合
            List<Tuple> valList = redis.zrevrangeWithScores("rank", 0, 9);
            int rankId = 0;
            for (Tuple tuple : valList) {
                int userId = Integer.parseInt(tuple.getElement());
                String jsonStr = redis.hget("user_" + userId, "user");
                if (jsonStr == null || jsonStr.isEmpty()) {
                    continue;
                }
                User user = JSONObject.parseObject(jsonStr, User.class);
                RankItem rankItem = new RankItem();
                rankItem.setRankId(++rankId);
                rankItem.setUserId(userId);
                rankItem.setUserName(user.getUserName());
                rankItem.setHeroAvatar(user.getHeroAvatar());
                rankItem.setWin((int) tuple.getScore());
                rankItems.add(rankItem);
            }
        }

        @Override
        public void doFinish() {
            callback.apply(rankItems);
        }
    }
}
