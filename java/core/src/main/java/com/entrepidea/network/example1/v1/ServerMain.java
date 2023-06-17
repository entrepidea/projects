package com.entrepidea.network.example1.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  this version and the follow-up versions adopts a step-by-step approach to cover networking programming in Java
 * https://www.zhihu.com/question/67535292/answer/1633228012?utm_oi=809364293245075456&utm_source=pocket_mylist
 *
 * Date: 03/11/22
 *
 * */

public class ServerMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Server launched ...");
        ServerSocket ss = new ServerSocket(8888);
        Socket s = ss.accept();
        InputStream is = s.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        while((line = br.readLine())!=null){
            LOGGER.info("The server receives: {}", line);
        }
    }
}
