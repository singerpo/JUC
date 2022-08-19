package com.sing.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author songbo
 * @since 2022-08-18
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(msg.getMsgEnum().ordinal());
        byte[] bytes = msg.toBytes();
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
