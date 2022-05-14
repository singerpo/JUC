package com.sing.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * lamda写法
 */
public class TestTwo {
    public static void main(String[] args) {
        int bufferSize = 1024;
        ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("disruptor-%d").build();
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, threadFactory);
        disruptor.handleEventsWith(( longEvent,  sequence,  endOfBatch)->{
            System.out.println("[" + Thread.currentThread().getName() +"] " + longEvent + " 序号:" + sequence);
        });
        // Start the Disruptor,starts all threads running
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        // 1.发布事件
        EventTranslator<LongEvent> translator1 = (longEvent, sequence1) -> longEvent.setValue(2222L);
        ringBuffer.publishEvent(translator1);

        // 2.发布事件
        EventTranslatorOneArg<LongEvent, Long> translatorOneArg = (longEvent, sequence, l) -> longEvent.setValue(l);
        ringBuffer.publishEvent(translatorOneArg, 4444L);

        // 3.发布事件
        EventTranslatorTwoArg<LongEvent, Long, Long> translatorTwoArg = (longEvent, sequence, aLong, aLong2) -> longEvent.setValue(aLong + aLong2);
        ringBuffer.publishEvent(translatorTwoArg, 4444L, 1111L);

        // 4.发布事件
        EventTranslatorThreeArg<LongEvent, Long, Long, Long> translatorThreeArg = (longEvent, sequence, aLong, aLong2, aLong3) -> longEvent.setValue(aLong + aLong2 + aLong3);
        ringBuffer.publishEvent(translatorThreeArg, 4444L, 1111L, 1111L);

        //5.发布事件
        EventTranslatorVararg<LongEvent> translatorVararg = (longEvent, sequence, objects) -> {
            long sum = 0;
            for (Object object : objects) {
                sum += (Long)object;
            }
            longEvent.setValue(sum);
        };
        ringBuffer.publishEvent(translatorVararg,4444L, 1111L, 1111L, 1111L);

        disruptor.shutdown();
    }
}
