package com.company;

import java.io.Serializable;

public class BorrowedBook extends Book implements Serializable {

    private String name;
    public BorrowedBook(String name, String title, String author, int quantity, float rating){
        super(title, author, quantity, rating);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return "\nBorrower: " + name.toUpperCase() +
                "\nTitle   : " + getTitle().toUpperCase() +
                "\nAuthor  : " + getAuthor().toUpperCase() +
                "\n-----------------------";
    }

}
