package com.example.tdd;

import java.time.LocalDateTime;
import java.util.HashSet;

public interface Restaurant {
    HashSet<LocalDateTime> getBookedHours();

    void book(String startDateTime, String endDateTime);

    void book(int hour);
}
