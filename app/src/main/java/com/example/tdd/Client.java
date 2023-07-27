package com.example.tdd;

import java.util.List;

public interface Client {
    void receive(Message message);

    void subscribeToCategory(Category category);

    List<Category> getCategories();
}