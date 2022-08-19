package com.sing.tank.net;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author songbo
 * @since 2022-08-19
 */
public class StrMsg extends Msg {
    public static final int type = 1;
    public String str;

    public StrMsg(String str) {
        this.str = str;
    }

    @Override
    public void handleClient() {

    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream byteArrayOutputStream = null;
        DataOutputStream dataOutputStream = null;
        byte[] bytes = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeInt(type);
            dataOutputStream.writeInt(str.getBytes("UTF-8").length);
            dataOutputStream.write(str.getBytes("UTF-8"));
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

    }

    @Override
    public MsgEnum getMsgEnum() {
        return MsgEnum.message;
    }
}
