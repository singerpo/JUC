package com.sing.tank.net;

import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author songbo
 * @since 2022-08-18
 */
public class TankJoinMsg {
    public int x, y;
    public DirectionEnum directionEnum;
    public boolean moving;
    public GroupEnum groupEnum;
    public UUID id;
    /*** 是否重复复活 */
    public boolean repeat;

    public TankJoinMsg(BaseTank baseTank) {
        this.x = baseTank.getX();
        this.y = baseTank.getY();
        this.directionEnum = baseTank.getDirectionEnum();
        this.moving = baseTank.getMoving();
        this.groupEnum = baseTank.getGroupEnum();
        this.id = baseTank.getId();
        this.repeat = baseTank.getRepeat();
    }

    public TankJoinMsg(int x, int y, DirectionEnum directionEnum, boolean moving, GroupEnum groupEnum, UUID id, boolean repeat) {
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
        this.moving = moving;
        this.groupEnum = groupEnum;
        this.id = id;
        this.repeat = repeat;
    }

    public TankJoinMsg() {
    }

    public byte[] toBytes() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(this.x);
            dataOutputStream.writeInt(this.y);
            dataOutputStream.writeInt(this.directionEnum.ordinal());
            dataOutputStream.writeBoolean(moving);
            dataOutputStream.writeInt(this.groupEnum.ordinal());
            //高8个字节
            dataOutputStream.writeLong(this.id.getMostSignificantBits());
            //低8个字节
            dataOutputStream.writeLong(this.id.getLeastSignificantBits());
            dataOutputStream.writeBoolean(this.repeat);
            dataOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
