package com.entrepidea.java.features.v1_8;

import com.entrepidea.java.features.v1_8.support.Person;
import com.entrepidea.java.features.v1_8.support.RoboContact;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


    /*
     * Lamda replaces anonymous inner class hence improves the readability;
     * and the retrofitted collections for lambda use internal iteration rather than using external
     * iteration. these are the two good things I can think of.
     * As of performance, there is no evidence that lambda is better than non-lambda version.
     *
     * https://www.reddit.com/r/java/comments/2suvir/java_8_lambda_performance_is_not_great/
     *
     * The article above address Lambda performance issue, it's also claimed that Stream is like LINQ and LINQ is an abstract
     * working on data source like SQL.
     *
     * Since JDK 8
     *
     * */

public class LambdaTests {

    //
    // 	Predicate is a function interface, other predicates are:
    //	Consumer, Supplier, Function, UnaryOperator, BinaryOperator
    //	see this link:
    //	http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html
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