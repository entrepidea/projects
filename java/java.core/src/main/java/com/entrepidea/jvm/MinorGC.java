package com.entrepidea.jvm;

/**
 * @Desc:
 * the VM parameters: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * The above setting tells JVM that heap is not extendable; Young generation is 10M, tenured generation 10M; Eden is 8M and two surviror spaces are 1M for each.
 * When it come to allocating alloc4, not enough space for Eden, minor GC took place. alloc1,2,3 should be moved to tenured.
 * TODO: still have to analyze the gc log.
 *
 * @Source: 深入理解Java虚拟机, 3rd Ed, 3.8.1
 *
 * @Date: 04/12/20
 *
 * */
public class MinorGC {

    private final static int _1MB = 1024*1024;
    private static void testAllocation(){
        byte[] alloc1, alloc2, alloc3, alloc4;
        alloc1 = new byte[2*_1MB];
        alloc2 = new byte[2*_1MB];
        alloc3 = new byte[2*_1MB];
        alloc4 = new byte[4*_1MB];

    }
    public static void main(String[] args){
        testAllocation();
    }
}
