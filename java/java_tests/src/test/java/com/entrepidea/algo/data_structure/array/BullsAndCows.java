//Source: https://leetcode.com/problems/bulls-and-cows/
//You are playing the following Bulls and Cows game with your friend: You write down a number and ask your friend to guess what the number is. Each time your friend makes a guess, you provide a hint that indicates how many digits in said guess match your secret number exactly in both digit and position (called "bulls") and how many digits match the secret number but locate in the wrong position (called "cows"). Your friend will use successive guesses and hints to eventually derive the secret number.

//For example:

//Secret number:  "1807"
//Friend's guess: "7810"
//Hint: 1 bull and 3 cows. (The bull is 8, the cows are 0, 1 and 7.)
//Write a function to return a hint according to the secret number and friend's guess, use A to indicate the bulls and B to indicate the cows. In the above example, your function should return "1A3B". 

//Please note that both secret number and friend's guess may contain duplicate digits, for example:

//Secret number:  "1123"
//Friend's guess: "0111"
//In this case, the 1st 1 in friend's guess is a bull, the 2nd or 3rd 1 is a cow, and your function should return "1A1B".
//You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.

//Credits:
//Special thanks to @jeantimex for adding this problem and creating all checkBalancedBinaryTree cases.

package com.entrepidea.algo.data_structure.array;

public class BullsAndCows {
    public String getHint(String secret, String guess) {
        int bulls = 0;
        int cows = 0;
        int[] cSecret = new int[10]; //count the appearances of digit appears but in wrong places
        int[] cGuess = new int[10];
        
        int dSecret, dGuess;
        for (int i = 0; i < secret.length(); i++) {
            dSecret = (secret.charAt(i) - '0') % 10;
            dGuess = (guess.charAt(i) - '0') % 10;
            if (dSecret == dGuess)
               bulls++;
            else { //the two digits do not match at the same position
                  cSecret[dSecret]++;
                  cGuess[dGuess]++;
            }
        }
        
        for (int i = 0; i < cSecret.length; i++)
            cows += Math.min(cSecret[i], cGuess[i]);
            
        return "" + bulls + "A" + cows + "B";
    }
}