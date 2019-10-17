package com.company;

public class BorrowedBook extends Book {

    private String name;
    public BorrowedBook(String name, String title, String author, int quantity, float rating){
        super(title, author, quantity, rating);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
