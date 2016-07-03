package com.entrepidea.java.basic;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import junit.framework.Assert;


/**
 * @desc: 
 * this is to test the serializable mark interface.
 * Seriabliable interface indicate the implementing class is for persistence and network transport.
 * 
 * @link: ...
 * 
 * */
public class SerializableTests {

	//FOO, a dummy class used for testing serializable and externizable interfaces.
	public static class Foo implements Serializable {
		private static final long serialVersionUID = -2910515220268212971L;
		private int id;
		private String name;
		
		
		public Foo(int i, String n){
			id = i;
			name = n;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
	@Test
	public void writeTest() throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("foo.ser"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(new Foo(1, "john"));
		oos.close();
		fos.close();
	}
	@Test
	public void readTest() throws ClassNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(new File("foo.ser"));
		ObjectInputStream ois = new ObjectInputStream(fis);
		Foo foo = (Foo)ois.readObject();
		Assert.assertEquals(1, foo.getId());
		Assert.assertEquals(true, "john".equals(foo.getName()));
		ois.close();
		fis.close();
	}
	
	//Test to persist a link list
	@Test
	public void LinkListTest() throws IOException, ClassNotFoundException {
		List<Foo> foos = new ArrayList<>();
		for(int i=0;i<10;i++){
			foos.add(new Foo(i, "test_"+i));
		}
		FileOutputStream fos = new FileOutputStream(new File("LinkofFoo.ser"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(foos);
		
		FileInputStream fis = new FileInputStream("LinkofFoo.ser");
		ObjectInputStream ois = new ObjectInputStream(fis);
		@SuppressWarnings("unchecked")
		List<Foo> l = (List<Foo>)ois.readObject();
		Assert.assertEquals(10, l.size());
		for(Foo f : l){
			System.out.println(f.getId()+", "+f.getName());
			
		}
		
	}
	
	
	//test - serialization process will break if a serializable object has a non-serializable member
	//also see discussion about how to use Junit to assert an expected exception:
	//http://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests
	public static class NonSeri {
		private String dummmy;

		public String getDummmy() {
			return dummmy;
		}

		public void setDummmy(String dummmy) {
			this.dummmy = dummmy;
		}
	}
	public static class Foo2 extends Foo {
		NonSeri nonSeri;

		public Foo2(int i, String n, String dummy) {
			super(i, n);
			if(nonSeri==null){
				nonSeri = new NonSeri();
			}
			nonSeri.setDummmy(dummy);
		}
		
		public NonSeri getNonSeri(){
			return nonSeri;
		}
		public void setNonSeri(NonSeri ns){
			nonSeri = ns;
		}
		
		public String getDummy(){
			return nonSeri==null?null:nonSeri.getDummmy();
		}
	}
	
	@Test(expected=NotSerializableException.class)
	public void nonSeriMemTest() throws IOException {
		FileOutputStream fos= new FileOutputStream(new File("nonSeri.ser"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Foo2 f = new Foo2(1, "test", "dummy");
		oos.writeObject(f); //this line will break as NonSeri is a non-serializable member in Foo2 class. 
	}
	
	
	//test - intercept JVM's process of serialization/de-serialization by implementing two private methods
	//readObject & writeObject.
	public static class Foo3 implements Serializable{
		private static final long serialVersionUID = 357193156633015429L;
		
		private void readObject(java.io.ObjectInputStream stream)
			     throws IOException, ClassNotFoundException{
			System.out.println("intecepted: => read object.");
			stream.defaultReadObject();
		};
		
		private void writeObject(java.io.ObjectOutputStream stream)
			     throws IOException{
			System.out.println("intecepted: => write object.");
			stream.defaultWriteObject();
		};
	}
	
	@Test
	public void readWriteObjectTest() throws IOException{
		Foo3 f3 = new Foo3();
		ByteOutputStream bos = new ByteOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(f3);
	}
}
