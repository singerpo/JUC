package com.sing.disruptor;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 1.55
 */
public class ExampleOne {
    public static void main(String[] args) {
        LongEventFactory eventFactory = new LongEventFactory();
        int bufferSize = 1024;
        ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("disruptor-%d").build();
        Disruptor<LongEvent> disruptor = new Disruptor<>(eventFactory,bufferSize, threadFactory);
        disruptor.handleEventsWith(new LongEventHandler());
        // Start the Disruptor,starts all threads running
        disruptor.start();
        // 发布事件
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(1111L);
        }finally {
            ringBuffer.publish(sequence);
        }

        // 发布事件
        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence) {
                longEvent.setValue(2222L);
            }
        };
        ringBuffer.publishEvent(translator1);
        // lamda表达式
        ringBuffer.publishEvent((longEvent,seq)->{
            longEvent.setValue(3333L);
        });

        // 发布事件
        EventTranslatorOneArg<LongEvent,Long> translatorOneArg = new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence, Long l) {
                longEvent.setValue(l);
            }
        };
        ringBuffer.publishEvent(translatorOneArg,4444L);

        // 发布事件
        EventTranslatorTwoArg<LongEvent,Long,Long> translatorTwoArg = new EventTranslatorTwoArg<LongEvent, Long, Long>() {
            @Override
            public void translateTo(LongEvent longEvent, long sequence, Long aLong, Long aLong2) {
                longEvent.setValue(aLong + aLong2);
            }
        };
        ringBuffer.publishEvent(translatorTwoArg,4444L,1111L);
    }
}
