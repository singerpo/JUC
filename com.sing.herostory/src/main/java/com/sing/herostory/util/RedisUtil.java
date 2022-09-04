package com.sing.herostory.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis工具类
 */
public final class RedisUtil {
    /**
     * Redis连接池
     */
    private static JedisPool jedisPool = null;
    private RedisUtil(){

    }

    /**
     * 初始化
     */
    public static void init(){
        try {
            jedisPool = new JedisPool("127.0.0.1",6379);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得Redis实例
     * @return
     */
    public static Jedis getRedis(){
        if(jedisPool == null){
            throw new RuntimeException("jedisPool 未初始化");
        }
        Jedis jedis = jedisPool.getResource();
//        jedis.auth("root");
        return jedis;
    }
}
