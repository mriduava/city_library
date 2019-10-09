package com.company;

public class Librarian extends User {

    public Librarian(String name, int id){
        super(name, id);
    }

    public void welcomeMessage(){
        System.out.println("Welcome " + getName().toUpperCase() + "!");
    }
}
