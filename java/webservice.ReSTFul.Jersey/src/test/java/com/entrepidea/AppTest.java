package com.entrepidea;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = App.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(App.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdownNow();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target.path("myresource").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    @Test
    public void testHttpStatus(){
        Response response = target.path("myresource").request().get();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetHeadersAndBody(){
        Response response = target.path("myresource").request().get();
        MultivaluedMap<String, Object> headers = response.getHeaders();
        System.out.println("-- response headers --");
        headers.entrySet().forEach(e-> System.out.printf("%s: %s%n", e.getKey(), e.getValue()));
        System.out.printf("-- response body --%n%s%n", response.readEntity(String.class));
    }
}