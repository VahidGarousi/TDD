package com.example.composebasestructure;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import java.util.Collections;

public class RaceResultsServiceTest {
    private final RaceResultsService raceResults = new RaceResultsService();
    private final Message message = mock(Message.class);
    private final Category categoryA = mock(Category.class,"CategoryA");
    private final Category categoryB = mock(Category.class,"CategoryB");
    private final Client clientA = mock(Client.class, "ClientA");
    private final Client clientB = mock(Client.class, "ClientB");

    // zero subscribers
    @Test
    public void notSubscribedClientShouldNotReceiveMessage() {
        raceResults.send(categoryA, message);
        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }

    // one subscribers
    @Test
    public void subscribeClientShouldReceiveMessage() {
        clientA.subscribeToCategory(categoryA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.addSubscriber(clientA);
        raceResults.send(categoryA, message);
        verify(clientA).receive(message);
    }

    // two subscribers
    @Test
    public void messageShouldBeSentToAllSubscribedClients() {
        clientA.subscribeToCategory(categoryA);
        clientB.subscribeToCategory(categoryA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        when(clientB.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(categoryA, message);
        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }


    @Test
    public void shouldSendOnlyOneMessageToMultipleSubscribers() {
        clientA.subscribeToCategory(categoryA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientA);
        raceResults.send(categoryA, message);
        verify(clientA).receive(message);
    }

    @Test
    public void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.addSubscriber(clientA);
        raceResults.removeSubscriber(clientA);

        raceResults.send(categoryA, message);
        verify(clientA, never()).receive(message);
    }


    @Test
    public void onlySubscribedClientToRelatedCategoriesShouldReceiveMessages() {
        clientA.subscribeToCategory(categoryA); // spy
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);

        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.send(categoryA, message);

        verify(clientB, never()).receive(message);
        verify(clientA).receive(message);
    }

    @Test
    public void shouldSendMessagesWithDifferentCategories() {
        clientA.subscribeToCategory(categoryA); // spy
        raceResults.addSubscriber(clientA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.send(categoryA, message);
        raceResults.send(categoryB, message);
        verify(clientA,atLeastOnce()).receive(message);
    }

}
