package com.company;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String name;
    private int id;

    public User(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public abstract String welcomeMessage();

}
