package com.example.tdd;

import java.util.HashSet;
import java.util.Set;

public class RestaurantBookingSystem {
    private Set<Integer> bookedHours;

    public RestaurantBookingSystem() {
        this.bookedHours = new HashSet<>();
    }

    public Set<Integer> getBookedHours() {
        return bookedHours;
    }


    public void book(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23.");
        }
        if (!isInForbiddenHours(hour)) {
            throw new IllegalArgumentException("Only regular whole clock hours are allowed for booking.");
        }
        if (isHourBooked(hour)) {
            throw new IllegalArgumentException("this hour has been already booked!");
        }
        bookedHours.add(hour);
    }

    public boolean isHourBooked(int hour) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23.");
        }
        return bookedHours.contains(hour);
    }

    // Helper method to check if the provided hour is a regular whole clock hour
    private boolean isInForbiddenHours(int hour) {
        if (hour >= 16 && hour <= 17){
            throw new IllegalArgumentException("Forbidden time!");
        }
        return true;
    }
}