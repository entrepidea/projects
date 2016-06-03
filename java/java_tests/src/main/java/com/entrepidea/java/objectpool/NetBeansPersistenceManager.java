package com.entrepidea.java.objectpool;

public class NetBeansPersistenceManager extends PersistenceManager {
	   private QuotePool quotePool = new QuotePool();

	   public final QuotePool getQuotePool() {
	        return quotePool;
	    }
}
