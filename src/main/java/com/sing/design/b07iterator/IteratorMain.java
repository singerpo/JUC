package com.sing.design.b07iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * 迭代器模式
 * 提供一种访问集合中的各个元素，而不暴露其内部表示的方法。
 * @author songbo
 * @since 2022-07-08
 */
public class IteratorMain {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add("11");
        list.iterator();

    }
}
