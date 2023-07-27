package com.example.tdd;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import java.util.Collections;

public class RaceResultsServiceTest {
    private final Logger logger = mock(Logger.class);
    private final RaceResultsService raceResults = new RaceResultsService(logger);
    private final Message messageA = mock(Message.class,"MessageA");
    private final Message messageB = mock(Message.class,"MessageB");
    private final Category categoryA = mock(Category.class, "CategoryA");
    private final Category categoryB = mock(Category.class, "CategoryB");
    private final Client clientA = mock(Client.class, "ClientA");
    private final Client clientB = mock(Client.class, "ClientB");

    // zero subscribers
    @Test
    public void notSubscribedClientShouldNotReceiveMessage() {
        raceResults.send(categoryA, messageA);
        verify(clientA, never()).receive(messageA);
        verify(clientB, never()).receive(messageA);
    }

    // one subscribers
    @Test
    public void subscribeClientShouldReceiveMessage() {
        clientA.subscribeToCategory(categoryA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.addSubscriber(clientA);
        raceResults.send(categoryA, messageA);
        verify(clientA).receive(messageA);
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
        raceResults.send(categoryA, messageA);
        verify(clientA).receive(messageA);
        verify(clientB).receive(messageA);
    }


    @Test
    public void shouldSendOnlyOneMessageToMultipleSubscribers() {
        clientA.subscribeToCategory(categoryA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientA);
        raceResults.send(categoryA, messageA);
        verify(clientA).receive(messageA);
    }

    @Test
    public void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.addSubscriber(clientA);
        raceResults.removeSubscriber(clientA);

        raceResults.send(categoryA, messageA);
        verify(clientA, never()).receive(messageA);
    }


    @Test
    public void onlySubscribedClientToRelatedCategoriesShouldReceiveMessages() {
        clientA.subscribeToCategory(categoryA); // spy
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);

        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.send(categoryA, messageA);

        verify(clientB, never()).receive(messageA);
        verify(clientA).receive(messageA);
    }

    @Test
    public void shouldSendMessagesWithDifferentCategories() {
        clientA.subscribeToCategory(categoryA); // spy
        raceResults.addSubscriber(clientA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.send(categoryA, messageA);
        raceResults.send(categoryB, messageA);
        verify(clientA, atLeastOnce()).receive(messageA);
    }

    @Test
    public void whenEachMessageSendThenLoggerShouldLogThat() {
        clientA.subscribeToCategory(categoryA);
        clientB.subscribeToCategory(categoryA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        when(clientB.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(categoryA, messageA);
        verify(logger,times(2)).log(any(), any());
    }

    @Test
    public void shouldSendMultipleMessage() {
        clientA.subscribeToCategory(categoryA);
        clientB.subscribeToCategory(categoryA);
        when(clientA.getCategories()).thenReturn(Collections.singletonList(categoryA));
        when(clientB.getCategories()).thenReturn(Collections.singletonList(categoryA));
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(categoryA, messageA,messageB);
        verify(clientA).receive(messageA);
        verify(clientA).receive(messageB);

        verify(clientB).receive(messageA);
        verify(clientB).receive(messageB);
    }

    @Test(expected =  IllegalStateException.class)
    public void throwExceptionWhenClientWhichIsNotSubscribedTriesToUnsubscribe() {
        raceResults.removeSubscriber(clientA);
    }
}
