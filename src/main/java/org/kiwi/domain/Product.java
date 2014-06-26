package org.kiwi.domain;

public class Product {
    int id;
    Price currentPrice;
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

    public Price getCurrentPrice() {
        return currentPrice;
    }
}
