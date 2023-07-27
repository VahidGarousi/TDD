package com.example.composebasestructure;

public interface Client {
    void receive(Message message);

    Category[] getSubscribedCategory();
}
