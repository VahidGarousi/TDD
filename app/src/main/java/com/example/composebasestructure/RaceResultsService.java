package com.example.composebasestructure;

import java.util.Collection;
import java.util.HashSet;

public class RaceResultsService {
    private final Logger logger;

    public RaceResultsService(Logger logger) {
        this.logger = logger;
    }

    private final Collection<Client> clients = new HashSet<>();

    public void addSubscriber(Client client) {
        clients.add(client);
    }

    public void send(Category category, Message message) {
        for (Client client : clients) {
            if (client.getCategories().contains(category)) {
                String date = String.valueOf(System.currentTimeMillis());
                logger.log(date,message.getMessage());
                client.receive(message);
            }
        }
    }

    public void removeSubscriber(Client client) {
        clients.remove(client);
    }
}
