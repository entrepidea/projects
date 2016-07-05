package com.entrepidea.java.design.pattern;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.junit.Test;


/**
 * the code below is from:
 * https://iolaru.wordpress.com/2011/11/26/two-singleton-instances-inside-the-same-jvm-example/
 * purpose is to demo that a JVM can load multiple Singleton instances, as long as they are loaded by different class loaders
 * in another word, the Singleton is one per class loader, not JVM.
 * 
 * However, the code failed to work. TODO: Just google or SO the answer.
 * 
 * also read this: 
 * http://www.javaworld.com/article/2074897/java-web-development/when-is-a-singleton-not-a-singleton-.html
 * 
 * */

public class SingletonTests {

	public static class Singleton {

		private static Singleton _instance;
		private Singleton() {
			System.out.println("Created an instance of Singleton.");
		}

		public static Singleton getInstance() {
			synchronized(Singleton.class){
				if (_instance == null) {
					_instance = new Singleton();
				}
				return _instance;
			}
		}
	}
	
	public static class MyClassLoader extends ClassLoader{
		private static final int BUFFER_SIZE = 8192;
		 
	    protected synchronized Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
	 
	        // IS THIS CLASS ALREADY LOADED?
	        Class<?> cls = findLoadedClass(className);
	        if (cls != null) {
	            return cls;
	        }
	 
	        // GET CLASS FILE NAME FROM CLASS NAME
	        String clsFile = ""+className.replace('.', '/') + ".class";
	 
	        // GETS BYTES FOR CLASS
	        byte[] classBytes = null;
	        try {
	            InputStream in = getResourceAsStream(clsFile);
	            byte[] buffer = new byte[BUFFER_SIZE];
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
	            int n = -1;
	            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
	                out.write(buffer, 0, n);
	            }
	            classBytes = out.toByteArray();
	        } catch (IOException e) {
	            System.out.println("ERROR loading class file: " + e);
	        }
	 
	        if (classBytes == null) {
	            throw new ClassNotFoundException("Cannot load class: " + className);
	        }
	 
	        // TURNS THE BYTE ARRAY INTO A CLASS
	        try {
	            cls = defineClass(className, classBytes, 0, classBytes.length);
	            if (resolve) {
	                resolveClass(cls);
	            }
	        } catch (SecurityException e) {
	            // LOADING CORE JAVA CLASSES SUCH AS JAVA.LANG.STRING
	            // IS PROHIBITED, THROWS JAVA.LANG.SECURITYEXCEPTION.
	            // DELEGATE TO PARENT IF NOT ALLOWED TO LOAD CLASS
	            cls = super.loadClass(className, resolve);
	        }
	 
	        return cls;
	    }
	}
	@Test
	public void testTwoSingletonInOneJVM() {
        MyClassLoader cl1 = new MyClassLoader();
        Object o1;
 
        try {
            Class<?> c = Class.forName("com.entrepidea.java.design.pattern.Singleton", true, cl1);
            Method m = c.getMethod("getInstance");
            o1 = m.invoke(null, new Object[] {});
            System.out.println(o1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something is wrong here: ", e);
        }
 
        Singleton s2 = Singleton.getInstance();
        System.out.println(s2);
 
        Singleton s3 = Singleton.getInstance();
        System.out.println(s3);
	}
}
