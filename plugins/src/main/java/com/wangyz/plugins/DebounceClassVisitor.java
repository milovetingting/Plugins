package com.wangyz.plugins;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class DebounceClassVisitor extends ClassVisitor implements Opcodes {

    public DebounceClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM6, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        if (((access & ACC_PUBLIC) != 0 && (access & ACC_STATIC) == 0) &&
                name.equals("onClick") &&
                desc.equals("(Landroid/view/View;)V")) {
            methodVisitor = new DebounceMethodVisitor(Opcodes.ASM6, methodVisitor);
        }
        return methodVisitor;
    }
}
