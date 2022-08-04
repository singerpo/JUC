package com.sing.design.b05visitor.asm;

/**
 * @author songbo
 * @since 2022-08-04
 */
public class MyClassLoader extends ClassLoader {
    public Class defineClass(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }

}
