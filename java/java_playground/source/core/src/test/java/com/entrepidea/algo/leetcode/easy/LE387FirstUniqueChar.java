package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Desc:
 * Find the first unique character from a string.
 * @Date: 04/18/20
 * */
public class LE387FirstUniqueChar {
    /*
    * This solution takes advantage of LinkedHashMap's feature that its keys are ordered by the insertion order.
    * */
    private int firstUnique(String s){
        char[] chars = s.toCharArray();
        Map<Character, Integer> map = new LinkedHashMap<>();
        //how to create a char stream: https://stackoverflow.com/questions/31554025/want-to-create-a-stream-of-characters-from-char-array-in-java
        Stream<Character> cStream = IntStream.range(0,chars.length).mapToObj(i->chars[i]);
        cStream.forEach(c->map.put(c,map.getOrDefault(c,0)+1));
        char foundChar = map.keySet().stream().filter(k ->map.get(k)==1).findFirst().get();
        int count = 0;
        for(char c : chars){
            if(c==foundChar){
                break;
            }
            count++;
        }
        return count;
    }

    @Test
    public void test(){
        Assert.assertEquals(firstUnique("leetcode"),0);//l
        Assert.assertEquals(firstUnique("loveleetcode"),2);//v
        Assert.assertEquals(firstUnique("love"),0);//l
        Assert.assertEquals(firstUnique("lovsosleetcodev"),9); //t


    }
}
