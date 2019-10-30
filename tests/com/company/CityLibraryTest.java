package com.company;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CityLibraryTest {

    CityLibrary cityLibrary = new CityLibrary("Bibliotek");

    @Test
    void addBook() {
        ArrayList<Book> books = new ArrayList<>();
        String title = "Pippi";
        String author = "Astrid Lindgren";
        float rating = 0.3f;
        int quantity = 2;

        Book bookInfo = new Book(title, author, quantity, rating);
        books.add(bookInfo);

        assertEquals(bookInfo, books.get(0));
    }

    //To test average rating
    @Test
    void avgRating() {
        float num = cityLibrary.avgRating(3.1f, 4.9f);
        float result = Float.parseFloat(String.format("%.1f", num));
        assertEquals(String.format("%.1f", 4.0f), num);
    }
}