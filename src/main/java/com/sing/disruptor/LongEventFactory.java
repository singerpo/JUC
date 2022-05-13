package com.sing.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author songbo
 * @since 2022-05-13
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
