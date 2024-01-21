package com.entrepidea.evolution.v21;

import org.junit.Assert;
import org.junit.Test;
/*
* Record is a transparent carrier of data.
* https://openjdk.org/jeps/440
*
* 01/20/24
*
* */
public class RecordTests {
    record Point(int x, int y){}
    //below semantics is supported by V21
    private int printSumV21(Object obj){
        if(obj instanceof Point(int x,int y)){
            return x + y;
        }
        return 0;
    }
    @Test
    public void testRecordV21(){
        Point p = new Point(4,5);
        Assert.assertEquals(printSumV21(p), 9);
    }

    //below semantics is supported by V16
    private int printSumV16(Object obj){
        if(obj instanceof Point p){
            return p.x()+p.y();
        }
        return 0;
    }
    @Test
    public void testRecordV16(){
        Assert.assertEquals(printSumV16(new Point(4,5)), 9);
    }
}
