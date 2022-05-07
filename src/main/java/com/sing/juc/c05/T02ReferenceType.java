package com.sing.juc.c05;

import java.io.IOException;
import java.lang.ref.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 强引用(当没有指向引用时会回收)
 * 软引用(当内存不够时会回收)
 * --大对象的缓存
 * --常用对象的缓存
 * 弱引用(遇到gc就会回收)
 * --缓存，没有容器引用指向的时候就需要清除的缓存
 * --ThreadLocal
 * --WeakHashMap
 * 虚引用
 * --管理堆外内存
 *
 * @author songbo
 * @since 2022-05-06
 */
public class T02ReferenceType {

    @Override
    protected void finalize() {
        System.out.println("finalize");
    }

    static class T01StrongReference {
        static void strong() throws IOException {
            T02ReferenceType t02ReferenceType = new T02ReferenceType();
            t02ReferenceType = null;
            System.gc();
            System.in.read();
        }
    }

    static class T02SoftReference {
        static void soft() throws IOException, InterruptedException {
            SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
            // m = null;
            System.out.println(m.get());
            System.gc();
            Thread.sleep(500);
            System.out.println(m.get());

            // 再分配一个数组，heap将装不下，这时候系统会垃圾回收，先回收一次，如果不够，会把软引用干掉
            byte[] b = new byte[1024 * 1024 * 15];
            System.out.println(m.get());
        }
    }

    static class T03WeakReference {
        static void weak() throws IOException {
            WeakReference<T02ReferenceType> m = new WeakReference<>(new T02ReferenceType());
            System.out.println(m.get());
            System.gc();
            System.out.println(m.get());

            ThreadLocal<T02ReferenceType> threadLocal = new ThreadLocal<>();
            threadLocal.set(new T02ReferenceType());
            threadLocal.remove();
        }
    }

    static class T04PhantomReference {
        private static final List<Object> LIST = new LinkedList<>();
        private static final ReferenceQueue<T02ReferenceType> QUEUE = new ReferenceQueue<>();
        static void phantom(){
            PhantomReference<T02ReferenceType> phantomReference = new PhantomReference<>(new T02ReferenceType(),QUEUE);
            new Thread(()->{
                while (true){
                    System.out.println(phantomReference.get());
                    LIST.add(new byte[1024*1024]);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            new Thread(()->{
                while (true){
                    Reference poll = QUEUE.poll();
                    if(poll != null){
                        System.out.println("---虚引用对象被JVM回收了----" + poll);
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // 强引用：当没有指向引用时会回收
        // T01StrongReference.strong();

        // 软引用: 当内存不够时会回收（-Xms20M -Xmx20M）
        // T02SoftReference.soft();

        // 弱引用：遇到gc就会回收
        // T03WeakReference.weak();

        // 虚引用：在任何时候都可能被垃圾回收器回收，它不能单独使用也不能通过它访问对象，虚引用必须和引用队列（ReferenceQueue）联合使用
        T04PhantomReference.phantom();
    }
}
