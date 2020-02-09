package com.entrepidea.OCA1Z0_808;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
class Star {
    public void doStuff(){
        System.out.println("Twiking star");
    }
}

interface Universe {
    void doStuff();
}

class Sun extends Star implements Universe{
    public void doStuff(){
        System.out.println("Shinning sun");
    }
}


public class Test {
    private static void dispResult(int[] num){
        try{
            System.out.println(num[1]/(num[1]-num[2]));
        }
        catch(ArithmeticException e){
            System.out.println("first exception");
            e.printStackTrace();
        }
        System.out.println("done");
    }
    public static void main(String[] args){
        try {
            int[] arr = {100, 100};
            dispResult(arr);
        }
        catch(IllegalArgumentException e){
            System.out.println("second exception");
        }
        catch (Exception e){
            System.out.println("Third exception");
        }
    }
}
