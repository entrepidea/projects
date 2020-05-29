package com.entrepidea.core.basic;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Desc:
 * Construct in Java means allowable elements on the language level. Examples of constructs include but not limited to:
 * Keywords such as final, static, abstract; members in a class as well as static block within; inner classes;
 * selection/looping/sequence execution statements; member scopes such as private, public, protected and packaged
 * for a discussion, check out this SO post and the reference within:
 * https://stackoverflow.com/questions/7563650/what-are-the-basic-language-constructs-in-java
 *
 * @Date: 01/22/20, 04/12/20
 *
 * */
public class ConstructTests {

    /**
     * why a constructor can't be final (or static, or abstract)?
     */
    //https://stackoverflow.com/questions/9477476/why-constructors-cannot-be-final
    //some takeaways:
    // 1. a constructor can't be overridden, therefore it's implicitly final
    // 2. What's static? static keyword indicates that no instance of a class. However, when you use "new" followed by a constructor, an instance is created thus a "static" constructor doesn't make sense
    // 3. abstract keyword applied to a method implies that method doesn't have a body, a constructor always has a body, so it can't be abstract. Besides, abstract class/method are meant to be overridden, constructor is already final, it can't be overridden.



    /**
        Explain final and finally, and usage in real life scenarios	(Morgan Stanley Interview 05/17/17)
        Explain final keyword. (10/15/14, Markit on site)
     */
    //final is used to decorate variables, instance methods and classes. It means variables are constants, non-overridable methods and non-inheritable classes respectively.
    //finally is used together with try/catch to provide an venue for house cleaning work such as closing a database connection.
    // One thing to remember, even there is a "return" statement in the catch block, the finally block will be run.
    // However there is a catch, see code below, and read the comments for an explanation
    public int getInt(){
        int i=0;
        try{
            i++;
            return i; //at this point i==1, a copy was made from the stack frame, the "return" is held to allow "finally" block to execute
        }
        finally{
            i++;
            System.out.println("i in finally block: "+i); //the local variable i proceed to increment itself and now i==2. After this line "return" is called, the copy (i==1) is restored back to stack frame, so the final i==1
        }
    }

    @Test
    public void test(){
        System.out.println("The ultramate i is: "+getInt());
    }

    /**
        phone Interview with Ted from BNP Paribas, 09/26/14
        Tell me 3 advantages of final key word of a member variable; how is it processed by compiler,
        if you are a compiler writer, why do you define/design a final variable?
     *
     */
    //final keyword is processed at compiler time
    //advantages: speed - result is always rendered at compiler time; space saving; safety - can't be changed thus no malicious code implanted; can be used in multi-threaded env w/o synchronization


    /**
       TD ameritrade phone interview 08/12/14
       What’s the difference b/w static and final
     */
    //different semantics. "static" is for methods that are associated with classes rather than instances; "final" implies constant variables or non-inheritant class, non-overridable instance methods.

    /**
       How to avoid persisting certain fields in POJO?
     *
     */
    //using transient keyword, See TransientTests.java for examples.



    /**
       Morgan Stanley phone interview, 05/14/18
       Different b/w int and Integer. Tell me more about Integer class. For example, is it immutable
     *
     */
    //Integer is immutable. its value, once set, can't be changed. A immutable's state cant be changed.
    // If you look into the source code, Integer's value is a final int. final meaning it can't be changed.


    /**
     *
        Interview with Credit Susie (01/23/14)
        How to return a non-primitive member in a class and make sure it’s NOT mutable?
     */
    //To make a returning class member immutable, it's important to make sure its internal state won't be changed in all circumstances.
    //it's also noted that this applies to the members of the class member in question, in case it's a user defined type itself.
    //By extension, if there is a list in the class, how can we return the list but also make sure it won't be altered structurely (meaning no insertion/deletion, etc)
    //for the last part, I think we can return a iterator of the CopyOnWriteArrayList wrapper. The iterator is only a snapshot so that it won't be altered.
    static class Koo{
        private int id;
        Koo(int id){
            this.id = id;
        }

        @Override
        public String toString(){
            return Integer.toString(id);
        }
    }
    static class ImmutableFoo{
        private String bar;
        private List<Koo> lstKoo;
        ImmutableFoo(){
            lstKoo = new ArrayList<>();
            for(int i=0;i<10;i++){
                lstKoo.add(new Koo(i));
            }
        }

        public String getBar(){return bar;}
        public Iterator<Koo> getKoos(){
            return new CopyOnWriteArrayList<Koo>(lstKoo).iterator();
        }
    }

    @Test
    public void test2(){
        ImmutableFoo foo = new ImmutableFoo();
        Iterator<Koo> iter = foo.getKoos();
        while(iter!=null&&iter.hasNext()){
            System.out.println(iter.next());
        }
        //No way can update foo's internal state.
    }


