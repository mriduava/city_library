package com.company;

public class Admin extends User {

    public Admin(String name, int id){
        super(name, id);
    }

    public String welcomeMessage(){
        return "Welcome " + getName().toUpperCase() + " !";
    }
}
