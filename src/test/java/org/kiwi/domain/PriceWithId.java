package org.kiwi.domain;

public class PriceWithId {
    public static Price priceWithId(int id, Price price) {
        price.id = id;
        return price;
    }
}
