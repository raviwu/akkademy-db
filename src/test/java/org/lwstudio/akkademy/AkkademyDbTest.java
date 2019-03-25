package org.lwstudio.akkademy;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.lwstudio.akkademy.messages.GetRequest;
import org.lwstudio.akkademy.messages.SetRequest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

public class AkkademyDbTest {
    private final ActorSystem system = ActorSystem.create();
    private TestActorRef<AkkademyDb> actorRef;
    private AkkademyDb akkademyDb;

    @Before
    public void setup() {
        actorRef = TestActorRef.create(system, Props.create(AkkademyDb.class));
        akkademyDb = actorRef.underlyingActor();
    }

    @Test
    public void itShouldPlaceKeyValueFromSetRequestMessageIntoMap() {
        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());
        actorRef.tell(new SetRequest("secondKey", "secondValue"), ActorRef.noSender());

        assertEquals("value", akkademyDb.map.get("key"));
        assertEquals("secondValue", akkademyDb.map.get("secondKey"));
    }

    @Test
    public void itShouldGetValueFromMapThroughGetRequestMessage() {
        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());
        actorRef.tell(new GetRequest("key"), ActorRef.noSender());
    }
}