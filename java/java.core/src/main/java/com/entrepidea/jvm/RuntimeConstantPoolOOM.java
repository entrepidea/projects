package com.entrepidea.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Source: 深入理解Java虚拟机：JVM高级特性与最佳实践（第2版）


 * Created by jonat on 10/18/2019.
 * TODO need revisit - the code seem just running - supposedly it should end soon with exceptions.
 */
public class RuntimeConstantPoolOOM {

    public static void main(String[] args){
        List<String> l = new ArrayList<>();
        int i=0;
        while(true){
            l.add(String.valueOf(i++).intern());
        }
    }
}
