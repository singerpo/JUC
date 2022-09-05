package com.sing.herostory.mq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 消息队列生成者
 * @author songbo
 * @since 2022-09-05
 */
public class MQProducer {
    /**
     * 生产者
     */
    private static DefaultMQProducer producer;
    private MQProducer(){

    }

    /**
     * 初始化
     */
    public static void init(){
        try {
            producer = new DefaultMQProducer("herostory");
            producer.setNamesrvAddr("127.0.0.1:9876");
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(3);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param topic 主题
     * @param msg 消息对象
     */
    public static void sendMsg(String topic,Object msg){
        if(topic == null || msg == null){
            return;
        }
        if(producer == null){
            throw new RuntimeException("MQ producer未初始化");
        }
        Message mqMsg = new Message();
        mqMsg.setTopic(topic);
        mqMsg.setBody(JSONObject.toJSONBytes(msg));
        try {
            producer.send(mqMsg);
        } catch (MQClientException | InterruptedException | RemotingException | MQBrokerException e) {
            e.printStackTrace();
        }
    }


}
