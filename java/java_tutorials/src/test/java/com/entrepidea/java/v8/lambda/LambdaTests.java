package com.entrepidea.java.v8.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

/**
 * Lambda was introduced since JDK 1.8. 
 * lambda is a function. It can be passed as data functional interface: one single method interface.
 * when implementing a functional interface, lambda is used.
 * JDK 8 introduce APIs that support lambda parameters such as forEAch removeIf, etc.
 * 
 * source: https://www.youtube.com/watch?v=a450CqNXFgs
 * other source: Functional programming in Java: using collections/Java magazine, July/Aug, 2015
 * 
 */
public class LambdaTests {

	// in this example, runnable and callable as a thread worker can be a good
	// candidate for lambda.
	// these two interfaces have only one method respectively (run and call)
	// therefore Java is smart
	// enough to infer that out. That's how it looks: () ->
	@Test
	public void lambdaTest() throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Runnable r = () -> System.out.println("Hello, I am programmed in Lambda way.");
		executor.submit(r);
		executor.shutdown();
		executor = Executors.newCachedThreadPool();
		Callable<String> c = () -> "Hello.";
		Future<String> f = executor.submit(c);
		Assert.assertEquals("Hello.", f.get());
		executor.shutdown();

	}

	List<String> friends = Arrays.asList(new String[] { "Gates", "Jobs", "Linus", "Zurkberg" });

	@Test
	public void arrayTest() {
		friends.forEach(name -> System.out.println(name));
	}

	// string is immutable, when transforming string list,
	// traditionally we create a new List<String> as the place holder
	// for the transformed result.
	// with the introduction of Stream in JDK8, things become easy
	// code below:
	// get a stream instance by calling stream(), it's available in all
	// collections
	// Stream's method map() take lamda's transformed result on a while
	// collection.
	// a new collection was crested at this point
	@Test
	public void streamTest() {
		// the map() method is useful in that it transform a input collection
		// to a new output collection, and the input and output are not
		// necesserily of the same type.
		friends.stream().map(name -> name.length()).forEach(count -> System.out.print(count + " "));
		System.out.println();

		// when trying to find elements met particular criteria
		// Stream#filter can be used.
		final List<String> filterFrields = friends.stream().filter(name -> name.startsWith("Z"))
				.collect(Collectors.toList());

		System.out.println(String.format("fount %d name(s)", filterFrields.size()));

		filterFrields.forEach(name -> System.out.println(name));

		friends.replaceAll(s -> s.toUpperCase());

		friends.forEach(System.out::println);

		// method reference
		System.out.println("\ndemo for method reference\n");
		friends.forEach(System.out::println);
		friends.stream().map(String::toUpperCase).forEach(System.out::println);

	}
	
	
	static class Person{
		String name;
		public String getName(){return name;};
		public Person(String n){
			name = n;
		}
	}
	@Test
	public void objectArrayTest(){
		List<Person> persons = Arrays.asList(new Person[]{
				new Person("Jonathan"), new Person("Aaron")
		});
		//Collections has been retrofitted to support lambda in JDK 1.8	
		Collections.sort(persons, (p1,p2) -> p1.getName().compareTo(p2.getName()));
		persons.forEach(p -> System.out.println(p.getName()));
		//this line will fail due to UOE, this is a bug. UOE occurs when list was created out of asList
		//http://stackoverflow.com/questions/27587672/encountered-in-java-8-jdk-8023339-j-u-arrays-aslist-removeif-is-lazy-i
		persons.removeIf(p -> "Aaron".equals(p.getName()));
		persons.forEach(p -> System.out.println(p.getName()));
	}
}
