package com.entrepidea.network.example1.v4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Use a thread pool and pick up an available thread to respond to an incoming request. Once available threads are exhausted, incoming threads are queued up
 * when queue is full, further requests are rejected.
 *
 * NOTE: ClientMain in v2 can be used for testing.
 *
 * 12/19/21
 *
 * */
public class ServerMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);
    static class SocketHandlerThreadPool{

        private final Executor executor;
        public SocketHandlerThreadPool(int maxPoolSize, int queueSize){
            executor = new ThreadPoolExecutor(3, maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
        }
        public void execute(Runnable r){
            executor.execute(r);
        }

    }

    static class ReadClientTask implements Runnable{
        private final Socket s;

        ReadClientTask(Socket s){
            this.s = s;
        }

        @Override
        public void run() {
            InputStream is = null;
            try {
                is = s.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while (true){
                try {
                    if ((line = br.readLine()) == null) break;
                    LOGGER.info("CurrentThread = {}, Server receives : {}", Thread.currentThread().getName(), line);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
        LOGGER.info("Server launched...");
        SocketHandlerThreadPool stp = new SocketHandlerThreadPool(3,1000);
        ServerSocket ss = new ServerSocket(8888);
        while (true){
            Socket s = ss.accept();
            LOGGER.info("Connection established.");
            stp.execute(new ReadClientTask(s));
        }
    }
}
