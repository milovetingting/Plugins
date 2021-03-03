package com.wangyz.plugins;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.gradle.internal.impldep.org.mozilla.classfile.ByteCode.ALOAD;
import static org.gradle.internal.impldep.org.mozilla.classfile.ByteCode.IFNE;
import static org.gradle.internal.impldep.org.mozilla.classfile.ByteCode.INVOKESTATIC;
import static org.gradle.internal.impldep.org.mozilla.classfile.ByteCode.RETURN;


public class DebounceMethodVisitor extends MethodVisitor {

    public DebounceMethodVisitor(int api, MethodVisitor mv) {
        super(api, mv);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESTATIC, "com/wangyz/utils/debounce/DebounceHelper", "shouldClick", "(Landroid/view/View;)Z", false);
        Label label = new Label();
        mv.visitJumpInsn(IFNE, label);
        mv.visitInsn(RETURN);
        mv.visitLabel(label);
    }

}
