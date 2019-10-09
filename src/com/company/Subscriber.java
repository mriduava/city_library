package com.company;

public class Subscriber extends User {

    public Subscriber(String name, int id){
        super(name, id);
    }

    public void welcomeMessage(){
        System.out.println("Welcome " + getName().toUpperCase() + "!");
    }

}
