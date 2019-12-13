package com.entrepidea.java.basic;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a collection of Regular expression tests
 * In java, two classes of importance to resolve regex issues:
 * Pattern & Matcher
 * */
public class RegexTests {
    @Test
    public void testSplit(){
        String testStr = "Hello, Ellen!";
        String[] rets= testStr.split("[eE]");
        for(String s : rets){
            System.out.println(s);
        }
    }

    //3 rounds of Interview with Neuberger Berman
    //TODO 4. write code to detect invalid email address
    //be noted the follow code snippet didn't work, I found the pattern in this post:
    //https://stackoverflow.com/questions/8204680/java-regex-email
    @Test
    public void testEmailVaildator(){
        Pattern pattern = Pattern.compile("\\\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,4}\\\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("my@yahoo.com");
        Assert.assertTrue((matcher.matches()));
    }
}
