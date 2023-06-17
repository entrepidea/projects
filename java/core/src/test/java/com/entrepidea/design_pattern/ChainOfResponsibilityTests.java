package com.entrepidea.design_pattern;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

public class ChainOfResponsibilityTests {

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



	//the 2nd test is based on the example from https://refactoring.guru/design-patterns/chain-of-responsibility/java/example
    //note: class server maintains a linked list of handlers(MiddleWare) and sequentially call it's "check" method until one of them return false or the end of the list is reached.
    //05/23/21

    class Server {
        private Map<String, String> users = new HashMap<>();
        private Middleware middleware;

        /**
         * Client passes a chain of object to server. This improves flexibility and
         * makes testing the server class easier.
         */
        public void setMiddleware(Middleware middleware) {
            this.middleware = middleware;
        }

        /**
         * Server gets email and password from client and sends the authorization
         * request to the chain.
         */
        public boolean logIn(String email, String password) {
            if (middleware.check(email, password)) {
                System.out.println("Authorization have been successful!");

                // Do something useful here for authorized users.

                return true;
            }
            return false;
        }

        public void register(String email, String password) {
            users.put(email, password);
        }

        public boolean hasEmail(String email) {
            return users.containsKey(email);
        }

        public boolean isValidPassword(String email, String password) {
            return users.get(email).equals(password);
        }
    }

    abstract class Middleware {
        private Middleware next;

        /**
         * Builds chains of middleware objects.
         */
        public Middleware linkWith(Middleware next) {
            this.next = next;
            return next;
        }

        /**
         * Subclasses will implement this method with concrete checks.
         */
        public abstract boolean check(String email, String password);

        /**
         * Runs check on the next object in chain or ends traversing if we're in
         * last object in chain.
         */
        protected boolean checkNext(String email, String password) {
            if (next == null) {
                return true;
            }
            return next.check(email, password);
        }
    }


    /**
     * ConcreteHandler. Checks whether there are too many failed login requests.
     */
    class ThrottlingMiddleware extends Middleware {
        private int requestPerMinute;
        private int request;
        private long currentTime;

        public ThrottlingMiddleware(int requestPerMinute) {
            this.requestPerMinute = requestPerMinute;
            this.currentTime = System.currentTimeMillis();
        }

        /**
         * Please, not that checkNext() call can be inserted both in the beginning
         * of this method and in the end.
         *
         * This gives much more flexibility than a simple loop over all middleware
         * objects. For instance, an element of a chain can change the order of
         * checks by running its check after all other checks.
         */
        public boolean check(String email, String password) {
            if (System.currentTimeMillis() > currentTime + 60_000) {
                request = 0;
                currentTime = System.currentTimeMillis();
            }

            request++;

            if (request > requestPerMinute) {
                System.out.println("Request limit exceeded!");
                Thread.currentThread().stop();
            }
            return checkNext(email, password);
        }
    }

    /**
     * ConcreteHandler. Checks whether a user with the given credentials exists.
     */
    class UserExistsMiddleware extends Middleware {
        private Server server;

        public UserExistsMiddleware(Server server) {
            this.server = server;
        }

        public boolean check(String email, String password) {
            if (!server.hasEmail(email)) {
                System.out.println("This email is not registered!");
                return false;
            }
            if (!server.isValidPassword(email, password)) {
                System.out.println("Wrong password!");
                return false;
            }
            return checkNext(email, password);
        }
    }

    /**
     * ConcreteHandler. Checks a user's role.
     */
    class RoleCheckMiddleware extends Middleware {
        public boolean check(String email, String password) {
            if (email.equals("admin@example.com")) {
                System.out.println("Hello, admin!");
                return true;
            }
            System.out.println("Hello, user!");
            return checkNext(email, password);
        }
    }


    @Test
    public void test2()  {

        Server server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // All checks are linked. Client can build various chains using the same
        // components.
        Middleware middleware = new ThrottlingMiddleware(2);
        middleware.linkWith(new UserExistsMiddleware(server))
                .linkWith(new RoleCheckMiddleware());

        // Server gets a chain from client code.
        server.setMiddleware(middleware);

        /*boolean success;
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password);
        } while (!success);*/

        Assert.assertTrue(server.logIn("admin@example.com", "admin_pass"));
        Assert.assertTrue(server.logIn("user@example.com", "user_pass"));
    }

}
