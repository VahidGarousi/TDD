package com.example.tdd;


import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class RestaurantBookingSystemTest {
    private RestaurantBookingSystem bookingSystem;


    @Before
    public void setUp() {
        bookingSystem = new RestaurantBookingSystem();
    }

    @Test
    public void testBookingHour_ValidHour_Success() {
        bookingSystem.book(12);
        assertThat(bookingSystem.isHourBooked(12)).isTrue();
    }

    @Test
    public void testBookingHour_InvalidHour_Exception() {
        // Arrange
        int invalidHour = 30;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookingSystem.book(invalidHour));
    }


    @Test
    public void testBookingHour_AlreadyBookedHour_Exception() {
        // Arrange
        int hour = 12;

        // Act
        bookingSystem.book(hour);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> bookingSystem.book(hour));
    }

    @Test
    public void testGetBookedHours_NoBookedHours_EmptySet() {
        // Act
        Set<Integer> bookedHours = bookingSystem.getBookedHours();

        // Assert
        assertTrue(bookedHours.isEmpty());
    }


    @Test
    public void testGetBookedHours_BookedHours_NonEmptySet() {
        // Arrange
        int hour1 = 12;
        int hour2 = 15;
        bookingSystem.book(hour1);
        bookingSystem.book(hour2);

        // Act
        Set<Integer> bookedHours = bookingSystem.getBookedHours();

        // Assert
        assertEquals(2, bookedHours.size());
        assertTrue(bookedHours.contains(hour1));
        assertTrue(bookedHours.contains(hour2));
    }


    @Test
    public void testIsHourBooked_BookedHour_True() {
        // Arrange
        int hour = 13;
        bookingSystem.book(hour);

        // Act
        boolean isBooked = bookingSystem.isHourBooked(hour);

        // Assert
        assertTrue(isBooked);
    }

    @Test
    public void testIsHourBooked_NotBookedHour_False() {
        // Arrange
        int bookedHour = 13;
        int notBookedHour = 15;
        bookingSystem.book(bookedHour);

        // Act
        boolean isNotBooked = bookingSystem.isHourBooked(notBookedHour);

        // Assert
        assertFalse(isNotBooked);
    }

    @Test
    public void testBookingHour_InvalidHalfHour_Exception() {
        // Arrange
        int invalidHour = 16; // 2:00 pm

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookingSystem.book(invalidHour));
    }
}
