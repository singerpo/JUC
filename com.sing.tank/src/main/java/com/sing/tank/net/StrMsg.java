package com.sing.tank.net;

import java.io.*;

/**
 * @author songbo
 * @since 2022-08-19
 */
public class StrMsg extends Msg {
    public String str;

    public StrMsg() {
    }

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
            dataOutputStream.writeUTF(str);
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
            this.str = dataInputStream.readUTF();
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
        return MsgEnum.MESSAGE;
    }

    @Override
    public String toString() {
        return str;
    }
}
