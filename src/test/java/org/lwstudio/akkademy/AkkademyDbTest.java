package org.lwstudio.akkademy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.lwstudio.akkademy.messages.SetRequest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

public class AkkademyDbTest {
    ActorSystem system = ActorSystem.create();

    @Test
    public void itShouldPlaceKeyValueFromSetRequestMessageIntoMap() {
        TestActorRef<AkkademyDb> actorRef = TestActorRef.create(system, Props.create(AkkademyDb.class));

        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());
        actorRef.tell(new SetRequest("secondKey", "secondValue"), ActorRef.noSender());

        AkkademyDb akkademyDb = actorRef.underlyingActor();
        assertEquals("value", akkademyDb.map.get("key"));
        assertEquals("secondValue", akkademyDb.map.get("secondKey"));
    }
}