package org.lwstudio.akkademy;

import java.util.HashMap;
import java.util.Map;

import org.lwstudio.akkademy.messages.GetRequest;
import org.lwstudio.akkademy.messages.SetRequest;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class AkkademyDb extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SetRequest.class, this::receiveSetRequest)
                .match(GetRequest.class, this::receiveGetRequest)
                .matchAny(o -> log.info("received unknown message {}", o)).build();
    }

    private void receiveSetRequest(SetRequest request) {
        log.info("Received set request - key: {} value: {}", request.getKey(), request.getValue());
        map.put(request.getKey(), request.getValue());
    }

    private void receiveGetRequest(GetRequest request) {
        log.info("Received set request - key: {}", request.getKey());
        sender().tell(map.get(request.getKey()), ActorRef.noSender());
    }
}