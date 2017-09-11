package com.entrepidea.java.concurrency.tests.basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Meal{
	private final int orderNum;
	public Meal(int orderNum){this.orderNum = orderNum;}
	public String toString(){
		return "Mean #: " + orderNum;
	}
}

class WaitPerson implements Runnable{
	private Restaurant restaurant;
	public WaitPerson(Restaurant r ){
		restaurant = r;
	}
	public void run(){
		while(!Thread.interrupted()){
			try{
				if(restaurant.meal==null){// no meal now
					synchronized(this){
						wait();
					}
				}
				synchronized(restaurant.chef){
					restaurant.meal = null;
					restaurant.chef.notifyAll();
				}
			}
			catch(InterruptedException e){
				//e.printStackTrace();
				System.out.println("wait person interrupted. ");
			}
		}
		
	}
}

class Chef implements Runnable {
	private Restaurant restaurant;
	private int count=0;
	public Chef(Restaurant r){
		restaurant = r;
	}
	public void run(){
		try{
			while(!Thread.interrupted()){
				if(restaurant.meal!=null){//the meal hasn't been taken yet...
					synchronized(this){
						wait();
					}
				}
				if(++count==10){
					System.out.println("Out of food, closed!");
					restaurant.exec.shutdownNow();
				}
				System.out.println("Order up!");
				synchronized(restaurant.waitperson){
					restaurant.meal = new Meal(count);
					restaurant.waitperson.notifyAll();
				}
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}
		catch(InterruptedException e){
			//e.printStackTrace();
			System.out.println("Chef interrupted. ");
		}
	}
}
public class Restaurant {
	public Meal meal;
	public ExecutorService exec = Executors.newCachedThreadPool();
	public Chef chef = new Chef(this);
	public WaitPerson waitperson = new WaitPerson(this);
	
	public Restaurant(){
		exec.execute(chef);
		exec.execute(waitperson);
	}
	
	public static void main(String[] args){
		new Restaurant();
	}
	
	
}
