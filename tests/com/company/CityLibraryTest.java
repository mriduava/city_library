package com.company;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
<<<<<<< HEAD
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
||||||| merged common ancestors

import static org.junit.jupiter.api.Assertions.*;
=======
import java.util.ArrayList;
>>>>>>> master

class CityLibraryTest {

    CityLibrary cityLibrary = new CityLibrary("Bibliotek");

<<<<<<< HEAD
    @Test
    void addBook() {
        ArrayList<Book> books = new ArrayList<>();

        String title = "Pippi";
        String author = "Astrid Lindgren";
        float rating = 0.3f;
        int quantity = 2;

        Book bookInfo = new Book(title, author, quantity, rating);
        books.add(bookInfo);

        assertEquals(1, books.size());
        assertEquals(bookInfo, books.get(0));
    }

||||||| merged common ancestors
=======
    @Test
    void addBook() {
        ArrayList<Book> books = new ArrayList<>();
        String title = "Pippi";
        String author = "Astrid Lindgren";
        float rating = 0.3f;
        int quantity = 2;

        Book bookInfo = new Book(title, author, quantity, rating);
        books.add(bookInfo);

        Assert.assertEquals(1, books.size());
        Assert.assertEquals(bookInfo, books.get(0));
    }

>>>>>>> master
    //To test average rating
    @Test
    void avgRating() {
        float num = cityLibrary.avgRating(3.1f, 4.9f);
        String result = String.format("%.1f", num);
<<<<<<< HEAD
        assertEquals(String.format("%.1f", 4.0), result);
||||||| merged common ancestors
        assertEquals(String.format("%.1f",4.0), result, "Converted to String format");
=======
        Assert.assertEquals(String.format("%.1f", 4.0), result);
>>>>>>> master
    }


}