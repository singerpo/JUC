package com.sing.design.b05visitor.asm;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * @author songbo
 * @since 2022-08-04
 */
public class ClassTransformerTest {
    public static void main(String[] args) throws IOException {
        ClassReader classReader = new ClassReader(
                ClassTransformerTest.class.getClassLoader().getResourceAsStream("com/sing/xx.class")
        );
        ClassWriter classWriter = new ClassWriter(0);
        ClassVisitor classVisitor = new ClassVisitor(ASM4, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(ASM4, methodVisitor) {
                    @Override
                    public void visitCode() {
                        visitMethodInsn(INVOKESTATIC, "TimeProxy", "before", "()V", false);
                        super.visitCode();
                    }
                };
            }
        };

        classReader.accept(classVisitor, 0);
        byte[] bytes = classWriter.toByteArray();

        String path = "E:/com/sing/asm";
        File file = new File(path);
        file.mkdirs();

        FileOutputStream fileOutputStream = new FileOutputStream(new File(path + "/TankTest.class"));
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();


    }
}
