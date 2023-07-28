package com.example.tdd;

/**
 * Practical Unit Testing with TestNG and Mockito - source code for exercises.
 * Visit http://practicalunittesting.com for more information.
 *
 * @author Tomek Kaczanowski
 */
public interface User {
    String getPassword();

    void setPassword(String passwordMd5);
}