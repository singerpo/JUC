package com.sing.tank.net;

import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

/**
 * @author songbo
 * @since 2022-08-18
 */
public class TankJoinMsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 33) {
            //解决 TCP拆包 粘包的问题
            return;
        }
        TankJoinMsg tankJoinMsg = new TankJoinMsg();
        tankJoinMsg.x = byteBuf.readInt();
        tankJoinMsg.y = byteBuf.readInt();
        tankJoinMsg.directionEnum = DirectionEnum.values()[byteBuf.readInt()];
        tankJoinMsg.moving = byteBuf.readBoolean();
        tankJoinMsg.groupEnum = GroupEnum.values()[byteBuf.readInt()];
        tankJoinMsg.id = new UUID(byteBuf.readLong(), byteBuf.readLong());
        tankJoinMsg.repeat = byteBuf.readBoolean();
        list.add(tankJoinMsg);
    }
}
