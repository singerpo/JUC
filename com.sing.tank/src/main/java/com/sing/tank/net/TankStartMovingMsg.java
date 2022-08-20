package com.sing.tank.net;

import com.sing.tank.abstractfactory.BaseTank;
import com.sing.tank.abstractfactory.GameObject;
import com.sing.tank.enums.DirectionEnum;
import com.sing.tank.enums.GroupEnum;
import com.sing.tank.facade.GameModel;

import java.io.*;
import java.util.UUID;

/**
 * @author songbo
 * @since 2022-08-18
 */
public class TankStartMovingMsg extends Msg {
    public UUID id;
    public int x, y;
    public DirectionEnum directionEnum;


    public TankStartMovingMsg(BaseTank baseTank) {
        this.id = baseTank.getId();
        this.x = baseTank.getX();
        this.y = baseTank.getY();
        this.directionEnum = baseTank.getDirectionEnum();
    }

    public TankStartMovingMsg(int x, int y, DirectionEnum directionEnum, UUID id) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.directionEnum = directionEnum;
    }

    public TankStartMovingMsg() {
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            //高8个字节
            dataOutputStream.writeLong(this.id.getMostSignificantBits());
            //低8个字节
            dataOutputStream.writeLong(this.id.getLeastSignificantBits());
            dataOutputStream.writeInt(this.x);
            dataOutputStream.writeInt(this.y);
            dataOutputStream.writeInt(this.directionEnum.ordinal());
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

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        DataInputStream dataInputStream = null;

        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            dataInputStream = new DataInputStream(byteArrayInputStream);
            this.id = new UUID(dataInputStream.readLong(), dataInputStream.readLong());
            this.x = dataInputStream.readInt();
            this.y = dataInputStream.readInt();
            this.directionEnum = DirectionEnum.values()[dataInputStream.readInt()];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgEnum getMsgEnum() {
        return MsgEnum.TANK_START_MOVING;
    }


    @Override
    public void handleClient() {
        if(this.id.equals(GameModel.getInstance().getMainTank().getId())){
            return;
        }
        BaseTank baseTank = (BaseTank) GameModel.getInstance().getGameObjectMap().get(this.id);
        if(baseTank != null){
            baseTank.setMoving(true);
            baseTank.setX(this.x);
            baseTank.setY(this.y);
            baseTank.setDirectionEnum(this.directionEnum);
        }
    }

    @Override
    public String toString() {
        return "TankStartMovingMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", directionEnum=" + directionEnum +
                '}';
    }
}
