package com.sing.javassist;

import java.sql.ResultSet;

/**
 * 抽象实体助手
 * @author songbo
 * @since 2022-09-02
 */
public abstract class AbstractEntityHelper {
    /**
     * 创建实体
     * @param resultSet
     * @return
     */
    public abstract Object create(ResultSet resultSet);
}
