package com.sing.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author songbo
 * @since 2022-08-18
 */
public class TankJoinMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 8) {
            return;
        }
        byteBuf.markReaderIndex();
        //第一个四位表示消息类型
        MsgEnum msgEnum = MsgEnum.values()[byteBuf.readInt()];
        //第二个四位表示消息总字节数
        int length = byteBuf.readInt();
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            //解决 TCP拆包 粘包的问题
            return;
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        switch (msgEnum) {
            case message:
                list.add(new String(bytes, "UTF-8"));
                break;
            case tankJoin:
                TankJoinMsg tankJoinMsg = new TankJoinMsg();
                tankJoinMsg.parse(bytes);
                list.add(tankJoinMsg);
                break;
        }

    }
}
