package com.sing.herostory.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import com.sing.herostory.util.PackageUtil;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 指令处理器工厂
 *
 * @author songbo
 * @since 2022-08-31
 */
public final class CmdHandlerFactory {
    private static final Map map = new HashMap();

    private CmdHandlerFactory() {

    }

    public static void init() {
        Set<Class<?>> clazzSet = PackageUtil.listSubClazz(CmdHandlerFactory.class.getPackage().getName(),
                true, ICmdHandler.class);
        for (Class<?> clazz : clazzSet) {
            if ((clazz.getModifiers() & Modifier.ABSTRACT) != 0) {
                continue;
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().equals("handle")) {
                    continue;
                }
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length < 2) {
                    continue;
                }
                if (parameterTypes.length < 2) {
                    continue;
                }
                if (!ChannelHandlerContext.class.isAssignableFrom(parameterTypes[0]) || !GeneratedMessageV3.class.isAssignableFrom(parameterTypes[1])) {
                    continue;
                }
            }
        }

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
