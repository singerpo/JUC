package com.sing.design.b05visitor.asm;

import org.objectweb.asm.ClassWriter;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author songbo
 * @since 2022-08-04
 */
public class ClassWriterTest {

    public static void main(String[] args) {
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                null
        );
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, -1).visitEnd();
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, 0).visitEnd();
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, 1).visitEnd();
        classWriter.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();
        MyClassLoader myClassLoader = new MyClassLoader();
        Class clazz = myClassLoader.defineClass("pkg.Comparable",bytes);
        System.out.println(clazz.getMethods()[0].getName());

    }
}
