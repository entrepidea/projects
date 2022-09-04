package com.entrepidea.jersey.jvm;

/**
 * @Desc:
 * create a StackOverflowError deliberately. VM args: -Xss128k
 *
 *
 * @Source: 深入理解Java虚拟机, 2.4.2
 *
 * @Date: 10/17/19
 *
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable{
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try{
            oom.stackLeak();
        }
        catch(Throwable e){
            System.out.println("stack length: "+oom.stackLength);
            throw e;
        }
    }
}
