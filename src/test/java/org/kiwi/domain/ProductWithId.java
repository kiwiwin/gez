package org.kiwi.domain;

public class ProductWithId {

    public static Product productWithId(int id, Product product) {
        product.id = id;
        return product;
    }
}
