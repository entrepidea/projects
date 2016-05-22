package com.entrepidea.java.basic;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

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
		FileOutputStream fos = new FileOutputStream(new File("resources/data/Foo.ser"));
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(new Foo(1, "john"));
		oos.close();
		fos.close();
	}
	@Test
	public void readTest() throws ClassNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(new File("resources/data/foo.ser"));
		ObjectInputStream ois = new ObjectInputStream(fis);
		Foo foo = (Foo)ois.readObject();
		Assert.assertEquals(1, foo.getId());
		Assert.assertEquals(true, "john".equals(foo.getName()));
		ois.close();
		fis.close();
	}

}
