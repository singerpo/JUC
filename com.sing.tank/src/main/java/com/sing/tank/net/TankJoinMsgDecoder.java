package com.sing.tank.net;

import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

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
        //第一个四位表示消息类型
        int type = byteBuf.readInt();
        //第二个四位表示消息总字节数
        int length = byteBuf.readInt();
        if (byteBuf.readableBytes() < length) {
            //解决 TCP拆包 粘包的问题
            return;
        }
        switch (type) {
            case 1:
                byte[] bytes = new byte[length];
                byteBuf.readBytes(bytes);
                list.add(new String(bytes,"UTF-8"));
                break;
            case 2:
                TankJoinMsg tankJoinMsg = new TankJoinMsg();
                tankJoinMsg.x = byteBuf.readInt();
                tankJoinMsg.y = byteBuf.readInt();
                tankJoinMsg.directionEnum = DirectionEnum.values()[byteBuf.readInt()];
                tankJoinMsg.moving = byteBuf.readBoolean();
                tankJoinMsg.groupEnum = GroupEnum.values()[byteBuf.readInt()];
                tankJoinMsg.id = new UUID(byteBuf.readLong(), byteBuf.readLong());
                tankJoinMsg.repeat = byteBuf.readBoolean();
                list.add(tankJoinMsg);
                break;
        }

    }
}
