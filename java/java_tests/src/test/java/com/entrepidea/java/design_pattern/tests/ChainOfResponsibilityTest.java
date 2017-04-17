package com.entrepidea.java.design_pattern.tests;

import org.junit.Test;

/**
 * 
 * @author Hai Yi
 * @description this is to demo Chain of Responsibility pattern. This pattern consists a source of command
 * objects and a series of processing objects. Each processing object contains a set of rule for the command
 * objects that it can handle, and pass those that it can't to the next processing object. 
 *
 */
abstract class Logger{
	public static int ERR = 3;
    public static int NOTICE = 5;
    public static int DEBUG = 7;
    protected int mask;
 
    // The next element in the chain of responsibility
    protected Logger next;
    public Logger setNext( Logger l )
    {
        next = l;
        return l;
    }
 
    public void message( String msg, int priority )
    {
        if ( priority <= mask ) 
        {
            writeMessage( msg );
        }
        if ( next != null )
        {
            next.message( msg, priority );
        }
    }
 
    abstract protected void writeMessage( String msg );
}

class StdoutLogger extends Logger 
{
 
    public StdoutLogger( int mask ) { this.mask = mask; }
 
    protected void writeMessage( String msg )
    {
        System.out.println( "Writing to stdout: " + msg );
    }
}
 
 
class EmailLogger extends Logger 
{
 
    public EmailLogger( int mask ) { this.mask = mask; }
 
    protected void writeMessage( String msg )
    {
        System.out.println( "Sending via email: " + msg );
    }
}
 
class StderrLogger extends Logger 
{
 
    public StderrLogger( int mask ) { this.mask = mask; }
 
    protected void writeMessage( String msg )
    {
        System.err.println( "Sending to stderr: " + msg );
    }
}

public class ChainOfResponsibilityTest {

    @Test
	public void test() {
		Logger l, ll;
		ll = l = new StdoutLogger(Logger.DEBUG);
		ll = ll.setNext(new EmailLogger(Logger.NOTICE));
		ll = ll.setNext(new StderrLogger(Logger.ERR));
		
        // Handled by StdoutLogger
        l.message( "Entering function y.", Logger.DEBUG );
 
        // Handled by StdoutLogger and EmailLogger
        l.message( "Step1 completed.", Logger.NOTICE );
 
        // Handled by all three loggers
        l.message( "An error has occurred.", Logger.ERR );


	}

}
