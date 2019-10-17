package com.company;

public class Admin extends User {

    public Admin(String name, int id){
        super(name, id);
    }

    public void welcomeMessage(){
        System.out.println("Welcome " + getName().toUpperCase() + "!");
    }
}
