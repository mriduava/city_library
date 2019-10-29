package com.company;

import java.io.Serializable;

public class Book implements Comparable<Book>, Serializable {

    private static SortBy SortBy = Book.SortBy.TITLE;

    enum SortBy{
        TITLE,
        AUTHOR
    }

    private String title, author;
    private int quantity;
    private float rating;
    public Book(String title, String author, int quantity, float rating){
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    //Setter of SortBy field
    public static void setSortBy(Book.SortBy sortBy){
        Book.SortBy = sortBy;
    }

    //Sort Books
    public int compareTo(Book books){
        switch (SortBy){
            case AUTHOR:
                return getAuthor().compareTo(books.getAuthor());
            default:
                return getTitle().compareTo(books.getTitle());
        }
    }

    public String toString(){
        return String.format("Title   : %s \nAuthor  : %s\nAvg Rate: %.1f\nQuantity: %s\n------------------",
                title.toUpperCase(),
                author.toUpperCase(),
                rating,
                quantity);
    }
}
