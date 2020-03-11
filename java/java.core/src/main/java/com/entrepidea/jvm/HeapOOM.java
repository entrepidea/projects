package com.entrepidea.jvm;
import java.util.List;
import java.util.ArrayList;

/**
 *  @Desc:
 *  This class is run to create a Out-Of_Memory error deliberately.
 *
 *  Run this program with these arguments: -Xmx20m -Xms20m XX:+HeapDumpOnOutOfMemoryError
 *
 *  After the program is run, a heap dump file is generated and can be opened using Eclipse Memory Analyzer (which can be downloaded as a standalone utility)
 *
 *  @Source is from: 深入理解Java虚拟机, 2.4.1
 *
 *  @Date: 10/17/19
 *
 * */

public class HeapOOM{
    static class OOMObject{}
    public static void main(String[] args){
        List<OOMObject> l = new ArrayList<>();
        while(true){
            l.add(new OOMObject());
        }
    }
}