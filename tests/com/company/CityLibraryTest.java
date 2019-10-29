package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CityLibraryTest {

    CityLibrary cityLibrary = new CityLibrary("Bibliotek");

    //To test average rating
    @Test
    void avgRating() {
<<<<<<< HEAD
        String num = cityLibrary.avgRating(3.1f, 4.9f);
        String result = String.format("%.1f", num);
        assertEquals(String.format("%.1f",4.0), result, "Converted to String format");
=======
        float num = cityLibrary.avgRating(2.9f, 3.3f);
        String result = String.format("%.1f", num);
        assertEquals(String.format("%.1f",3.1), result, "Output was long decimal,\n Converted to String format");
>>>>>>> master
    }
}