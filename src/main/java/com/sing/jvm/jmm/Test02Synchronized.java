package com.sing.jvm.jmm;

/**
 *synchronized实现细节
 * 1.字节码层面
 *  flags: ACC_SYNCHRONIZED
 *  monitorenter monitorexit
 * 2.JVM层面
 *  C C++ 调用了操作系统提供的同步机制
 * 3.OS和硬件层面
 * lock cmpxchg xxxx 指令
 *
 */
public class Test02Synchronized {

    synchronized void m(){

    }

    void n(){
        synchronized (this){

        }
    }

    public static void main(String[] args) {

    }
}
