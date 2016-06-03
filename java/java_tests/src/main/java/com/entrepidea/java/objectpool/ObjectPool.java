package com.entrepidea.java.objectpool;

/**
 * A pooling interface.
 * <p>
 * <code>ObjectPool</code> defines a trivially simple pooling interface. The only 
 * required methods are {@link #borrowObject borrowObject} and {@link #returnObject returnObject}.
 * <p>
 * Example of use:
 * <table border="1" cellspacing="0" cellpadding="3" align="center" bgcolor="#FFFFFF"><tr><td><pre>
 * Object obj = <font color="#0000CC">null</font>;
 * 
 * <font color="#0000CC">try</font> {
 *    obj = pool.borrowObject();
 *    <font color="#00CC00">//...use the object...</font>
 * } <font color="#0000CC">catch</font>(Exception e) {
 *    <font color="#00CC00">//...handle any exceptions...</font>
 * } <font color="#0000CC">finally</font> {
 *    <font color="#00CC00">// make sure the object is returned to the pool</font>
 *    <font color="#0000CC">if</font>(<font color="#0000CC">null</font> != obj) {
 *       pool.returnObject(obj);
 *    }
 * }</pre></td></tr></table>
 * See {@link org.apache.commons.pool.BaseObjectPool BaseObjectPool} for a simple base implementation.
 *
 * @author Rodney Waldhoff
 * @version $Revision: 1.1 $ $Date: 2013-11-07 05:04:45 $ 
 *
 */
public interface ObjectPool<T> {
    /**
     * Obtain an instance from my pool.
     * By contract, clients MUST return
     * the borrowed instance using
     * {@link #returnObject(java.lang.Object) returnObject}
     * or a related method as defined in an implementation
     * or sub-interface.
     * <p>
     * The behaviour of this method when the pool has been exhausted
     * is not specified (although it may be specified by implementations).
     *
     * @return an instance from my pool.
     */
    T borrowObject() throws RuntimeException;

    /**
     * Return an instance to my pool.
     * By contract, <i>obj</i> MUST have been obtained
     * using {@link #borrowObject() borrowObject}
     * or a related method as defined in an implementation
     * or sub-interface.
     *
     * @param obj a {@link #borrowObject borrowed} instance to be returned.
     */
    void returnObject(T obj) throws RuntimeException;

    /**
     * Invalidates an object from the pool
     * By contract, <i>obj</i> MUST have been obtained
     * using {@link #borrowObject() borrowObject}
     * or a related method as defined in an implementation
     * or sub-interface.
     * <p>
     * This method should be used when an object that has been borrowed
     * is determined (due to an exception or other problem) to be invalid.
     * If the connection should be validated before or after borrowing,
     * then the {@link PoolableObjectFactory#validateObject} method should be
     * used instead.
     *
     * @param obj a {@link #borrowObject borrowed} instance to be returned.
     */
    void invalidateObject(T obj) throws RuntimeException;

    /**
     * Create an object using my {@link #setFactory factory} or other
     * implementation dependent mechanism, and place it into the pool.
     * addObject() is useful for "pre-loading" a pool with idle objects.
     * (Optional operation).
     */
    void addObject() throws RuntimeException;

    /**
     * Return the number of instances
     * currently idle in my pool (optional operation).  
     * This may be considered an approximation of the number
     * of objects that can be {@link #borrowObject borrowed}
     * without creating any new instances.
     *
     * @return the number of instances currently idle in my pool
     * @throws UnsupportedOperationException if this implementation does not support the operation
     */
    int getNumIdle() throws UnsupportedOperationException;

    /**
     * Return the number of instances
     * currently borrowed from my pool 
     * (optional operation).
     *
     * @return the number of instances currently borrowed in my pool
     * @throws UnsupportedOperationException if this implementation does not support the operation
     */
    int getNumActive() throws UnsupportedOperationException;

    /**
     * Clears any objects sitting idle in the pool, releasing any
     * associated resources (optional operation).
     *
     * @throws UnsupportedOperationException if this implementation does not support the operation
     */
    void clear() throws Exception, UnsupportedOperationException;

    /**
     * Close this pool, and free any resources associated with it.
     */
    void close() throws Exception;

    /**
     * Sets the {@link PoolableObjectFactory factory} I use
     * to create new instances (optional operation).
     * @param factory the {@link PoolableObjectFactory} I use to create new instances.
     *
     * @throws IllegalStateException when the factory cannot be set at this time
     * @throws UnsupportedOperationException if this implementation does not support the operation
     */
    void setFactory(PoolableObjectFactory<T> factory) throws IllegalStateException, UnsupportedOperationException;
}
