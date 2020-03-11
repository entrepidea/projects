package com.entrepidea.core.features.v1_8;

import org.junit.Test;
import static org.junit.Assert.assertSame;
//everything else in new features of v1.8
public class MiscTests {

    //Constructor reference
    class Person{
        private String firstName;
        private String lastName;

        Person(){}
        Person(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName(){
            return firstName;
        }
        public String getLastName(){
            return lastName;
        }
    }
    interface PersonFactory<P extends Person>{
        P create(String firstName, String lastName);
    }

    @Test
    public void testConstructorReference(){
        PersonFactory<Person> factory = Person::new;
        Person p = factory.create("Peter","Parker"); //compile would be smart to pick up the right constructor of Person class.
        assertSame("must be same", p.getFirstName(),"Peter");
    }
}
