package org.lwstudio.akkademy;

import java.util.HashMap;
import java.util.Map;

import org.lwstudio.akkademy.messages.GetRequest;
import org.lwstudio.akkademy.messages.SetRequest;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AkkademyDb extends AbstractActor {
    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SetRequest.class, message -> {
            log.info("Received set request - key: {} value: {}", message.getKey(), message.getValue());
            map.put(message.getKey(), message.getValue());
        }).match(GetRequest.class, message -> {
            log.info("Received set request - key: {}", message.getKey());
            map.get(message.getKey());
        }).matchAny(o -> log.info("received unknown message {}", o)).build();
    }
}