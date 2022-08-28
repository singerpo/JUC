package com.sing.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author songbo
 * @since 2022-08-18
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        //第一个四位表示消息类型
        MsgEnum msgEnum = MsgEnum.values()[byteBuf.readShort()];
        //第二个四位表示消息总字节数
        short length = byteBuf.readShort();
        if (byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
            //解决 TCP拆包 粘包的问题
            return;
        }
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Msg msg = (Msg) Class.forName("com.sing.tank.net."+msgEnum.getValue()+"Msg").newInstance();
        // switch (msgEnum) {
        //     case MESSAGE:
        //         msg = new StrMsg();
        //         break;
        //     case TANK_JOIN:
        //         msg = new TankJoinMsg();
        //         break;
        //     case TANK_START_MOVING:
        //         msg = new TankStartMovingMsg();
        //         break;
        //     case TANK_STOP:
        //         msg = new TankStopMsg();
        //         break;
        // }
        msg.parse(bytes);
        list.add(msg);

    }
}
