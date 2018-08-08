package com.entrepidea.algo.tests.array;

import java.util.ArrayDeque;
import java.util.Deque;

//2. find out if a string brackets come in pairs. For example, "[{}]{([])}" will return true, but "[(])" will return false;
//Barcap onsite interview, 10/09/14
public class BracketPairs {

    public static int[] isBracket(char c, char[][] bracketPairs){
        int[] ret = new int[2];
        for(int i=0;i<bracketPairs.length;i++){
          for(int j=0;j<bracketPairs[0].length;j++){
              if(c==bracketPairs[i][j]){
                  ret[0] = i; ret[1] = j;
                  return ret;
              }
          }
        }
        ret[0] = ret[1] = -1;
        return ret;
    };

    public static boolean found(int[] relt){
        return !(relt[0]==-1 && relt[1] == -1);
    }

    public static void main(String[] args){
        //create a two dimension array for brackets.
        char[][] bracketPairs = new char[3][2];
        bracketPairs[0][0] = '{';bracketPairs[0][1] = '}';
        bracketPairs[1][0] = '[';bracketPairs[1][1] = ']';
        bracketPairs[2][0] = '(';bracketPairs[2][1] = ')';
        //create a stack, Deque is preferrable
        Deque<Character> stack = new ArrayDeque<>();

        String testStr = "[{balalalalalal}]lllalalala{lalalgdfgreferfre([])}";

        //test2, this string will break!
        testStr = "[(])";

        char[] chars = testStr.toCharArray();

        for(char c : chars){
            int[] relt = isBracket(c, bracketPairs);
            if(found(relt) && relt[1]==1){ //right hand bracket found!
                //pop up stack and compare
                if(!stack.isEmpty()){
                    char b = stack.pop();
                    int[] r2 = isBracket(b, bracketPairs);
                    if(r2[0]==relt[0] && r2[1]==0)
                    {
                        continue;
                    }
                    else{
                        System.out.print("'"+testStr+ "' ==> brackets DON'T come in pairs, break!");
                        System.exit(1);
                    }
                }
            }
            else{
                if(found(relt) && relt[1]==0) { //left hand bracket found!
                    stack.push(c);
                }
            }
        }
        System.out.print("'"+testStr + "' ==> brackets come in pair, awesome!");

    }
}
