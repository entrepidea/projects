package com.entrepidea.java.concurrency.basics;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Desc:
 * If an object is immutable, it means its states (invariants) are not updatable thus can be safely used in multi-threading environment, no synchronization is required.
 * Examples include "String", Integer, Double, Float, Boolean or any value objects.
 * @Interviews:
 *
 *  1. 2nd round on-site with Wells Fargo, 12/03/19
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
public class ImmutableObjectTests {
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

}
