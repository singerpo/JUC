package com.sing.disruptor;


import com.lmax.disruptor.EventHandler;

/**1.29
 * @author songbo
 * @since 2022-05-13
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public static long count = 0;

    /**
     *
     * @param longEvent
     * @param sequence RingBuffer的序列号
     * @param endOfBatch 是否为最后一个元素
     * @throws Exception
     */
    @Override
    public void onEvent(LongEvent longEvent, long sequence, boolean endOfBatch) throws Exception {
        count ++;
        System.out.println("[" + Thread.currentThread().getName() +"] " + longEvent + " 序号:" + sequence);
    }
}
