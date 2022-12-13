package com.entrepidea.core.features.v8;

import com.entrepidea.core.features.v8.support.Person;
import com.entrepidea.core.features.v8.support.RoboContact;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
     * @Description:
     *
     * Lamda replaces anonymous inner class hence improves the readability;
     * another common use of Lambda is to pass it as a data type in a function's argument list.
     * Lambda is an instance of a more broader concept known as functional interface, which can be seen as a data type.
     *
     * Functional interface is introduced since Java 8 to address the outcry for addition of functional programming in Java, like Scala.
     * Functional interface has an abstract method and zero/multiple default method implementations. Be careful that the methods in the Object class can not be used as Functional interface's abstract method.
     * Functional interface is annotated with @FunctionalInterface
     * A Functional interface can be assigned with a lambda expression or a method reference.
     *
     * Lambda are generic functional interface instances, but there are also several other built-in functional interface instances such as:
     * Predicate, Function, Consumer, Supplier, etc
     *
     * In addition, the APIs in Java Collection package is retrofitted to accommodate functional interfaces and lambda, e.g sort.
     * Also a Stream interface was added on the top of it for the group operation on the collection.
     *
     * As of performance, there is no evidence that lambda is better than its non-lambda cousin.
     *
     *Since JDK 8
     *
     * References:
     * https://www.reddit.com/r/java/comments/2suvir/java_8_lambda_performance_is_not_great/
     * This article addresses Lambda performance issue, it's also claimed that Stream is like LINQ and LINQ is an abstract
     * working on data source like SQL.
     *
     * https://github.com/winterbe/java8-tutorial
     *
     *
     * */

public class LambdaTests {

    //maybe most simple lambda example
    @Test
    public void testSimpleLambda() {
        List<String> fruits = Arrays.asList("Apple", "Pear", "Mongo", "Water melon", "Pineapple");
        Collections.sort(fruits, (String s1, String s2) -> {
            return s1.compareTo(s2);
        });
        fruits.forEach(System.out::println);
    }

    //data deference
    @Test
    public void testEvenMoreSimpleLambda() {
        List<String> fruits = Arrays.asList("Apple", "Pear", "Mongo", "Water melon", "Pineapple");
        fruits.sort((a, b) -> a.compareTo(b));//compiler is smart enough to deference what type a and b should be basing on the type of fruits.

        fruits.forEach(System.out::println);
    }

    @FunctionalInterface
    interface Converter<F, T> {
        T convert(F from);
    }

