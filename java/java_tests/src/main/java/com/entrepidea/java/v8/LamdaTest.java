package com.entrepidea.java.v8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//lamda is new feature introduced in JDK8.0
//resource: Functional programming in Java: using collections/Java magazine, July/Aug
public class LamdaTest {

	public static void main(String[] args) {
		List<String> friends = Arrays.asList(new String[]{
				"Gates","Jobs", "Linus","Zurkberg"
		});
		
		//lamda 
		friends.forEach(name -> System.out.println(name));


		
		//string is immutable, when transforming string list, 
		//traditionally we create a new List<String> as the place holder
		//for the transformed result.
		//with the introduction of Stream in JDK8, things become easy
		//code below:
		//get a stream instance by calling stream(), it's available in all collections
		//Stream's method map() take lamda's transformed result on a while collection.
		//a new collection was crested at this point
		friends.stream().map(name -> name.toUpperCase())
		.forEach(name -> System.out.println(name));
		
		
		//the map() method is useful in that it transform a input collection
		//to a new output collection, and the input and output are not 
		//necesserily of the same type.
		friends.stream().map(name ->name.
				length())
		.forEach(count -> System.out.print(count+" "));
		System.out.println();
		
		//when trying to find elements met particular criteria
		//Stream#filter can be used.
		final List<String> filterFrields = friends.stream().filter(name -> name.startsWith("Z"))
		.collect(Collectors.toList());
		
		System.out.println(String.format("fount %d name(s)", filterFrields.size()));
		
		filterFrields.forEach(name -> System.out.println(name));
		
		friends.replaceAll(s -> s.toUpperCase());
		
		friends.forEach(System.out::println);
		
		
		//method reference
		System.out.println("\ndemo for method reference\n");
		friends.forEach(System.out::println);
		friends.stream().map(String::toUpperCase)
		.forEach(System.out::println);
		
		
	}

}
