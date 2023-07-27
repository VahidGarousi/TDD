package com.example.composebasestructure;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class RaceResultsServiceTest {
    private final RaceResultsService raceResults = new RaceResultsService();
    private final Message message = mock(Message.class);
    private final Client clientA = mock(Client.class, "ClientA");
    private final Client clientB = mock(Client.class, "ClientB");

    // zero subscribers
    @Test
    public void notSubscribedClientShouldNotReceiveMessage() {
        raceResults.send(message);
        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }

    // one subscribers
    @Test
    public void subscribeClientShouldReceiveMessage() {
        raceResults.addSubscriber(clientA);
        raceResults.send(message);
        verify(clientA).receive(message);
    }

    // two subscribers
    @Test
    public void messageShouldBeSentToAllSubscribedClients() {
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(message);
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }


    @Test
    public void shouldSendOnlyOneMessageToMultipleSubscribers() {
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientA);
        raceResults.send(message);
        verify(clientA).receive(message);
    }

    @Test
    public void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.addSubscriber(clientA);
        raceResults.removeSubscriber(clientA);

        raceResults.send(message);
        verify(clientA, never()).receive(message);
    }




}
