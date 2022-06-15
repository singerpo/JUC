package com.sing.jvm.gc;

/**
 * lamda表达式会产生内存类Class,过多时则造成方法区溢出
 *
 */
public class LambdaGC {
    public static void main(String[] args) {
        for (; ; ) {
            I i = C::n;
        }
    }

    public interface I {
        void m();
    }

    public static class C {
        static void n() {
            System.out.println("Hello");
        }
    }
}
