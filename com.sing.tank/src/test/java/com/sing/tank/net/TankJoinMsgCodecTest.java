package com.sing.tank.net;

import cn.hutool.core.lang.Assert;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.UUID;

/**
 * @author songbo
 * @since 2022-08-18
 */
public class TankJoinMsgCodecTest {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankJoinMsg tankJoinMsg = new TankJoinMsg(5, 10, DirectionEnum.UP, false, GroupEnum.GOOD, id);
        channel.pipeline().addLast(new TankJoinMsgEncoder());

        channel.writeOutbound(tankJoinMsg);

        ByteBuf byteBuf = channel.readOutbound();
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        DirectionEnum directionEnum = DirectionEnum.values()[byteBuf.readInt()];
        boolean moving = byteBuf.readBoolean();
        GroupEnum groupEnum = GroupEnum.values()[byteBuf.readInt()];
        UUID uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());

        Assert.equals(5, x);
        Assert.equals(10, y);
        Assert.equals(DirectionEnum.UP, directionEnum);
        Assert.equals(false, moving);
        Assert.equals(GroupEnum.GOOD, groupEnum);
        Assert.equals(id, uuid);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankJoinMsg tankJoinMsg = new TankJoinMsg(5, 10, DirectionEnum.UP, false, GroupEnum.GOOD, id);
        channel.pipeline().addLast(new TankJoinMsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(tankJoinMsg.toBytes());
        channel.writeInbound(byteBuf.duplicate());

        TankJoinMsg msg = channel.readInbound();

        Assert.equals(5, msg.x);
        Assert.equals(10, msg.y);
        Assert.equals(DirectionEnum.UP, msg.directionEnum);
        Assert.equals(false, msg.moving);
        Assert.equals(GroupEnum.GOOD, msg.groupEnum);
        Assert.equals(id, msg.id);

    }
}
