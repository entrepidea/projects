package com.entrepidea.core.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;

class BookOrder implements Serializable {
	private String name;
	private int units;
	private double price;	

	BookOrder(String name, int units, double price){
		this.name = name;
		this.units = units;
		this.price = price;
	};
	public void printList(){
		System.out.println("Book Name: "+name);
		System.out.println("Unit(s): "+units);
		System.out.println("Price: "+price);
	};
}

public class ObjectIOTests {

	public static void main(String[] argv){
		BookOrder bo = new BookOrder("test1", 50,45.4);
		//write 
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("invoice.bin"));
			oos.writeObject(bo);
			oos.close();
		}catch(IOException e){e.printStackTrace();};
		
		//read		
		try{	
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream("invoice.bin"));
			while(oin.available()!=0){
				BookOrder in = (BookOrder)oin.readObject();
				in.printList();						
			}
		}
		catch(FileNotFoundException fnfe){fnfe.printStackTrace();}
		catch(ClassNotFoundException cnfe){cnfe.printStackTrace();}
		/*catch(EOFException eofe){eofe.printStackTrace();}*/
		catch(IOException ioe){ioe.printStackTrace();};
	}
}