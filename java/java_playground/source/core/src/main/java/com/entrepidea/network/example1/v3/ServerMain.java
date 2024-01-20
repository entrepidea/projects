package com.entrepidea.network.example1.v3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * create a thread for each incoming request. No change in ClientMain. To test, run the ClientMain in v2.
 *
 * Date: 12/17/21
 *
 * */
public class ServerMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("The server launched...");
        ServerSocket ss = new ServerSocket(8888);
        while(true){
            Socket s = ss.accept();
            new ServerReadThread(s).start();
            LOGGER.info("{} is online.", s.getRemoteSocketAddress());
        }
    }

    static class ServerReadThread extends Thread{
        private final Socket s;

        public ServerReadThread(Socket s){
            this.s = s;
        }

        @Override
        public void run(){
            try{
                InputStream is = s.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while((line = br.readLine())!=null){
                    LOGGER.info("Thread [{}] is running : The server received from '{}' with message: {}",
                            Thread.currentThread().getName(), s.getRemoteSocketAddress(),line);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
