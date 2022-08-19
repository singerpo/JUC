package com.sing.tank.net;

/**
 * @author songbo
 * @since 2022-08-19
 */
public abstract class Msg {
    /**
     * 客户端收到消息处理
     */
    public abstract void handleClient();

    public abstract byte[] toBytes();

    public abstract Msg parse(byte[] bytes);

    public abstract MsgEnum getMsgEnum();
}
