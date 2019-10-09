package com.company;

public class ReservedBook extends Book {

    private String name;
    public ReservedBook(String name, String title, String author, int quantity){
        super(title, author, quantity);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
