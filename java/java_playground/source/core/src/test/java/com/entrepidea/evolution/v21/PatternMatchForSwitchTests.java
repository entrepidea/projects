package com.entrepidea.evolution.v21;

import org.junit.Test;

/*
* enhancement for pattern match for Switch statement.
* https://openjdk.org/jeps/441
*
* 01/21/24
*
* */
public class PatternMatchForSwitchTests {
    private void fooBarNew(String s) {
        switch (s) {
            case null         -> System.out.println("Oops");
            case "Foo", "Bar" -> System.out.println("Great");
            default           -> System.out.println("Ok");
        }
    }

    @Test
    public void testNullString(){
        fooBarNew(null);
        fooBarNew("Foo");
        fooBarNew("Other");
    }

    private void stringEnhanced(String response) {
        switch (response) {
            case null -> { }
            case "y", "Y" -> {
                System.out.println("You got it");
            }
            case "n", "N" -> {
                System.out.println("Shame");
            }
            case String s
                    when s.equalsIgnoreCase("YES") -> {
                System.out.println("You got it");
            }
            case String s
                    when s.equalsIgnoreCase("NO") -> {
                System.out.println("Shame");
            }
            case String s -> {
                System.out.println("Sorry?");
            }
        }
    }

    @Test
    public void testStringMatchForSwitch(){
        stringEnhanced("Y");
        stringEnhanced("N");
        stringEnhanced("Yes");
        stringEnhanced("No");
        stringEnhanced("Gibberish");
    }
}
