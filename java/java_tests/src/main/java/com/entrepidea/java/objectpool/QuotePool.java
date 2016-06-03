package com.entrepidea.java.objectpool;

public class QuotePool extends StackObjectPool<Quote> implements PoolableObjectFactory<Quote> {

    public QuotePool() {
        super(500, 200);
        setFactory(this);
    }
    
    public void activateObject(Quote obj) throws RuntimeException {
        obj.reset();
    }
    
    public void destroyObject(Quote obj) throws RuntimeException {
        obj = null;
    }
    
    public Quote makeObject() throws RuntimeException {
        return new Quote();
    }
    
    public void passivateObject(Quote obj) throws RuntimeException {
    }
    
    public boolean validateObject(Quote obj) {
        return true;
    }
    
    
}


