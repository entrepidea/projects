package com.entrepidea.java.basic.tests.support;

/**
 * Created by jonat on 1/10/2017.
 */
public abstract class PurchaseEvent {
    String item;
    public PurchaseEvent(String item){
        this.item = item;
    }
}