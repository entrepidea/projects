package com.entrepidea.core.basic;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringTests {

    ////Morgan Stanley phone interview, 05/14/18
    //TODO Is String immutable? what's the benefit of immutable class? Why is String immutable?
    /*String is immutable. Being immutable, a class is secure, thread-safe and somehow performant since its value can be cached.
    That a String being immutable is pretty much a design decision. Most important, security. Since String is used as parameters
    in occasions such as url connection, db connection and reflection to restore a class object.
    see more from: https://stackoverflow.com/questions/22397861/why-is-string-immutable-in-java
    */

    //10/01/14, 5:30PM, BofA phone interview with Wilson
    //TODO 7. Difference b/w StringBuffer and StringBuilder; and their difference with String?


    /*
        @KH: this is supposed to be a better to check if a string is empty after being trimmed.
        normally we might use str.trim().isEmpty() to check, but according to this:
        https://javadoc.io/doc/net.sourceforge.pmd/pmd-java/6.19.0/net/sourceforge/pmd/lang/java/rule/performance/InefficientEmptyStringCheckRule.html
        it's deemed to be inefficient.
        04/27/23

     */
    private boolean isTrimEmpty(String str) {
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void testEmptyStr(){
        String str = "      ";
        assertTrue(isTrimEmpty(str));

        str = "";
        assertTrue(isTrimEmpty(str));

        str = "hello      ";
        assertFalse(isTrimEmpty(str));


    }
}
