package com.entrepidea.network.example1.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
/**
*  this version and the follow-up versions adopts a step-by-step approach to cover networking programming in Java
 * https://www.zhihu.com/question/67535292/answer/1633228012?utm_oi=809364293245075456&utm_source=pocket_mylist
 *
 * Date: 03/11/22
 *
* */
public class ClientMain {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Client launched...");
        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream os = socket.getOutputStream();
        PrintStream ps = new PrintStream(os);
        ps.println("Hello, world!");
        ps.flush();
    }
}