    @Test
    public void testFunctionInterface() {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);//convert from a String type to an Integer type
        assertEquals(converter.convert("123"), 123, 0);
        //another way, more simple way, through method reference
        Converter<String, Integer> converter2 = Integer::valueOf;
        assertEquals(converter2.convert("456"), 456, 0);
    }

    //Lambda scope - how lambda access final, static and normal instance variables?
    @Test
    public void testLambdaAccessFinalVar() {
        final int num = 1; // the final keyword can be ignored, as long as num won't change, thus implicitly it's thought of as final
        Converter<Integer, String> int2StringConverter = (from) -> String.valueOf(from + num); //assign a lambda expression to a Function interface variable.
        assertTrue(int2StringConverter.convert(2).equals("3"));

    }

    static int staticNum = 32;

    @Test
    public void testLamdaAccessStaticVar() {

        Converter<Integer, String> converter = (from) -> {
            staticNum = 64;
            return String.valueOf(from + staticNum);
        };
        assertTrue(converter.convert(10).equals("74"));

    }

    @Test
    public void testLambdaAccessNormalVar() {

        Converter<Integer, String> converter = (form) -> {
            int num = 24;
            return String.valueOf(form + num);
        };

        assertTrue(converter.convert(24).equals("48"));
    }

    //
    // 	Predicate is a functional interface, it takes one argument and return a boolean thus can serves as a filter on a collection, for example.
    //  Other built-in functional interfaces include:
    //	Consumer, Supplier, Function, UnaryOperator, BinaryOperator
    //	see this link:
    //	http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html
    //  As a comparison, predicate in C++ context is a kind of functor that checkBalancedBinaryTree the elements, e.g "find_if"
    // more predicate examples and usages can be found from: https://www.geeksforgeeks.org/java-8-predicate-with-examples/
    //
    @Test
    public void PredicateTest() {
        List<Person> pl = Person.createShortList();
        RoboContact robo = new RoboContact();

        Predicate<Person> allDrivers = p -> p.getAge() >= 16;
        Predicate<Person> allDraftees = p -> p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Person.Gender.MALE;
        Predicate<Person> allPilots = p -> p.getAge() >= 23 && p.getAge() <= 65;

        System.out.println("\n==== Test 04 ====");
        System.out.println("\n=== Calling all Drivers ===");
        robo.phoneContacts(pl, allDrivers);

        System.out.println("\n=== Emailing all Draftees ===");
        robo.emailContacts(pl, allDraftees);

        System.out.println("\n=== Mail all Pilots ===");
        robo.mailContacts(pl, allPilots);

        // Mix and match becomes easy
        System.out.println("\n=== Mail all Draftees ===");
        robo.mailContacts(pl, allDraftees);

        System.out.println("\n=== Call all Pilots ===");
        robo.phoneContacts(pl, allPilots);


    }

    @Test
    public void testPredicate() {
        Predicate<String> predicate = (s) -> s.length() > 0;
        assertTrue(predicate.test("fool"));
        assertFalse(predicate.negate().test("foo"));
    }

    //Function is a one argument function producing a result - it can be used in chain operation
    @Test
    public void testFunction() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> back2String = toInteger.andThen(String::valueOf);
        assertTrue(back2String.apply("123").equals("123"));
    }

    //Supplier produces a result of a given generic type, Supplier doesn't take argument
    class Foo {
    }

    @Test
    public void testSupplier() {
        Supplier<Foo> fooSupplier = Foo::new;
        assertTrue(fooSupplier.get() instanceof Foo);

    }

    //Consumer represent operations working on a single input argument
    class Nothing {
        private String name;

        public Nothing() {
        }

        ;

        public Nothing(String name) {
            this.name = name;
        }

        @Override
        public String toString() {

            return name + " is totally nothing";
        }
    }

    interface NothingFactory<N> {
        N create(String name);
    }

    @Test
    public void testConsumer() {
        NothingFactory<Nothing> nothingNothingFactory = Nothing::new;
        Nothing alice = nothingNothingFactory.create("Alice");
        Consumer<Nothing> nothingConsumer = n -> System.out.println(n);
        nothingConsumer.accept(alice);

    }

    //more consumer examples: https://www.geeksforgeeks.org/java-8-consumer-interface-in-java-with-examples/
    @Test
    public void testConsumerAccept() {
        Consumer<List<Integer>> displayList = list -> list.stream().forEach(System.out::println);
        List<Integer> ll = new ArrayList<>();
        ll.add(2);
        ll.add(3);
        ll.add(1);
        displayList.accept(ll);
    }

    @Test
    public void testComsumerAndThen(){
        Consumer<List<Integer>> modify = list -> {
            for (int i=0;i<list.size();i++){
                list.set(i,2*list.get(i));
            }
        };

        Consumer<List<Integer>> display = list -> list.stream().forEach(System.out::println);
        List<Integer> ll = new ArrayList<>();
        ll.add(2);
        ll.add(3);
        ll.add(1);
        modify.andThen(display).accept(ll);
    }




    //
    // Stream APIs provide a series of intermediate or terminal functions to work on a collection
    // http://zeroturnaround.com/rebellabs/java-8-explained-applying-lambdas-to-java-collections/
    //
    @Test
    public void forEachTest() {
        List<Person> people = Person.createShortList();
        people.stream().filter(p -> p.getAge() >= 45 && p.getGender() == Person.Gender.MALE).forEach(System.out::println);
    }


    //stream is discharged after being used. To keep a mutable list, we will create a list copy
    //See the examples below to see how collectors can help
    @Test
    public void CollectTest() {
        List<Person> people = Person.createShortList();
        List<Person> newGroup = people.stream().filter(p -> p.getAge() < 45).collect(Collectors.toList());
        newGroup.forEach(System.out::println); //method reference
    }

    //this one to return a specific list type (ArrayList), be noted how method reference is used.
    @Test
    public void CollectTest2() {
        List<Person> people = Person.createShortList();
        List<Person> newGroup = people.stream().filter(p -> p.getAge() < 45).collect(Collectors.toCollection(ArrayList::new));
        newGroup.forEach(System.out::println); //method reference
    }


    //Stream#map is used to transform a list, example below shows a list of People has been transformed to a list of Students.
    static class Student {
        private Person person;
        public Student(Person p) {
            person = p;
        }
        @Override
        public String toString() {
            return person.getGivenName() + "," + person.getSurName() + ", age: " + person.getAge();
        }
    }
    @Test
    public void mapTest() {
        List<Person> persons = Person.createShortList();
        List<Student> students = persons.stream().map(Student::new).collect(Collectors.toList());
        students.forEach(System.out::println);
    }

    //aggregation in Stream
    @Test
    public void aggregateTest() {
        List<Double> list = Arrays.asList(new Double[]{1.2, 3.2, 4.5, 6.6, 10.01, -0.45, 0.09});

        long start = System.nanoTime();
        double n = list.stream().min(Double::compare).get(); //using method reference like this seems expensive
        System.out.printf("take %dns to complete. The min is:%.2f\n ",(System.nanoTime() - start), n);

        start = System.nanoTime();
        double min = Double.MAX_VALUE;
        for (Double d : list) {
            if (d < min) {
                min = d;
            }
        }
        System.out.printf("take %dns to complete the regular way. The min is: %.2f\n",(System.nanoTime() - start),min);
    }
}