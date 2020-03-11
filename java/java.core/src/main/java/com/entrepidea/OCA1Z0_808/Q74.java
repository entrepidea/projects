package com.entrepidea.OCA1Z0_808;

class Vehicle{
    int x ;
    Vehicle(){
        this(10);
    }
    Vehicle(int x ){
        this.x = x;
    }
}

class Car extends Vehicle{
    int y;
    Car(){
        //super(); //this call is not necessary, default constructor in super class is called automatically.
        this(20);
    }
    Car(int y){
        this.y = y;
    }

    @Override
    public String toString() {
        return "X="+super.x+" Y="+this.y;
    }
}
public class Q74 {

    public static void main(String[] args){
        Vehicle v = new Car();
        System.out.println(v);
    }

}
