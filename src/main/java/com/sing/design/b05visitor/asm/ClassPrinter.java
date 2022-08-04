package com.sing.design.b05visitor.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author songbo
 * @since 2022-08-04
 */
public class ClassPrinter extends ClassVisitor {
    public ClassPrinter() {
        super(ASM4);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + "{");
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println("  " +descriptor+" "+ name);
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("  " + name + descriptor);
        return null;
    }

    @Override
    public void visitEnd() {
        System.out.println("}");
    }

    public static void main(String[] args) throws IOException {
        ClassPrinter classPrinter = new ClassPrinter();
        ClassReader classReader = new ClassReader("java.lang.Runnable");
        classReader.accept(classPrinter,0);
    }
}
