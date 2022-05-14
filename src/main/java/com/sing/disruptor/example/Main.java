package com.sing.disruptor.example;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

/**
 * @author songbo
 * @since 2022-05-14
 */
public class Main {
    public static void handlerEvent(LongEvent longEvent,long sequence,boolean endOfBatch){
        System.out.println("[" + Thread.currentThread().getName() +"] " + longEvent + " 序号:" + sequence);
    }

    public static void translate(LongEvent longEvent, long sequence, ByteBuffer buffer){
        longEvent.setValue(buffer.getLong(0));
    }

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new,bufferSize, DaemonThreadFactory.INSTANCE);
        // 指定消费者
        disruptor.handleEventsWith(Main::handlerEvent);
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 99999999;true;i++){
            byteBuffer.putLong(0,i);
            ringBuffer.publishEvent(Main::translate,byteBuffer);
            Thread.sleep(500);
        }

    }
}
