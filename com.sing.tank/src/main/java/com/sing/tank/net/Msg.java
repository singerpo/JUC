package com.sing.tank.net;

/**
 * @author songbo
 * @since 2022-08-19
 */
public abstract class Msg {
    public abstract void handle();

    public abstract byte[] toBytes();
}
