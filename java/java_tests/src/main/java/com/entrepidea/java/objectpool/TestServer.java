package com.entrepidea.java.objectpool;

public class TestServer {

	/**
	 * @param args
	 */
	private final static QuotePool quotePool = PersistenceManager.getDefault().getQuotePool();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Quote q = quotePool.borrowObject();
		q.reset();
		System.out.println("open price: "+q.getOpen());
		quotePool.returnObject(q);
	}

}
