package com.entrepidea.java.objectpool;

public abstract class PersistenceManager {
	private static PersistenceManager defaultManager;

	public static PersistenceManager getDefault() {
		if (defaultManager == null) {
			defaultManager = new NetBeansPersistenceManager();
		}
		return defaultManager;
	}
	
	public abstract QuotePool getQuotePool();	
}
