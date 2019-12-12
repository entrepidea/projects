package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LE243ShortestWordsDistance {

    private int distance(String[] words, String word1, String word2){
        int minDis = words.length;
        Map<String, List<Integer>> map = new HashMap<>();
        map.put(word1,new ArrayList<>());
        map.put(word2,new ArrayList<>());
        for(int i=0;i<words.length;i++){
            if(map.containsKey(words[i])){
                List<Integer> val = map.get(words[i]);
                val.add(i);
                map.put(words[i],  val);
            }
        }
        for(int idx1: map.get(word1)){
            for(int idx2: map.get(word2)){
                if(Math.abs(idx1-idx2)<minDis){
                    minDis = Math.abs(idx1-idx2);
                }
            }
        }
        return minDis;
    }
    @Test
    public void test(){
        Assert.assertEquals(3, distance(new String[]{"practice", "makes", "perfect", "coding", "makes"},"coding", "practice"));
        Assert.assertEquals(1, distance(new String[]{"practice", "makes", "perfect", "coding", "makes"},"coding", "makes"));
    }
}
