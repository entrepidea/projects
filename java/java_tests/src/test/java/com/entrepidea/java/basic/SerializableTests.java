package com.entrepidea.java.basic;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;

/**
 * Test cases for serialization/de-serialization
 */
public class SerializableTests {

    /*
    * TODO (BNP) Explain Serializable. Example:
    *
        class Key implements Serializable{
            private String id;
            private Object value;
            private String name;
            //...  getter and setter
            //..hashCode and equals...
        }

        Key k1 = new Key("id", (Object)("value"), "john");
        Key k2 = new Key("id", "value", "john");

        Will the above two lines of code be compiled and persisted as expected? answer: yes

        Note#1: I guess the interviewer intended to ask: if the variable value is Object type, after the class is serialized, change the value variable to String,
        will the file can be de-serialized back. The answer is yes. As long as the Object and String are inherited related.

        Note#2: the serialVersionUID MUST remain the same before and after the class fields update

    * */
    static class Key implements Serializable {
        static final long serialVersionUID = 41224322L;

        public String id;
        public Number value;
        public String name;
    }
    //note: i got "NotSerializableException" first because the Key class was a inner class, the error went away after I added static to make it an independent class.
    //explanation: "serializing such an inner class instance will result in serialization of its associated outer class instance as well"
    @Test
    public void testSimple() throws IOException, ClassNotFoundException {
        //Key k = new Key();
        //k.value = 1000;
        //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\Users\\jonat\\projects\\temp\\key"));
        //oos.writeObject(k);

        ObjectInputStream ios = new ObjectInputStream(new FileInputStream("C:\\Users\\jonat\\projects\\temp\\key"));
        Key k2 = (Key)ios.readObject();
        Assert.assertEquals(k2.value.intValue(), 1000);
    }

    /**
     * Below checkBalancedBinaryTree. First the mArg2 wasn't there. The class Foo was serialized to foo. Then class Foo added a new variable mArg2, but
     * readExternal/writeExternal remain unchanged, in this case, the file foo can be de-serialized all right - because readExtenal/writeExtenal
     * are customized serializer, and only mArg1 participate.
     * However if readExternal/writeExternal are upgraded to include the reading/writing of mArg2, the de-serialization of file foo will fail with OperationalException
     * */
    static class Foo implements Externalizable {
        static final long serialVersionUID = 42L;
        public String mArg1;
        public String mArg2;

        public Foo() { }

        public void readExternal(ObjectInput in)
                throws IOException, ClassNotFoundException {
            mArg1 = (String) in.readObject();
//            mArg2 = (String) in.readObject();
        }
        public void writeExternal(ObjectOutput out)
                throws IOException {
            out.writeObject(mArg1);
            // out.writeObject(mArg2);
        }
    }

    @Test
    public void testBackwardCompability() throws IOException, ClassNotFoundException {
/*        FileOutputStream fos = new FileOutputStream("C:\\users\\jonat\\projects\\temp\\foo");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Foo foo = new Foo();
        foo.setArg1("hello");
        oos.writeObject(foo);*/

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\users\\jonat\\projects\\temp\\foo"));
        Foo foo= (Foo)ois.readObject();
        Assert.assertEquals(foo.mArg1, "hello");
        Assert.assertNull(foo.mArg2);

    }

    //TODO the checkBalancedBinaryTree below also works, why? I thought it would break, since we added a new mArg2 thus the default serialization form changes as a result.
    static class Foo2 implements Serializable {
        static final long serialVersionUID = 42L;
        public String mArg1;
        //public String mArg2;
        public Foo2(){};
    }


    @Test
    public void testBackwardCompability2() throws IOException, ClassNotFoundException {
        /*FileOutputStream fos = new FileOutputStream("C:\\users\\jonat\\projects\\temp\\foo2");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        Foo2 foo2 = new Foo2();
        foo2.mArg1 = "hello";
        foo2.mArg2 = "world";
        oos.writeObject(foo2);*/


        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("C:\\users\\jonat\\projects\\temp\\foo2"));
        Foo2 foo2 = (Foo2)ois.readObject();
        Assert.assertEquals(foo2.mArg1, "hello");
        //Assert.assertNull(foo2.mArg2);


    }


    /**
     *
     * 10/15/14, Markit on site
     * Serializable object, how to maintain its backward comparability? For example, we have a class like
     class Foo implements Serializable{
     A;
     B;
     }

     one instance is serialized, now the programmer changed Foo by adding one more member C, now the new Foo class looks like
     class Foo implements Serializable{
     A;
     B;
     C;
     }
     *
     * TODO question, the earlier persisted instance, can it be deserialized back to a Foo object? as the Foo class has been updated. If not, what will happen? And how can you make it work?
     *
     *
     * TODO Can a class with only primitive members be serialized?
     *
     * */

    //TODO 5. Can a class with only primitive members be serialized? (10/15/14, Markit on site)

    //TODO 3. What's serialization? How to customize serialization
    //10/08/14 phone interview with BNP Paribas, GWT UI developer position, Jersey City




    /*

    BNP Paribas onsite, Jersey City, GWT UI programmer position, 10/14/2014
    * TODO 10. Explain Serializable.
        Example:
            class Key implements Serializable{
             private String id;
             private Object value;
             private String name;

             //...  getter and setter

            //..hashCode and equals...

            }

            Key k1 = new Key("id", new Object("value"), "john");
            Key k2 = new Key("id", new "value", "john");

        Will the above two lines of code be compiled and persisted as expected?
    *
    * */



}
