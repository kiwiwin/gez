package org.kiwi.domain;

public class ProductWithId {

    public static Product productWithId(int id, Product product) {
        product.id = id;
        return product;
    }

    public static Product productWithIdAndPrice(int id, Product product, Price currentPrice) {
        product.id = id;
        product.currentPrice = currentPrice;
        return product;
    }
}
