package com.sing.jvm.gc;

/**
 * 栈溢出：java.lang.StackOverFflowError
 */
public class StackOverFlow {

    public static void main(String[] args) {
        int i = 0;
        m(i);
    }

    static void m( int i) {
        System.out.println(i++);
        m(i);
    }
}
