package com.sing.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 *
 */
public class TestOne {
    public static void main(String[] args) {
        LongEventFactory eventFactory = new LongEventFactory();
        int bufferSize = 1024;
        ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("disruptor-%d").build();
        Disruptor<LongEvent> disruptor = new Disruptor<>(eventFactory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new LongEventHandler());
        // Start the Disruptor,starts all threads running
        disruptor.start();
        // 1.发布事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(1111L);
        } finally {
            ringBuffer.publish(sequence);
        }

        // 2.发布事件
        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence) {
                longEvent.setValue(2222L);
            }
        };
        ringBuffer.publishEvent(translator1);

        // 3.发布事件
        EventTranslatorOneArg<LongEvent, Long> translatorOneArg = new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence, Long l) {
                longEvent.setValue(l);
            }
        };
        ringBuffer.publishEvent(translatorOneArg, 4444L);

        // 4.发布事件
        EventTranslatorTwoArg<LongEvent, Long, Long> translatorTwoArg = new EventTranslatorTwoArg<LongEvent, Long, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence, Long aLong, Long aLong2) {
                longEvent.setValue(aLong + aLong2);
            }
        };
        ringBuffer.publishEvent(translatorTwoArg, 4444L, 1111L);

        // 5.发布事件
        EventTranslatorThreeArg<LongEvent, Long, Long, Long> translatorThreeArg = new EventTranslatorThreeArg<LongEvent, Long, Long, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence, Long aLong, Long aLong2, Long aLong3) {
                longEvent.setValue(aLong + aLong2 + aLong3);
            }
        };
        ringBuffer.publishEvent(translatorThreeArg, 4444L, 1111L, 1111L);

        //6.发布事件
        EventTranslatorVararg<LongEvent> translatorVararg = new EventTranslatorVararg<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence, Object... objects) {
                long sum = 0;
                for (Object object : objects) {
                    sum += (Long)object;
                }
                longEvent.setValue(sum);
            }
        };
        ringBuffer.publishEvent(translatorVararg,4444L, 1111L, 1111L, 1111L);

        disruptor.shutdown();
    }
}
