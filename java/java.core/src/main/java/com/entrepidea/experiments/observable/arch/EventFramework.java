package com.entrepidea.experiments.observable.arch;

import java.util.LinkedList;
import java.util.List;

public class EventFramework {
    private List<EventProducer> eventProducerList;
    private boolean started = false;
    private EventLoop eventLoop;
    private List<StartStatusListener> subscribers = null;

    public EventFramework(EventLoop eventLoop, StartStatusListener subscriber){
        this.eventLoop = eventLoop;
        if(subscribers == null){
            subscribers = new LinkedList<>();
            subscribers.add(subscriber);
        }
    }

    public boolean start(){
        if(!started){
            eventLoop.start();
            started = true;
        }
        return started;
    }

    public void setEventProducer(EventProducer producer){
        if(eventProducerList==null){
            eventProducerList = new LinkedList<>();
        }
        producer.bind(eventLoop);
        eventProducerList.add(producer);
    }

    public void notifyStart(){
        for(StartStatusListener listener : subscribers){
            listener.notifyStart();
        }
    }
}
