package com.entrepidea.experiments.observable.arch;

public class EventProducer {

    private EventLoop eventLoop;

    public void bind(EventLoop loop){
        this.eventLoop = loop;
    }

}
