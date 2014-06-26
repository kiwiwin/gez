package org.kiwi.domain;

public class Product {
    int id;
    private String name;
    private String description;

    public Product() {

    }

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
