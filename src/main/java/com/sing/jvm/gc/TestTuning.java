package com.sing.jvm.gc;

import java.util.LinkedList;
import java.util.List;

/**
 * @author songbo
 * @since 2022-05-31
 */
public class TestTuning {

    public static void main(String[] args) {
       // System.out.println("HelloGC!");
        List list = new LinkedList();
        for (int i = 1; ; i++) {
            byte[] b = new byte[1024 * 1024];
            // if(i > 2000){
                System.out.println(i);
            // }
            list.add(b);
        }
    }

}
