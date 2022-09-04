package com.entrepidea.network.example1.v5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * This example demonstrates a chat program. The code uses new NIO which features higher throughput and faster responsiveness.
 * Key APIs in new NIO package are Channel, Buffer and Selector. Selector is capable of handling multiple requests in a round robin fashion,
 * therefore a thread handling multiple requests becomes possible, as opposed to using one-thread-per-request or a threadpool.
 * This is the last example from: https://www.zhihu.com/question/67535292/answer/1633228012?utm_oi=809364293245075456&utm_source=pocket_mylist
 *
 * Date: 12/30/21
 *
 * */
public class ServerMain {
    private Selector serverSelector;
    private ServerSocketChannel ssChannel;

    public ServerMain(){
        try {
            ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            ssChannel.bind(new InetSocketAddress(8080));
            serverSelector = Selector.open();
            ssChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }


    public static void main(String[] args){

    }
}