    /**
     * polymorphism, how is it implemented in C++ or Java?
     *
     */
    //polymorphism is an object taking many forms. it happens when a hierarchy of classes related via inheritance.
    //a derived class can inherit member functions (C++) or member methods (Java) from its base class.
    //While the signature is the same, the implementation can vary.
    //In C++, member functions with qualifier "virtual" is signaled to be inherited and changed at the derived class's disposal, thus polymorphism.
    //which qualifier "virtual", it's just another member function.
    // (while we are at it, a virtual destuctor is to be there as well)
    //https://www.tutorialspoint.com/cplusplus/cpp_polymorphism.htm
    //In Java, every member methods are implicitly virtual therefore all instance methods can be inherited.
    //https://stackoverflow.com/questions/34445553/polymorphism-c-vs-java


    /**
     *
        Abstract class v.s Interface
     */
    //Abstract class is a class with qualifier "abstract". An abstract class can't be instantialized and is meant to be inherited.
    //Interface is a functions advertisement of a modules. The function declared within don't have a body (this is no longer the case since 1.8)
    //Since we are at it, interface with only one method (excluding default method implementation) is called functional interface (since 1.8),
    // it's normally working with lambda expression. See more examples from features package in this project.


    /**
     *
            Inheritance v.s Composite
     */
    //Inheritance defines a "Is-a" relation while Composite defines a "Has-a" relation. Composite is preferable over Inheritance
    //See this link: https://stackoverflow.com/questions/2399544/difference-between-inheritance-and-composition
    //and item 18 in EJ 3rd edition: Favor composite over inheritance
    //Be noted that Adapter pattern can use inheritance or composite (recommended) for implementation. See Design pattern packages.


    /**
     *
        Have you used annotation in JAVA? Have you designed Annotation yourself? (Credit Suise onsite interview, 01/29/14)?
        How to write a customized annotation (JPMorgan videoconf interview, 01/22/20)?
     *
     */
    //annotation is Java's way of telling JVM or compiler about the info of the class, methods, etc. They are the metadata class.
    //There are many built-in annotations, which, while optional, are recommended best practice.
    //To write customized annotation, one needs to implement @Interface.
    //https://www.zhihu.com/search?type=content&q=annotation
    //作者：疯狂的Java说书酱
    //链接：https://zhuanlan.zhihu.com/p/67967745
    //来源：知乎
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Todo {
        public enum Priority {LOW, MEDIUM, HIGH}
        public enum Status {STARTED, NOT_STARTED}
        String author() default "Yash";
        Priority priority() default Priority.LOW;
        Status status() default Status.NOT_STARTED;
    }
    @Todo(priority = Todo.Priority.MEDIUM, author = "Yashwant", status = Todo.Status.STARTED)
    public void incompleteMethod1() {
        //Some business logic is written
        //But it’s not complete yet
    }
    //There are four meta annotations to annotate an annotation: Target, Retention, Inherited and Documented
    //Retention is the lifespan of the annotation, could be SOURCE, CLASS AND RUNTIME.
    // SOURCE means the annotation survives until it's compiled into bytecode. Look at doc for the other two
    //When it's RUNTIME, it can be interpreted with reflection APIs.
    @Test
    public void readAnnotations(){
        Class clazz = this.getClass();
        for(Method m : clazz.getMethods()){
            Todo todoAnnotation = m.getAnnotation(Todo.class);
            if(todoAnnotation!=null){
                Assert.assertTrue(m.getName().equals("incompleteMethod1"));
                System.out.println(" Author : " + todoAnnotation.author());
                System.out.println(" Priority : " + todoAnnotation.priority());
                System.out.println(" Status : " + todoAnnotation.status());
            }
        }
    }


    /**
        phone interview with Morgan Stanley 08/16/13 3:00 PM (recruiter: IRIS)
        BNP Paribas onsite 02/18/20
        marker interface, what is it? how to create one?
     *
     */
    //mark interface doesn't have a body, it's more like a signal to JVM/Compiler to state its purpose. e.g Serializable, Cloneable
    //User defined annotation somehow can serve the same purpose as a mark interface.

    /**
    static factory method vs. Constructor, what's the benefit?
     */
    //one benefit is it has name, and by that solves the dilimma that two constructors with same signature being syntax incorrect but reality necessary
    //See this SO for an explanation: https://stackoverflow.com/questions/2842232/why-would-you-have-two-constructors-with-the-same-signature
    //other benefits see Item#1 from Effective Java, 3rd Ed.


    /**
     * BNP Paribas onsite by Leo, 02/18/20
     * Can protected be accessed by the class in the same package?
     * */
    //yes. See demo below.
    class Foo{
        protected int x = 4;
    }

    class Boo{
        public int getProtectedVarFromOtherClass(){
            Foo f = new Foo();
            return f.x;
        }
    }

    @Test
    public void testGetProtectedVarFromOtherClass(){
        Assert.assertTrue(new Boo().getProtectedVarFromOtherClass()==4);
    }

    /**
     * BNP Paribas onsite, 02/18/20
     * Abstract class. Does it have default constructor?
     *
     * */
    //it does. public abstract class has default public constructor; protected abstract class has default protected constructor.

    /**
     * BNP Paribas onsite, 02/18/20
     * Will finally block always be executed?
     *
     * */
    //almost. Except for system.exit. That's the most destructive nuke bomb.
}
