package com.entrepidea.aaron_corner;

public class PasswordHelp {
    protected static final String password = "fireBrilliant(??]";
    public static String passwordGuess = "fireBrilliant(??]";
    public static void main(String[] args) {
        boolean correctPassword;
        if(passwordGuess.equals(password)){
            correctPassword = true;
            System.out.println(correctPassword);
        } else {
            correctPassword = false;
            System.out.println(correctPassword);
        }
    }
}
