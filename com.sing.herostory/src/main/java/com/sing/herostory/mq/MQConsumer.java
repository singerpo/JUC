package com.sing.herostory.mq;

import com.alibaba.fastjson.JSONObject;
import com.sing.herostory.rank.RankService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消息队列消费者
 * @author songbo
 * @since 2022-09-05
 */
public class MQConsumer {
    private static DefaultMQPushConsumer consumer;
    private MQConsumer(){

    }

    public static void init(){
        consumer = new DefaultMQPushConsumer("herostory");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        try {
            consumer.subscribe("victor","*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    for (MessageExt messageExt : list) {
                        VictorMsg victorMsg = JSONObject.parseObject(messageExt.getBody(),VictorMsg.class);
                        // 刷新排行榜
                        RankService.getInstance().refreshRank(victorMsg.getWinnerId(),victorMsg.getLoserId());
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }
}
