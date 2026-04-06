package com.example.demo.model;

public class University {
    public University(String name) {
        Name = name;
    }

    private String Name;

    
    public String getName(){
        return this.Name;
    }

    public void setName(String name){
        this.Name = name;
    }

}