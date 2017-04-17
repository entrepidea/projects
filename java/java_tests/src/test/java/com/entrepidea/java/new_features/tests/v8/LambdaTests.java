package com.entrepidea.java.new_features.tests.v8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Test;

enum Gender { MALE, FEMALE }
class Person {
    private String givenName;
    private String surName;
    private int age;
    private Gender gender;
    private String eMail;
    private String phone;
    private String address;

    public static class Builder{

        private String givenName="";
        private String surName="";
        private int age = 0;
        private Gender gender = Gender.FEMALE;
        private String eMail = "";
        private String phone = "";
        private String address = "";

        public Person.Builder givenName(String givenName){
            this.givenName = givenName;
            return this;
        }

        public Person.Builder surName(String surName){
            this.surName = surName;
            return this;
        }

        public Person.Builder age (int val){
            age = val;
            return this;
        }

        public Person.Builder gender(Gender val){
            gender = val;
            return this;
        }

        public Person.Builder email(String val){
            eMail = val;
            return this;
        }

        public Person.Builder phoneNumber(String val){
            phone = val;
            return this;
        }

        public Person.Builder address(String val){
            address = val;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

    private Person(){
        super();
    }

    private Person(Person.Builder builder){
        givenName = builder.givenName;
        surName = builder.surName;
        age = builder.age;
        gender = builder.gender;
        eMail = builder.eMail;
        phone = builder.phone;
        address = builder.address;

    }

    public String getGivenName(){
        return givenName;
    }

    public String getSurName(){
        return surName;
    }

    public int getAge(){
        return age;
    }

    public Gender getGender(){
        return gender;
    }

    public String getEmail(){
        return eMail;
    }

    public String getPhone(){
        return phone;
    }

    public String getAddress(){
        return address;
    }

    public void print(){
        System.out.println(
                "\nName: " + givenName + " " + surName + "\n" +
                        "Age: " + age + "\n" +
                        "Gender: " + gender + "\n" +
                        "eMail: " + eMail + "\n" +
                        "Phone: " + phone + "\n" +
                        "Address: " + address + "\n"
        );
    }

    @Override
    public String toString(){
        return "Name: " + givenName + " " + surName + "\n" + "Age: " + age + "  Gender: " + gender + "\n" + "eMail: " + eMail + "\n";
    }

    public static List<Person> createShortList(){
        List<Person> people = new ArrayList<>();

        people.add(
                new Person.Builder()
                        .givenName("Bob")
                        .surName("Baker")
                        .age(21)
                        .gender(Gender.MALE)
                        .email("bob.baker@example.com")
                        .phoneNumber("201-121-4678")
                        .address("44 4th St, Smallville, KS 12333")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("Jane")
                        .surName("Doe")
                        .age(25)
                        .gender(Gender.FEMALE)
                        .email("jane.doe@example.com")
                        .phoneNumber("202-123-4678")
                        .address("33 3rd St, Smallville, KS 12333")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("John")
                        .surName("Doe")
                        .age(25)
                        .gender(Gender.MALE)
                        .email("john.doe@example.com")
                        .phoneNumber("202-123-4678")
                        .address("33 3rd St, Smallville, KS 12333")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("James")
                        .surName("Johnson")
                        .age(45)
                        .gender(Gender.MALE)
                        .email("james.johnson@example.com")
                        .phoneNumber("333-456-1233")
                        .address("201 2nd St, New York, NY 12111")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("Joe")
                        .surName("Bailey")
                        .age(67)
                        .gender(Gender.MALE)
                        .email("joebob.bailey@example.com")
                        .phoneNumber("112-111-1111")
                        .address("111 1st St, Town, CA 11111")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("Phil")
                        .surName("Smith")
                        .age(55)
                        .gender(Gender.MALE)
                        .email("phil.smith@examp;e.com")
                        .phoneNumber("222-33-1234")
                        .address("22 2nd St, New Park, CO 222333")
                        .build()
        );

        people.add(
                new Person.Builder()
                        .givenName("Betty")
                        .surName("Jones")
                        .age(85)
                        .gender(Gender.FEMALE)
                        .email("betty.jones@example.com")
                        .phoneNumber("211-33-1234")
                        .address("22 4th St, New Park, CO 222333")
                        .build()
        );


        return people;
    }

}
class RoboContactLambda {
	public void phoneContacts(List<Person> pl, Predicate<Person> pred){
		for(Person p:pl){
			if (pred.test(p)){
				roboCall(p);
			}
		}
	}

	public void emailContacts(List<Person> pl, Predicate<Person> pred){
		for(Person p:pl){
			if (pred.test(p)){
				roboEmail(p);
			}
		}
	}

	public void mailContacts(List<Person> pl, Predicate<Person> pred){
		for(Person p:pl){
			if (pred.test(p)){
				roboMail(p);
			}
		}
	}

	public void roboCall(Person p){
		System.out.println("Calling " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getPhone());
	}

	public void roboEmail(Person p){
		System.out.println("EMailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getEmail());
	}

	public void roboMail(Person p){
		System.out.println("Mailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getAddress());
	}

}


public class LambdaTests {

	@Test
	public void PredicateTest() {
		List<Person> pl = Person.createShortList();
		RoboContactLambda robo = new RoboContactLambda();
	    
		//
	    // 	Predicate is a function interface, others include
		//	Consumer, Supplier, Function, UnaryOperator, BinaryOperator
		//	see this link:
		//	http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html
		//
	    Predicate<Person> allDrivers = p -> p.getAge() >= 16;
	    Predicate<Person> allDraftees = p -> p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
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
	public void StreamTest(){
		List<Person> people = Person.createShortList();
		people.stream().filter(p->p.getAge()>=45&&p.getGender()==Gender.MALE).forEach(System.out::println);
	}
	
	
	//stream is discharged after being used. To keep a mutable list, we will create a list copy
	//this is what collect is used for
	@Test
	public void CollectTest(){
		List<Person> people = Person.createShortList();
		List<Person> newGroup = people.stream().filter(p->p.getAge()<45).collect(Collectors.toList());
		newGroup.forEach(System.out::println); //method reference
	}
}
