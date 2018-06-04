package console.algo;

/**
 * Question A1 : write a function Fibonacci(int N) for a positive N. You are given the formula F(0)=0, F(1)=1, F(N) = F(N-1) + F(N-2)
 * @source: c++/algo/SQL IV questions - BAML, 10/11/17, courtesy of Tanbin
 * */
public class Fibonacci {
    private static int fib(int n){
        if(n==0 || n ==1 ){
            return n;
        }
        else{
            return fib(n-1)+fib(n-2);
        }
    }
    public static void main(String[] args){
        System.out.println(fib(6));
    }
}

//TODO: find other non recursive solutions.
/*
* @note from Tanbin:
*
I decided to avoid recursion altogether. My iterative solution has a
caching feature (static local variable), but if we must achieve O(1)
space complexity (caching would be impossible), then there's a very
nice recursive solution.

* */