package com.entrepidea.evolution.v8;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class NewKeywordTests {
    //10/01/14, 5:30PM, BofA phone interview with Wilson
    //13. What's the new feature in JDK 8? What's the "default" keyword used for in JDK 8 context?
    //answer: Since Java 8 interface can have default concrete method implementation. Such a method is decorated with "default" keyword
    interface Fomula{
        double calculate(int n);
        default double sqrt(int a){
            return Math.sqrt(a);
        }
    }

    @Test
    public void testDefaultMethod(){
        Fomula fomula = new Fomula() {
            @Override
            public double calculate(int n) {
                return sqrt(n*100);
            }
        };
        assertEquals(fomula.calculate(100),100,0);
    }
}
