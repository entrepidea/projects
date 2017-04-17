package com.entrepidea.java.basic.tests.support;

import com.google.common.eventbus.Subscribe;

/**
 * Created by jonat on 1/10/2017.
 */
public class CreditPurchaseSubscriber {
    @Subscribe
    public void handleCreditPurchase(CreditPurchaseEvent event) {
     //....
    }
}