package com.entrepidea.java.objectpool;

/**
 * A simple base impementation of {@link ObjectPool}.
 * All optional operations are implemented as throwing
 * {@link UnsupportedOperationException}.
 *
 * @author Rodney Waldhoff
 * @version $Revision: 1.1 $ $Date: 2013-11-07 05:04:45 $
 */
public abstract class BaseObjectPool<T> implements ObjectPool<T> {
    public abstract T borrowObject() throws RuntimeException;
    public abstract void returnObject(T obj) throws RuntimeException;
    public abstract void invalidateObject(T obj) throws RuntimeException;

    /**
     * Not supported in this base implementation.
     */
    public int getNumIdle() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported in this base implementation.
     */
    public int getNumActive() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported in this base implementation.
     */
    public void clear() throws Exception, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Not supported in this base implementation.
     */
    public void addObject() throws RuntimeException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void close() throws Exception {
        assertOpen();
        closed = true;
    }

    /**
     * Not supported in this base implementation.
     */
    public void setFactory(PoolableObjectFactory<T> factory) throws IllegalStateException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    
    protected final boolean isClosed() {
        return closed;
    }
    
    protected final void assertOpen() throws IllegalStateException {
        if(isClosed()) {
            throw new IllegalStateException("Pool not open");
        }
    }
    
    private volatile boolean closed = false;
}

