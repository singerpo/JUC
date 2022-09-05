package com.sing.herostory;

import com.sing.herostory.mq.MQConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排行榜应用程序
 * @author songbo
 * @since 2022-09-05
 */
public class RankApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(RankApp.class);

    public static void main(String[] args) {
        MQConsumer.init();
        LOGGER.info("排行榜应用程序启动成功");
    }
}
