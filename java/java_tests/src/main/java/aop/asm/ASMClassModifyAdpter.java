package asm;


import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ASMClassModifyAdpter extends ClassAdapter {

    public ASMClassModifyAdpter(ClassVisitor classVisitor) {
        super(classVisitor);
    }

    public MethodVisitor visitMethod(final int access, final String methodName,
                                     final String desc, final String signature, final String[] exceptions) {
        if("display2".equals(methodName)) {
            return null;
        }
        if("display1".equals(methodName)) {
            MethodVisitor methodVisitor = cv.visitMethod(access, methodName, desc, signature, exceptions);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(Opcodes.ALOAD , 0);
            methodVisitor.visitLdcInsn("I am name");
            methodVisitor.visitFieldInsn(Opcodes.PUTFIELD , "asm/ForASMTestClass", "name" , "Ljava/lang/String;");

            methodVisitor.visitVarInsn(Opcodes.ALOAD , 0);
            methodVisitor.visitLdcInsn("I am value");
            methodVisitor.visitFieldInsn(Opcodes.PUTFIELD , "asm/ForASMTestClass", "value" , "Ljava/lang/String;");

            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out" , "Ljava/io/PrintStream;");
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
            methodVisitor.visitFieldInsn(Opcodes.GETFIELD, "asm/ForASMTestClass", "name", "Ljava/lang/String;");
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");

            methodVisitor.visitEnd();
            return methodVisitor;
        }else {
            return cv.visitMethod(access, methodName, desc, signature, exceptions);
        }
    }

}
