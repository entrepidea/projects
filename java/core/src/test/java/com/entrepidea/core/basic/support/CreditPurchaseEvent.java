package com.entrepidea.core.basic.support;

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