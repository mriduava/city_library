package com.company;

public class Subscriber extends User {

    public Subscriber(String name, int id){
        super(name, id);
    }

    public String welcomeMessage(){
        return "Welcome " + getName().toUpperCase() + " !";
    }

}
