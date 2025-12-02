package com.javapython.algo;
import java.util.Scanner;

public class FactorialTests {
    public static void main(String[] args){
//        Testing t = new Testing();
        int n = 5;
        System.out.println("The factorial of " + n + " is: " + factorial(n));
        n = 10;
        System.out.println("The factorial of " + n + " is: " + factorial2(n));
        //JDBC
        n = 8;
        System.out.println("The factorial of " + n + " is: " + factorial3(n));
        //Recursion
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print("enter here: ");
            input = scanner.nextLine();
            if (input.isEmpty() || input.equalsIgnoreCase("q"))
                break;
            System.out.println("You entered: " + input);
        }
    }
    public static int factorial(int n){
        int index=1;
        int temp = n;
        while(index<temp){
            if(n==0) return 1;
            n *= (temp-index);
            index++;
        }
        return n;
    }

    public static int factorial2(int n){
        int ret = 1;
        if (n==0) return 1;
        for(int i=1;i<=n;i++){
            ret *= i;
        }
        return ret;
    }

    public static int factorial3(int n){
        for (int i=n-1;i>0;i--){
            n *= i;
        }
        return n;
    }

}
