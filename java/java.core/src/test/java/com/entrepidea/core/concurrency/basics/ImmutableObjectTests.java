package com.entrepidea.core.concurrency.basics;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Desc:
 * If an object is immutable, it means its states (invariants) are not updatable thus can be safely used in multi-threading environment, no synchronization is required.
 * Examples include "String", Integer, Double, Float, Boolean or any value objects.
 *
 * @Date: 01/29/20
 *
 */


public class ImmutableObjectTests {

    /**
     *
     * 2nd round on-site with Wells Fargo, 12/03/19
     *      class Book{
     *          List<Page> pages;
     *      }
     *
     *      class Page {
     *          private String[] lines;
     *      }
     *
     *      You have complete control of writing the above two classes, what is that you need to do to make Book class immutable?
     *
     *
     * */
    static class Book{
        private final List<Page> pages;

        public Book(List<Page> pages) {
            this.pages = pages;
        }

        public Iterator<Page> getPages(){ //the iterator of a CopyOnWriterArrayList returns a snapshot of the underlying data structure, therefore will not be tainted by other thread.
            return new CopyOnWriteArrayList<>(pages).iterator();
        }
    }

    static final class Page {
        private final String[] lines;

        public Page(String[] lines) {
            this.lines = lines;
        }

        public String[] getLines(){
            return lines;
        }
    }

    @Test
    public void test(){
        Page page = new Page(new String[]{"hello", "world"});
        List<Page> pages = new ArrayList<>();
        pages.add(page);
        Book book = new Book(pages);
        for(Iterator<Page> pageIter = book.getPages();pageIter.hasNext();){
            for(String line: pageIter.next().getLines()){
                System.out.println(line);
            };
        }
    }

    /**
     * Goldman Sachs onsite interview, 01/27/20, 11:00AM
     * Write a customized array class, that can't be mutable in any way (can't change the element, can't increase or decrease the length, etc).
     * To make it more relatable, think of an array that include all states of the U.S. 
     *
     * */
    // An array in Java normally can be accessed using its [] operator, like arr[0].
    // However since Java doesn't support operator overriding, we can use "get" for that.
    // Can't figure out a viable way doing iteration though. Just have to use goodie-oldie for(). This has to be documented as lib writer
    // Can't find a viable way preventing from passing an array of mutable objects into the constructor either.
    static class ImmutableArray {
        private final String[] buf;

        ImmutableArray(String[] arr){
            buf = arr;
        }

        public String get(int idx){
            return buf[idx];
        }

        public int size(){
            return buf.length;
        }
    }

    @Test
    public void test2(){
        ImmutableArray arr = new ImmutableArray(new String[] {"AK","NJ","NW","NY"});
        String nj = arr.get(1); //get a reference of the element indexing with 1
        Assert.assertTrue(nj.equals("NJ"));
        nj = "UK"; //the reference has been reassigned to point to another String instance. This is allowable.
        Assert.assertTrue(nj.equals("UK"));
        // The array remains immutable, as shown in the iteration below
        for(int i=0;i<arr.size();i++){
            System.out.println(arr.get(i));
        }
    }

}
