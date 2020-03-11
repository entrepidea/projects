package com.entrepidea.core.basic.support;

import com.google.common.eventbus.Subscribe;

/**
 * Created by jonat on 1/10/2017.
 */
public class CashPurchaseEventSubscriber {
    @Subscribe
    public void handlePurchaseEvent(com.entrepidea.core.basic.support.PurchaseEvent event) {
       //.....
    }
}
