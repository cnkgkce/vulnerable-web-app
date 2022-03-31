package com.batuhaniskr.vulnerablewebapp.controller.request;

public class Lecture {

    public Lecture() {

    }

    public Lecture(String name) {
        this.name = name;
    }

    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
