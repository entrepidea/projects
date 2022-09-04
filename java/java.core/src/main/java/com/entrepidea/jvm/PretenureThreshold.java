package com.entrepidea.jersey.jvm;
/**
 * @Desc:
 *-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 * Given the above VM settings, the young generation is limited to 3MB so that the below allocation (4MB) should be put in tenured generation
 *
 * @Source: 深入理解Java虚拟机, 3rd Ed, 3.8.2
 *
 * @Date: 04/12/20
 *
 * */
public class PretenureThreshold {
    private static final int _1MB = 1024*1024;
    public static void main(String[] args){
        byte[] allc = new byte[4*_1MB];
    }
}
