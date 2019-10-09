package com.company;

import java.io.Serializable;

public class Book implements Comparable<Book>, Serializable {

    private static SortBy SortBy = Book.SortBy.TITLE;

    enum SortBy{
        TITLE,
        AUTHOR
    }

    private String title, author;
    private int quantity = 0;
    public Book(String title, String author, int quantity){
        this.title = title;
        this.author = author;
        this.quantity += quantity;
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

    //SORT BOOKS PART 1
    public static void setSortBy(Book.SortBy sortBy){
        Book.SortBy = sortBy;
    }

    //SORT BOOKS PART 2
    public int compareTo(Book books){
        switch (SortBy){
            case AUTHOR:
                return getAuthor().compareTo(books.getAuthor());
            default:
                return getTitle().compareTo(books.getTitle());
        }
    }

    public String toString(){
        return "\nTitle : " + title.toUpperCase() +
                "\nAuthor: " + author.toUpperCase() +
                "\nQuantity: " + quantity +
                "\n-----------------------";
    }

}
