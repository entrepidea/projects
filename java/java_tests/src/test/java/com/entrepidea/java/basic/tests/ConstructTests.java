package com.entrepidea.java.basic.tests;

/**
 * Construct in Java means allowable elements on the language level. Examples of constructs include but not limited to:
 * Keywords such as final static, abstract; members in a class as well as static block within; inner classes;
 * selection/looping/sequence execution statements; member scopes such as private, public, protected and packaged
 * for a discussion, check out this SO post and the reference within:
 * https://stackoverflow.com/questions/7563650/what-are-the-basic-language-constructs-in-java
 *
 * This test set covers relevant questions around constructs.
 *
 * */
public class ConstructTests {

    /**
     * why a constructor can't be final (or static, or abstract)?
     */
    //https://stackoverflow.com/questions/9477476/why-constructors-cannot-be-final
    //some takeaways:
    // 1. a construcotor can't be overriden, therefore it's implicitly final
    // 2. What's static? static keyword indicates that no instance of a class. However, when you use "new" followed by a constructor, an instance is created thus a "static" constructor doesn't make sense
    // 3. abstract keyword applied to a method implies that method doesn't have a body, a constructor always has a body, so it can't be abstract. Besides, abstract class/method are meant to be overridden, constructor is already final, it can't be overridden.



    /**
        Explain final and finally, and usage in real life scenarios	(Morgan Stanley Interview 05/17/17)
        Explain final keyword. (10/15/14, Markit on site)
     */
    //final is used to decorate variables, instance methods and classes. It means variables are constants, non-overriable methods and non-inheritable classes respectively.
    //finally is used together with try/catch to provide an venue for house cleaning work such as closing a dbase connection.


    /**
        phone Interview with Ted from BNP Paribas, 09/26/14
        Tell me 3 advantages of final key word of a member variable; how is it processed by compiler,
        if you are a compiler writer, why do you define/design a final variable?
     *
     */
    //final keyword is processed at compiler time
    //advantages: speed - result is always rendered at compiler time; space saving; safety - can't be changed thus no malicious code implanted.


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
       Different b/w int and Integer. Tell me more about Integer class. For example, is it inmutable
     *
     */
    //Integer is immutable. its value, once set, can't be changed. A immutable's state cant be changed.
    // If you look into the source code, Integer's value is a final int. final meaning it can't be changed.


    /**
     *
        Interview with Credit Susie (01/23/14)
        How to return a non-primitive member in a class and make sure it’s NOT mutable?
     */
        //To make a returning class member immutable, it's important to make sure its internal state won't be change in all contexts.
        //it's also noted that this applies to the members of the class member in question, in case it's a user type itself.


    /**
     * polymorphism, how is it implemented in C++ or Java?
     *
     */
    //polymorphism is an object taking many forms. it happens when a hierarchy of classes related via inheritance.
    //a derived class can inherit member functions (C++) or member methods (Java) from its base class.
    //While the signature is the same, the implementation can vary.
    //In C++, member functions with qualifier "virtual" is deemed to be inherited and changed at the derived class's disposal, thus polymorphism.
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


    /**
     *
            Inheritance v.s Composite
     */
    //Inheritance defines a "Is-a" relation while Composite defines a "Has-a" relation. Composite is preferable over Inheritance
    //See this link: https://stackoverflow.com/questions/2399544/difference-between-inheritance-and-composition
    //and item 18 in EJ 3rd edition: Favor composite over inheritance


    //Credit Suise onsite interview (01/29/14)
    //TODO Have you used annotation in JAVA? Have you designed Annotation yourself?

    //phone interview with Morgan Stanley 08/16/13 3:00 PM (recruiter: IRIS)
    //TODO marker interface, what is it? how to create one?

}
