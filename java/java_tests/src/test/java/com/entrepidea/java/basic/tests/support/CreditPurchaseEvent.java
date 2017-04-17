package com.entrepidea.java.basic.tests.support;

/**
 * Created by jonat on 1/10/2017.
 */
public class CreditPurchaseEvent extends PurchaseEvent {
    int amount;
    String cardNumber;
    public CreditPurchaseEvent(String item,int amount, String cardNumber){
        super(item);
        this.amount = amount;
        this.cardNumber = cardNumber;
    }
}