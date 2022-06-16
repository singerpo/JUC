package com.sing.design.d03singleton;

/**
 * 枚举式（没有构造方法）
 * 不仅可以解决线程同步，还可以防止反序列化
 */
public enum  Mgr04 {
    INSTANCE;
}
