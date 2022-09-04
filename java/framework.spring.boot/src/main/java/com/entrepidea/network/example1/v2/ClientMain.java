package com.entrepidea.network.example1.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * repetitive requests from client. No change in Server. To test, launch the ServerMain of v1.
 *
 * Date: 12/16/21
 *
 * */
public class ClientMain {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientMain.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Client launched...");
        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream os = socket.getOutputStream();
        PrintStream ps = new PrintStream(os);
        Scanner sc = new Scanner((System.in));
        while(true){
            LOGGER.info("Speak: ");
            String msg = sc.nextLine();
            ps.println(msg);
            ps.flush();
        }
    }
}