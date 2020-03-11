package com.entrepidea.core.basic;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionTests {

    //How many ways to instantialize a class? (Morgan Stanley Interview 05/17/17)

    //there should be at least three ways: through a constructor, or from a static factory method or reflection.

    //below shows how to use reflection to instantiate an integer class.
    @Test
    public void testInstantiate() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<Integer> cls = (Class<Integer>)Class.forName("java.lang.Integer");
        Constructor<Integer> ctr = cls.getConstructor(int.class);
        Integer i = ctr.newInstance(10);
        Assert.assertEquals(10,i.intValue());
    }

    @Test
    public void testMethods() throws ClassNotFoundException {
        Class<ConcurrentHashMap> cls = (Class<ConcurrentHashMap>)Class.forName("java.util.concurrent.ConcurrentHashMap");
        Method[] methods = cls.getDeclaredMethods();
        for(Method m : methods){
            System.out.println(m.getName());
        }
    }
}
