package com.sing.herostory;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.sing.herostory.msg.GameMsgProtocol;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息识别器
 *
 * @author songbo
 * @since 2022-08-31
 */
public final class GameMsgRecognizer {
    /***消息代码和消息体字典 **/
    private static final Map<Integer, GeneratedMessageV3> msgCodeAndMsgBodyMap = new HashMap<>();
    /***消息类型和消息编号字典 **/
    private static final Map<Class<?>, Integer> msgClazzAndMsgCodeMap = new HashMap<>();

    private GameMsgRecognizer() {

    }

    public static void init() {
        // 获取全部内部类
        Class<?>[] innerClazzArray = GameMsgProtocol.class.getDeclaredClasses();
        for (Class<?> innerClazz : innerClazzArray) {
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClazz)) {
                continue;
            }
            String innerClazzName = innerClazz.getSimpleName();
            innerClazzName = innerClazzName.toLowerCase();

            for (GameMsgProtocol.MsgCode msgCode : GameMsgProtocol.MsgCode.values()) {
                String msgCodeName = msgCode.name();
                msgCodeName = msgCodeName.replace("_", "");
                msgCodeName = msgCodeName.toLowerCase();
                if (msgCodeName.startsWith(innerClazzName)) {
                    try {
                        Object returnObj = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);
                        msgCodeAndMsgBodyMap.put(msgCode.getNumber(), (GeneratedMessageV3) returnObj);
                        msgClazzAndMsgCodeMap.put(innerClazz, msgCode.getNumber());
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    /**
     * 获取消息构建者(解码）
     *
     * @param msgCode
     * @return
     */
    public static Message.Builder getBuilderByMsgCode(int msgCode) {
        if (msgCode < 0) {
            return null;
        }
        GeneratedMessageV3 generatedMessageV3 = msgCodeAndMsgBodyMap.get(msgCode);
        if (generatedMessageV3 == null) {
            return null;
        }
        return generatedMessageV3.newBuilderForType();
    }

    /**
     * 获取消息code（编码）
     *
     * @param msgClazz
     * @return
     */
    public static int getMsgCodeByClazz(Class msgClazz) {
        if (msgClazz == null) {
            return -1;
        }

        return msgClazzAndMsgCodeMap.get(msgClazz);
    }
}
