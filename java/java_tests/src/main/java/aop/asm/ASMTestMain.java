package asm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class ASMTestMain {

    private final static DynamicClassLoader TEST_CLASS_LOADER = new DynamicClassLoader(
            (URLClassLoader)ASMTestMain.class.getClassLoader()
    );

    public static void main(String []args)
            throws ClassNotFoundException, IOException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, SecurityException,
            InvocationTargetException, NoSuchMethodException {
        Class <?>beforeASMClass = TEST_CLASS_LOADER.loadClass("asm.ForASMTestClass");

        TEST_CLASS_LOADER.defineClassByByteArray("asm.ForASMTestClass", asmChangeClassCall());
        Class <?>afterASMClass = TEST_CLASS_LOADER.loadClass("asm.ForASMTestClass");

        Object beforeObject = beforeASMClass.newInstance();
        Object afterObject = afterASMClass.newInstance();

        beforeASMClass.getMethod("display1").invoke(beforeObject);
        afterASMClass.getMethod("display1").invoke(afterObject);
    }

    private static byte[] asmChangeClassCall() throws IOException {
        ClassReader classReader = new ClassReader("asm.ForASMTestClass");

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ASMClassModifyAdpter modifyAdpter = new ASMClassModifyAdpter(classWriter);
        classReader.accept(modifyAdpter, ClassReader.SKIP_DEBUG);
        //byte []bytes = classWriter.toByteArray();
        //new FileOutputStream("d:/ForASMTestClass.class").write(bytes);
        //return bytes;

        return classWriter.toByteArray();
    }
}
