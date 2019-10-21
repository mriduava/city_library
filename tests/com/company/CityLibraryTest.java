package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityLibraryTest {

    CityLibrary cityLibrary = new CityLibrary("Bibliotek");

    @Test
    void displayBooks() {
    }

    //To test average rating
    @Test
    void avgRating() {
        float num1 = cityLibrary.avgRating(2.9f);
        float num2 = cityLibrary.avgRating(4.5f);
        String result = String.format("%.1f", num2);
        assertEquals(String.format("%.1f",3.7), result);
    }
}