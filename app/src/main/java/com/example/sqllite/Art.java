package com.example.sqllite;

public class Art {
    private String name;
    private int id;

    public Art(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
