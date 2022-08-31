package com.sing.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;

/**
 * 指令处理器工厂
 *
 * @author songbo
 * @since 2022-08-31
 */
public final class CmdHandlerFactory {
    private CmdHandlerFactory() {

    }

    public static ICmdHandler<? extends GeneratedMessageV3> create(Class<?> clazz) {
        String cmdHandlerClazz = "com.sing.herostory.cmdHandler." + clazz.getSimpleName() + "Handler";
        try {
            return (ICmdHandler<? extends GeneratedMessageV3>) Class.forName(cmdHandlerClazz).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
