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

    public void send(Category category, Message... messages) {
        for (Client client : clients) {
            if (client.getCategories().contains(category)) {
                String date = String.valueOf(System.currentTimeMillis());
                for (Message message : messages) {
                    logger.log(date, message.getMessage());
                    client.receive(message);
                }
            }
        }
    }

    public void removeSubscriber(Client client) {
        if (clients.contains(client))
            clients.remove(client);
        else throw new IllegalStateException("Are you sure that " + client + " was already subscribed?");
    }
}
